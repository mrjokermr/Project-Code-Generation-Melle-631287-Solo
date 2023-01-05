package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets BankAccountType
 */
public enum BankAccountType {
    SAVINGS("Savings"),
    CURRENT("Current");

    private String value;

    BankAccountType(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static BankAccountType fromValue(String text) {
        for (BankAccountType b : BankAccountType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
