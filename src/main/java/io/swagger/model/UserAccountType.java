package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets UserAccountType
 */
public enum UserAccountType implements GrantedAuthority {
    ROLE_EMPLOYEE("Employee"),
    ROLE_CUSTOMER("Customer"),
    ROLE_BANKADMIN("BankAdmin");

  private String value;

  @Override
  public String getAuthority() {
    return name();
  }

  UserAccountType(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static UserAccountType fromValue(String text) {
    for (UserAccountType b : UserAccountType.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
