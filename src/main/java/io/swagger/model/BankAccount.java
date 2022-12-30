package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.BankAccountStatus;
import io.swagger.model.BankAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * BankAccount
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")
@Entity
@Table(name = "bankAccounts")
public class BankAccount   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id = null;

  @JsonProperty("IBAN")
  private String IBAN = null;

  @JsonProperty("ownerId")
  private Integer ownerId = null;

  @JsonProperty("balance")
  private Double balance = null;

  @JsonProperty("accountStatus")
  private BankAccountType accountStatus = null;

  @JsonProperty("accountType")
  private BankAccountStatus accountType = null;

  @JsonProperty("absoluteLimit")
  private Double absoluteLimit = null;

  @JsonProperty("creationDate")
  private Date creationDate = null;

  public BankAccount id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "1", required = true, description = "")
      @NotNull

    public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public BankAccount IBAN(String IBAN) {
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

  public BankAccount ownerId(Integer ownerId) {
    this.ownerId = ownerId;
    return this;
  }

  /**
   * Get ownerId
   * @return ownerId
   **/
  @Schema(example = "12", required = true, description = "")
      @NotNull

    public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  public BankAccount balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
   **/
  @Schema(example = "1037.56", required = true, description = "")
      @NotNull

    @Valid
    public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public BankAccount accountStatus(BankAccountType accountStatus) {
    this.accountStatus = accountStatus;
    return this;
  }

  /**
   * Get accountStatus
   * @return accountStatus
   **/
  @Schema(description = "")
  
    @Valid
    public BankAccountType getAccountStatus() {
    return accountStatus;
  }

  public void setAccountStatus(BankAccountType accountStatus) {
    this.accountStatus = accountStatus;
  }

  public BankAccount accountType(BankAccountStatus accountType) {
    this.accountType = accountType;
    return this;
  }

  /**
   * Get accountType
   * @return accountType
   **/
  @Schema(description = "")
  
    @Valid
    public BankAccountStatus getAccountType() {
    return accountType;
  }

  public void setAccountType(BankAccountStatus accountType) {
    this.accountType = accountType;
  }

  public BankAccount absoluteLimit(Double absoluteLimit) {
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

  public BankAccount creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
   **/
  @Schema(example = "2016-08-29T09:12:33.001Z", required = true, description = "")
      @NotNull

    @Valid
    public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccount bankAccount = (BankAccount) o;
    return Objects.equals(this.id, bankAccount.id) &&
        Objects.equals(this.IBAN, bankAccount.IBAN) &&
        Objects.equals(this.ownerId, bankAccount.ownerId) &&
        Objects.equals(this.balance, bankAccount.balance) &&
        Objects.equals(this.accountStatus, bankAccount.accountStatus) &&
        Objects.equals(this.accountType, bankAccount.accountType) &&
        Objects.equals(this.absoluteLimit, bankAccount.absoluteLimit) &&
        Objects.equals(this.creationDate, bankAccount.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, IBAN, ownerId, balance, accountStatus, accountType, absoluteLimit, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccount {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    IBAN: ").append(toIndentedString(IBAN)).append("\n");
    sb.append("    ownerId: ").append(toIndentedString(ownerId)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    accountStatus: ").append(toIndentedString(accountStatus)).append("\n");
    sb.append("    accountType: ").append(toIndentedString(accountType)).append("\n");
    sb.append("    absoluteLimit: ").append(toIndentedString(absoluteLimit)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
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
