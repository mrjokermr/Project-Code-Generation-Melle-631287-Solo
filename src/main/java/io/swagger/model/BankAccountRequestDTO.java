package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankAccountRequestDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")


public class BankAccountRequestDTO   {
  @JsonProperty("iban")
  private String iban = null;

  @JsonProperty("accountStatus")
  private BankAccountStatus accountStatus = null;

  @JsonProperty("accountType")
  private BankAccountType accountType = null;

  @JsonProperty("absoluteLimit")
  private Double absoluteLimit = null;

  public BankAccountRequestDTO IBAN(String IBAN) {
    this.iban = IBAN;
    return this;
  }

  /**
   * Get IBAN
   * @return IBAN
   **/
  @Schema(example = "NLHDINHO0235930399", required = true, description = "")
      @NotNull

    public String getIban() {
    return iban;
  }

  public void setIban(String iban) {
    this.iban = iban;
  }

  public BankAccountRequestDTO accountStatus(BankAccountStatus accountStatus) {
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
    public BankAccountStatus getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(BankAccountStatus accountStatus) {
    this.accountStatus = accountStatus;
  }

  public BankAccountRequestDTO accountType(BankAccountType accountType) {
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
    public BankAccountType getAccountType() {
    return accountType;
  }

  public void setAccountType(BankAccountType accountType) {
    this.accountType = accountType;
  }

  public BankAccountRequestDTO absoluteLimit(Double absoluteLimit) {
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
    public Double getAbsoluteLimit() {
    return absoluteLimit;
  }

  public void setAbsoluteLimit(Double absoluteLimit) {
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
    return Objects.equals(this.iban, bankAccountRequestDTO.iban) &&
        Objects.equals(this.accountStatus, bankAccountRequestDTO.accountStatus) &&
        Objects.equals(this.accountType, bankAccountRequestDTO.accountType) &&
        Objects.equals(this.absoluteLimit, bankAccountRequestDTO.absoluteLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iban, accountStatus, accountType, absoluteLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccountRequestDTO {\n");
    
    sb.append("    IBAN: ").append(toIndentedString(iban)).append("\n");
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
