package com.cmarinre.wallet.controller;


import com.cmarinre.wallet.dtos.request.CreateCustomerWalletRequest;
import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.service.WalletService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
@Slf4j
public class WalletController {

  private final WalletService walletService;

  //HEADERS
  @GetMapping(path = "")
  public ResponseEntity<List<CustomerWallet>> getCustomerWallets() {
    return walletService.getCustomerWallets();
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<CustomerWallet> getCustomerWallet(@PathVariable String id) {
    return walletService.getCustomerWallet(id);
  }

  @PostMapping(path = "")
  public ResponseEntity<CustomerWallet> createCustomerWallet(@Valid @RequestBody
  CreateCustomerWalletRequest request) throws StripeException {
    return walletService.createCustomerWallet(request);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<String> deleteCustomerWallet(@PathVariable String walletId)
      throws StripeException {
    return walletService.deleteCustomerWallet(walletId);
  }


}
