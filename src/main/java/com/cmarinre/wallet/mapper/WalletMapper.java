package com.cmarinre.wallet.mapper;


import com.cmarinre.wallet.dtos.request.CreateCustomerWalletRequest;
import com.cmarinre.wallet.model.CustomerWallet;

import com.cmarinre.wallet.util.Util;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

  public CustomerWallet mapToCustomerWallet(CreateCustomerWalletRequest request, String stripeCustomerId){
   return CustomerWallet.builder()
       .stripeCustomerId(stripeCustomerId)
       .walletId(Util.generateUniqueId(request.getClientId()))
       .clientSecret(request.getClientSecret())
       .clientId(request.getClientId())
       .walletType(request.getWalletType())
       .balance(0L)
       .currency(String.valueOf(request.getCurrency()))
       .build();
  }

}
