package com.cmarinre.wallet.model;

import com.cmarinre.wallet.enums.WalletType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "`CustomerWallet`", schema = "wallet")
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomerWallet implements Serializable {

  //TODO define manytoOne or oneToMany

  @Id
  @Column(name = "wallet_id")
  private String walletId;
  @Column(name = "client_id")
  private String clientId;
  @Column(name = "client_secret")
  private String clientSecret;
  private Long balance;
  private List<Transaction> transactions; //????
  private WalletType walletType; // to distinguish if it is internal or external
  private String customerId;//<--- Stripe CustomerId
}
