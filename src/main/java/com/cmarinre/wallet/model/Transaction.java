package com.cmarinre.wallet.model;

import com.cmarinre.wallet.enums.StatusType;
import com.cmarinre.wallet.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

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
  private OffsetDateTime transactionDate;
  private BigDecimal amount;
  private TransactionType transactionType;
  private String description;
  private StatusType status;
  private String fullname;
  private String customerId;//<--- Stripe CustomerId


}
