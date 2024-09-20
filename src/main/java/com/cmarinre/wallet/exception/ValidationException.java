package com.cmarinre.wallet.exception;

public class ValidationException extends RuntimeException {

  public ValidationException(String errorMessage) {
    super(errorMessage);
  }
}
