package com.cmarinre.wallet.service;

import com.cmarinre.wallet.dtos.request.CreateCustomerWalletRequest;
import com.cmarinre.wallet.dtos.request.CreatePaymentRequest;
import com.cmarinre.wallet.exception.ValidationException;
import com.cmarinre.wallet.mapper.SubscriptionMapper;
import com.cmarinre.wallet.mapper.TransactionMapper;
import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.model.SubscriptionWallet;
import com.cmarinre.wallet.model.Transaction;
import com.cmarinre.wallet.repository.CustomerWalletRepository;
import com.cmarinre.wallet.repository.SubscriptionRepository;
import com.cmarinre.wallet.repository.TransactionRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentLink;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerSearchParams;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService {

  private final CustomerWalletRepository customerWalletRepository;
  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;
  private final SubscriptionMapper subscriptionMapper;
  private final SubscriptionRepository subscriptionRepository;

  @Value("${stripe.secret-key}")
  private String stripeSecretKey;

  public ResponseEntity<PaymentLink> createPaymentLink(CreatePaymentRequest createPaymentRequest) throws StripeException {
    PaymentLinkCreateParams params =
        PaymentLinkCreateParams.builder()
            .addLineItem(
                PaymentLinkCreateParams.LineItem.builder()
                    .setPrice(String.valueOf(createPaymentRequest.getPrice()))
                    .setQuantity(1L)
                    .build()
            )
            .setAfterCompletion(
                PaymentLinkCreateParams.AfterCompletion.builder()
                    .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                    .setRedirect(
                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                            .setUrl("https://example.com")//<--- Meter aqui client secret para dar el dinero a este???
                            .build()
                    )
                    .build()
            )
            .build();

    return ResponseEntity.ok(PaymentLink.create(params));
  }

  public void paymentSucceed(PaymentIntent paymentIntent) throws StripeException {

    CustomerWallet customerWallet = customerWalletRepository.findByStripeCustomerId(
        paymentIntent.getCustomer()).orElseThrow(
        () -> new ValidationException("Customer Wallet Not Found")
    );

    Transaction transaction = transactionMapper.mapPaymentSucceedToTransaction(customerWallet,
        paymentIntent.getAmount());

    transactionRepository.save(transaction);
    log.info("{} -- Transaction Saved", WalletService.class.getName());

    List<Transaction> transactionList = customerWallet.getTransactions();
    transactionList.add(transaction);
    customerWallet.setTransactions(transactionList);
    //si subscripcion
    addSubscription(customerWallet, paymentIntent);

    customerWalletRepository.save(customerWallet);
    log.info("{} -- Payment Succeed and amount added to balance", WalletService.class.getName());
  }

  public CustomerSearchResult searchCustomer(CreateCustomerWalletRequest request) throws StripeException {
    Stripe.apiKey = stripeSecretKey;
    return Customer.search(CustomerSearchParams.builder()
        .setQuery("name:'"+ request.getCustomerName() + "' AND email:'"+ request.getCustomerEmail() +"'")
        .build());
  }

  private void addSubscription(CustomerWallet customerWallet, PaymentIntent paymentIntent)
      throws StripeException {

    Stripe.apiKey = stripeSecretKey;

    if (Objects.isNull(customerWallet.getSubscription())) {



      SubscriptionCreateParams params =
          SubscriptionCreateParams.builder()
              .setCustomer(customerWallet.getStripeCustomerId())
              .addItem(
                  SubscriptionCreateParams.Item.builder()
                      .setPrice("price_1PBe4qD4BAwm8biQw1lpu8YF")
                      .build()
              )
              .build();
      Subscription subscriptionStripe = Subscription.create(params);

      SubscriptionWallet subscription = subscriptionMapper.mapToSubscription(paymentIntent,
          customerWallet);
      subscriptionRepository.save(subscription);
    }
  }

}
