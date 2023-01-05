package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets BankAccountStatus
 */
public enum BankAccountStatus {
  ACTIVE("Active"),
  INACTIVE("Inactive"),
  CLOSED("Closed");

  private String value;

  BankAccountStatus(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static BankAccountStatus fromValue(String text) {
    for (BankAccountStatus b : BankAccountStatus.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
