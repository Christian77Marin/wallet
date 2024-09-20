package com.cmarinre.wallet.dtos.request;

import com.cmarinre.wallet.enums.Currency;
import com.cmarinre.wallet.enums.WalletType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateCustomerWalletRequest {
  private final String customerStripeId;
  @NotNull
  private final String clientId;
  @NotNull
  private final String clientSecret;
  @NotNull
  private final WalletType walletType;
  @NotNull
  private final Currency currency;
  @NotNull
  private final String customerName;
  @NotNull
  private final String customerEmail;


}
