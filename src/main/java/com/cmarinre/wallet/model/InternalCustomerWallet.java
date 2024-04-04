package com.cmarinre.wallet.model;

import com.cmarinre.wallet.enums.StatusType;
import com.cmarinre.wallet.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "`InternalCustomerWallet`", schema = "wallet")
@RequiredArgsConstructor
@AllArgsConstructor
public class InternalCustomerWallet{
  @Id
  @Column(name = "wallet_id")
  private String walletId;
  private String clientId;
  private BigDecimal balance;
  private List<TransactionType> transactionType; //????
  private String customerId;//<--- Stripe CustomerId
}
