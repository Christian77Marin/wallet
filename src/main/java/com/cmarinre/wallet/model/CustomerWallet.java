package com.cmarinre.wallet.model;

import com.cmarinre.wallet.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "customer_wallet")
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerWallet implements Serializable {

  @Id
  @Column(name = "wallet_id")
  private String walletId;

  @Column(name = "client_id")
  private String clientId;

  @Column(name = "client_secret")
  private String clientSecret;

  @Column(name = "balance")
  private Long balance;

  @OneToMany(mappedBy = "wallet")
  @JsonIgnore
  private List<Transaction> transactions;

  @Column(name = "currency")
  private String currency;

  @Column(name = "wallet_type")
  @Enumerated(EnumType.STRING)
  private WalletType walletType;

  @Column(name = "stripe_customer_id")
  private String stripeCustomerId;

  @OneToOne(mappedBy = "wallet")
  private SubscriptionWallet subscription;
}
