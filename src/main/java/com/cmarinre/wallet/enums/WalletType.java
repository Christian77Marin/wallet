package com.cmarinre.wallet.enums;

public enum WalletType {

  INTERNAL("Internal"),
  External("External");

  private final String value;

  WalletType(String value) {
    this.value = value;
  }



}
