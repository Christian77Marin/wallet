package com.cmarinre.wallet.service;

import com.cmarinre.wallet.dtos.request.CreateCustomerWalletRequest;
import com.cmarinre.wallet.mapper.WalletMapper;
import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.repository.CustomerWalletRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.CustomerSearchResult;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerSearchParams;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

  private final CustomerWalletRepository customerWalletRepository;
  private final StripeService stripeService;
  private final WalletMapper walletMapper;



  public ResponseEntity<List<CustomerWallet>> getCustomerWallets() {//<-- Filtro
    return ResponseEntity.ok(customerWalletRepository.findAll());
  }

  public ResponseEntity<CustomerWallet> getCustomerWallet(String id) {
    return ResponseEntity.ok(customerWalletRepository.findById(id).orElseThrow());
  }

  public ResponseEntity<CustomerWallet> createCustomerWallet(CreateCustomerWalletRequest createCustomerWalletRequest)
      throws StripeException {

    CustomerSearchResult customerSearchResult = stripeService.searchCustomer(createCustomerWalletRequest);

    String customerId = null;

    if(customerSearchResult.getData().isEmpty()){
      CustomerCreateParams params =
        CustomerCreateParams.builder()
            .setName(createCustomerWalletRequest.getCustomerName())
            .setEmail(createCustomerWalletRequest.getCustomerEmail())
            .build();
    Customer customer = Customer.create(params);
    customerId = customer.getId();
    } else {
      customerId = customerSearchResult.getData().getFirst().getId();
    }

    return ResponseEntity.ok(customerWalletRepository.save(walletMapper.mapToCustomerWallet(createCustomerWalletRequest, customerId)));
  }

  public ResponseEntity<String> deleteCustomerWallet(String id)
      throws StripeException {

    CustomerWallet customerWallet = customerWalletRepository.findById(id).orElseThrow();

    Customer resource = Customer.retrieve(customerWallet.getWalletId());
    resource.delete();

    customerWalletRepository.deleteById(id);

    return ResponseEntity.ok("Deleted customer wallet with id" + id);
  }


}
