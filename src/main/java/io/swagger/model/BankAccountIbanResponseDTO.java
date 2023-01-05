package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

/**
 * BankAccountIbanResponseDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")


public class BankAccountIbanResponseDTO   {
  @JsonProperty("Iban")
  private String iban = null;

  @JsonProperty("OwnersFirstName")
  private String ownersFirstName = null;

  @JsonProperty("OwnersLastName")
  private String ownersLastName = null;

  @JsonProperty("OwnerId")
  private Integer ownerId = null;

  @Schema(example = "1", required = true, description = "")
  @NotNull

  public Integer getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Integer ownerId) {
    this.ownerId = ownerId;
  }

  @Schema(example = "Peter", required = true, description = "")
  @NotNull

  public String getOwnersFirstName() {
    return ownersFirstName;
  }

  public void setOwnersFirstName(String ownersFirstName) {
    this.ownersFirstName = ownersFirstName;
  }

  @Schema(example = "Kluivert", required = true, description = "")
  @NotNull

  public String getOwnersLastName() {
    return ownersLastName;
  }

  public void setOwnersLastName(String ownersLastName) {
    this.ownersLastName = ownersLastName;
  }
  public BankAccountIbanResponseDTO iban(String iban) {
    this.iban = iban;
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BankAccountIbanResponseDTO bankAccountIbanResponseDTO = (BankAccountIbanResponseDTO) o;
    return Objects.equals(this.iban, bankAccountIbanResponseDTO.iban);
  }

  @Override
  public int hashCode() {
    return Objects.hash(iban);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BankAccountIbanResponseDTO {\n");
    
    sb.append("    IBAN: ").append(toIndentedString(iban)).append("\n");
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
