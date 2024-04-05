package com.cmarinre.wallet.model;

import com.cmarinre.wallet.enums.StatusType;
import com.cmarinre.wallet.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Data
@Builder
@Table(name = "`Transaction`", schema = "wallet")
@RequiredArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @Column(name = "transaction_id")
  private String transactionId;
  private String clientId;
  private String walletId; // <-- WalletId to reference the wallet and show it easily in the frontend
  private LocalDateTime transactionDate;
  private Long amount;
  private String transactionType;
  private String description;
  private String status;


}
