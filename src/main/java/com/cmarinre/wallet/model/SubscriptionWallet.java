package com.cmarinre.wallet.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "subscription")
@RequiredArgsConstructor
@AllArgsConstructor
public class SubscriptionWallet {

  @Id
  @Column(name = "subscription_id")
  private String subscriptionId;

  @Column(name = "client_id")
  private String clientId;

  @OneToOne
  @JoinColumn(name = "wallet_id")
  private CustomerWallet wallet;

  @Column(name = "transaction_date")
  private LocalDateTime transactionDate;

  @Column(name = "next_payment_date")
  private LocalDateTime nextPaymentDate;

  @Column(name = "cancel_date")
  private LocalDateTime cancelDate;

  @Column(name = "amount")
  private Long amount;

  @Column(name = "transaction_type")
  private String transactionType;

  @Column(name = "description")
  private String description;

  @Column(name = "currency")
  private String currency;

  @Column(name = "status")
  private String status;
}
