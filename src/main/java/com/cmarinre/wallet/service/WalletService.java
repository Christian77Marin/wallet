package com.cmarinre.wallet.service;

import com.cmarinre.wallet.exception.ValidationException;
import com.cmarinre.wallet.mapper.TransactionMapper;
import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.model.Transaction;
import com.cmarinre.wallet.repository.CustomerWalletRepository;
import com.cmarinre.wallet.repository.TransactionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletService {

  private final CustomerWalletRepository customerWalletRepository;
  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;


  public void paymentSucceed(String clientSecret, Long amount) {

    CustomerWallet customerWallet = customerWalletRepository.findByClientSecret(clientSecret).orElseThrow(
        () -> new ValidationException("Customer Wallet Not Found")
    );

    customerWallet.setBalance(customerWallet.getBalance() + amount);


    Transaction transaction = transactionMapper.mapPaymentSucceedToTransaction(customerWallet, amount);

    transactionRepository.save(transaction);
    log.info(WalletService.class.getName() + " -- Transaction Saved");

    List<Transaction> transactionList = customerWallet.getTransactions();
    transactionList.add(transaction);
    customerWallet.setTransactions(transactionList);

    customerWalletRepository.save(customerWallet);
    log.info(WalletService.class.getName() + " -- Payment Succeed and amount added to balance");
  }

}
