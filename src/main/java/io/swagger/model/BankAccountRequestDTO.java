package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.BankAccountStatus;
import io.swagger.model.BankAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankAccountRequestDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")


public class BankAccountRequestDTO   {
  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("accountStatus")
  private BankAccountType accountStatus = null;

  @JsonProperty("accountType")
  private BankAccountStatus accountType = null;

  @JsonProperty("absoluteLimit")
  private BigDecimal absoluteLimit = null;

  public BankAccountRequestDTO IBAN(String IBAN) {
    this.IBAN = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(example = "NLHDINHO0235930399", required = true, description = "")
      @NotNull

    public String getIBAN() {
    return IBAN;
  }

  public void setIBAN(String IBAN) {
    this.IBAN = IBAN;
  }

  public BankAccountRequestDTO accountStatus(BankAccountType accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

  /**
   * Get accountStatus
   * @return accountStatus
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BankAccountType getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(BankAccountType accountStatus) {
    this.accountStatus = accountStatus;
  }

  public BankAccountRequestDTO accountType(BankAccountStatus accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public BankAccountStatus getAccountType() {
    return accountType;
  }

  public void setAccountType(BankAccountStatus accountType) {
    this.accountType = accountType;
  }

  public BankAccountRequestDTO absoluteLimit(BigDecimal absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
    return this;
  }

  /**
   * Get absoluteLimit
   * @return absoluteLimit
   **/
  @Schema(example = "-300.58", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getAbsoluteLimit() {
    return absoluteLimit;
  }

  public void setAbsoluteLimit(BigDecimal absoluteLimit) {
    this.absoluteLimit = absoluteLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccountRequestDTO bankAccountRequestDTO = (BankAccountRequestDTO) o;
    return Objects.equals(this.IBAN, bankAccountRequestDTO.IBAN) &&
        Objects.equals(this.accountStatus, bankAccountRequestDTO.accountStatus) &&
        Objects.equals(this.accountType, bankAccountRequestDTO.accountType) &&
        Objects.equals(this.absoluteLimit, bankAccountRequestDTO.absoluteLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(IBAN, accountStatus, accountType, absoluteLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccountRequestDTO {\n");
    
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
