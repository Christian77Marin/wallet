package com.cmarinre.wallet.mapper;

import static com.cmarinre.wallet.enums.StatusType.ACCEPTED;
import static com.cmarinre.wallet.enums.TransactionType.PAYMENT;

import com.cmarinre.wallet.enums.StatusType;
import com.cmarinre.wallet.model.CustomerWallet;
import com.cmarinre.wallet.model.Transaction;
import com.cmarinre.wallet.util.Util;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

  private static final String PAYMENT_SUCCEED_DESCRIPTION = "Payment Succeed";

  public Transaction mapPaymentSucceedToTransaction(CustomerWallet wallet, Long amount){
    LocalDateTime actualTime = LocalDateTime.now();

    return Transaction.builder()
        .transactionId(Util.generateUniqueId(wallet.getClientId() + actualTime))
        .transactionDate(actualTime)
        .transactionType(PAYMENT.getValue())
        .amount(amount)
        .clientId(wallet.getClientId())
        .description(PAYMENT_SUCCEED_DESCRIPTION)
        .status(ACCEPTED.getValue())
        .build();
  }
}
