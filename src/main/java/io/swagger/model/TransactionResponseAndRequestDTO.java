package io.swagger.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Transaction
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")

public class TransactionResponseAndRequestDTO {
  @JsonProperty("ibanFrom")
  private String ibanFrom = null;

  @JsonProperty("ibanTo")
  private String ibanTo = null;

  @JsonProperty("amount")
  private Double amount = null;

  @JsonProperty("userPerforming")
  private Integer userPerforming = null;

  @JsonProperty("creationDate")
  private Date creationDate = null;

  public TransactionResponseAndRequestDTO creationDate(Date creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Gets or Sets transactionType
   */
  @JsonProperty("transactionType")
  private Transaction.TransactionTypeEnum transactionType = null;

  public TransactionResponseAndRequestDTO ibanFrom(String ibanFrom) {
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

  public TransactionResponseAndRequestDTO ibanTo(String ibanTo) {
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

  public TransactionResponseAndRequestDTO amount(Double amount) {
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
    public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public TransactionResponseAndRequestDTO userPerforming(Integer userPerforming) {
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

  public TransactionResponseAndRequestDTO transactionType(Transaction.TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
    return this;
  }

  /**
   * Get transactionType
   * @return transactionType
   **/
  @Schema(required = true, description = "")
      @NotNull

    public Transaction.TransactionTypeEnum getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(Transaction.TransactionTypeEnum transactionType) {
    this.transactionType = transactionType;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TransactionResponseAndRequestDTO transaction = (TransactionResponseAndRequestDTO) o;
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
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
