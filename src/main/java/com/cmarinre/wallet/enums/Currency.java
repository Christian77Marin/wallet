package com.cmarinre.wallet.enums;

import lombok.Getter;

@Getter
public enum Currency {
  EUR("EUR"),
  USD("USD"),
  LIB("LIB"),
  MXN("MXN");

  private final String value;

  Currency(String value) {
    this.value = value;
  }


}
