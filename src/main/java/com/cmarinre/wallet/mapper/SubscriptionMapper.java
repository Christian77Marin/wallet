package com.cmarinre.wallet.mapper;

import static com.cmarinre.wallet.enums.StatusType.ACCEPTED;
import static com.cmarinre.wallet.enums.TransactionType.SUBSCRIPTION;

import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.model.SubscriptionWallet;
import com.cmarinre.wallet.util.Util;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionMapper {

  private static final String PAYMENT_SUCCEED_DESCRIPTION = "Payment Succeed";

  public PaymentIntentCreateParams mapToPaymentIntentParams(SubscriptionWallet subscription){
    LocalDateTime actualTime = LocalDateTime.now();

    return PaymentIntentCreateParams.builder()
            .setAmount(subscription.getAmount())
            .setCurrency("usd")
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .build();
  }

  public SubscriptionWallet mapToSubscription(PaymentIntent paymentIntent, CustomerWallet customerWallet){
    LocalDateTime actualTime = LocalDateTime.now();

    return SubscriptionWallet.builder()
        .subscriptionId(Util.generateUniqueId(paymentIntent.getId()+actualTime))
        .status(ACCEPTED.getValue())
        .nextPaymentDate(actualTime.plusMonths(1))
        .transactionDate(actualTime)
        .transactionType(SUBSCRIPTION.getValue())
        .amount(paymentIntent.getAmount())
        .description("Subscription")
        .currency(paymentIntent.getCurrency())
        .wallet(customerWallet)
        .clientId(customerWallet.getClientId())
        .build();
  }
}
