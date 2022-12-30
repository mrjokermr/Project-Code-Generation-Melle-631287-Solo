package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")


public class Transaction   {
  @JsonProperty("ibanFrom")
  private String ibanFrom = null;

  @JsonProperty("ibanTo")
  private String ibanTo = null;

  @JsonProperty("amount")
  private BigDecimal amount = null;

  @JsonProperty("userPerforming")
  private Integer userPerforming = null;

  /**
   * Gets or Sets transactionType
   */
  public enum TransactionTypeEnum {
    REGULAR("regular"),
    
    WITHDRAW("withdraw"),
    
    DEPOSIT("deposit");

    private String value;

    TransactionTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TransactionTypeEnum fromValue(String text) {
      for (TransactionTypeEnum b : TransactionTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("transactionType")
  private TransactionTypeEnum transactionType = null;

  public Transaction ibanFrom(String ibanFrom) {
    this.ibanFrom = ibanFrom;
    return this;
  }

  /**
   * Get ibanFrom
   * @return ibanFrom
   **/
  @Schema(example = "NLHDINHO0235930399", required = true, description = "")
      @NotNull

    public String getIbanFrom() {
    return ibanFrom;
  }

  public void setIbanFrom(String ibanFrom) {
    this.ibanFrom = ibanFrom;
  }

  public Transaction ibanTo(String ibanTo) {
    this.ibanTo = ibanTo;
    return this;
  }

  /**
   * Get ibanTo
   * @return ibanTo
   **/
  @Schema(example = "NLKPINHO0039923593", required = true, description = "")
      @NotNull

    public String getIbanTo() {
    return ibanTo;
  }

  public void setIbanTo(String ibanTo) {
    this.ibanTo = ibanTo;
  }

  public Transaction amount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
   **/
  @Schema(example = "104.99", required = true, description = "")
      @NotNull

    @Valid
    public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Transaction userPerforming(Integer userPerforming) {
    this.userPerforming = userPerforming;
    return this;
  }

  /**
   * Get userPerforming
   * @return userPerforming
   **/
  @Schema(example = "3", required = true, description = "")
      @NotNull

    public Integer getUserPerforming() {
    return userPerforming;
  }

  public void setUserPerforming(Integer userPerforming) {
    this.userPerforming = userPerforming;
  }

  public Transaction transactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
   **/
  @Schema(required = true, description = "")
      @NotNull

    public TransactionTypeEnum getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction transaction = (Transaction) o;
    return Objects.equals(this.ibanFrom, transaction.ibanFrom) &&
        Objects.equals(this.ibanTo, transaction.ibanTo) &&
        Objects.equals(this.amount, transaction.amount) &&
        Objects.equals(this.userPerforming, transaction.userPerforming) &&
        Objects.equals(this.transactionType, transaction.transactionType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ibanFrom, ibanTo, amount, userPerforming, transactionType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Transaction {\n");
    
    sb.append("    ibanFrom: ").append(toIndentedString(ibanFrom)).append("\n");
    sb.append("    ibanTo: ").append(toIndentedString(ibanTo)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    userPerforming: ").append(toIndentedString(userPerforming)).append("\n");
    sb.append("    transactionType: ").append(toIndentedString(transactionType)).append("\n");
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
