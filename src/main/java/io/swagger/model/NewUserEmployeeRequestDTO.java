package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.UserAccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * NewUserEmployeeRequestDTO
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")


public class NewUserEmployeeRequestDTO   {
  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("username")
  private String username = null;

  @JsonProperty("userType")
  private UserAccountType userType = null;

  @JsonProperty("password")
  private String password = null;

  @JsonProperty("dayLimit")
  private Integer dayLimit = null;

  @JsonProperty("transactionLimit")
  private Double transactionLimit = null;

  public NewUserEmployeeRequestDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * Get firstName
   * @return firstName
   **/
  @Schema(example = "jantje", required = true, description = "")
      @NotNull

    public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public NewUserEmployeeRequestDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * Get lastName
   * @return lastName
   **/
  @Schema(example = "schuurman", required = true, description = "")
      @NotNull

    public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public NewUserEmployeeRequestDTO username(String username) {
    this.username = username;
    return this;
  }

  /**
   * Get username
   * @return username
   **/
  @Schema(example = "jantje93283", required = true, description = "")
      @NotNull

    public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public NewUserEmployeeRequestDTO userType(UserAccountType userType) {
    this.userType = userType;
    return this;
  }

  /**
   * Get userType
   * @return userType
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public UserAccountType getUserType() {
    return userType;
  }

  public void setUserType(UserAccountType userType) {
    this.userType = userType;
  }

  public NewUserEmployeeRequestDTO password(String password) {
    this.password = password;
    return this;
  }

  /**
   * Get password
   * @return password
   **/
  @Schema(example = "adbj23!9edus", required = true, description = "")
      @NotNull

    public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public NewUserEmployeeRequestDTO dayLimit(Integer dayLimit) {
    this.dayLimit = dayLimit;
    return this;
  }

  /**
   * Get dayLimit
   * @return dayLimit
   **/
  @Schema(example = "18", description = "")
  
    public Integer getDayLimit() {
    return dayLimit;
  }

  public void setDayLimit(Integer dayLimit) {
    this.dayLimit = dayLimit;
  }

  public NewUserEmployeeRequestDTO transactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * Get transactionLimit
   * @return transactionLimit
   **/
  @Schema(example = "1000", description = "")
  
    @Valid
    public Double getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewUserEmployeeRequestDTO newUserEmployeeRequestDTO = (NewUserEmployeeRequestDTO) o;
    return Objects.equals(this.firstName, newUserEmployeeRequestDTO.firstName) &&
        Objects.equals(this.lastName, newUserEmployeeRequestDTO.lastName) &&
        Objects.equals(this.username, newUserEmployeeRequestDTO.username) &&
        Objects.equals(this.userType, newUserEmployeeRequestDTO.userType) &&
        Objects.equals(this.password, newUserEmployeeRequestDTO.password) &&
        Objects.equals(this.dayLimit, newUserEmployeeRequestDTO.dayLimit) &&
        Objects.equals(this.transactionLimit, newUserEmployeeRequestDTO.transactionLimit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstName, lastName, username, userType, password, dayLimit, transactionLimit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NewUserEmployeeRequestDTO {\n");
    
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    userType: ").append(toIndentedString(userType)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
    sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
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
