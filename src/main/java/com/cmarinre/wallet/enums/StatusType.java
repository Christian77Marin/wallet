package com.cmarinre.wallet.enums;

import lombok.Getter;

@Getter
public enum StatusType {
  ACCEPTED("ACCEPTED"),
  SUCCEEDED("succeeded"),
  FAILED("payment_failed"),
  CANCELLED("CANCELLED"),
  DECLINED("DECLINED");

  private final String value;

  StatusType(String value) {
    this.value = value;
  }


}
