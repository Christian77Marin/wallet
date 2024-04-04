package com.cmarinre.wallet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public enum TransactionType {
  MONTHLY("Monthly"),
  YEARLY("Yearly");

  private final String value;

  TransactionType(String value) {
    this.value = value;
  }


}
