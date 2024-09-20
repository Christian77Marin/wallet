package com.cmarinre.wallet.dtos.request;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreatePaymentRequest {
  private final BigDecimal price;
  private final String clientId;
  private final String clientSecret;

}
