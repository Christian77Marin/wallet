package com.cmarinre.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "transaction")
@RequiredArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  @Column(name = "transaction_id")
  private String transactionId;

  @Column(name = "client_id")
  private String clientId;

  @ManyToOne
  @JoinColumn(name = "wallet_id")
  private CustomerWallet wallet;

  @Column(name = "transaction_date")
  private LocalDateTime transactionDate;

  @Column(name = "amount")
  private Long amount;

  @Column(name = "transaction_type")
  private String transactionType;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private String status;
}
