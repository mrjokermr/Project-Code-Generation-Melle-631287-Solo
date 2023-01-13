package io.swagger.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.Security.PasswordEncoderHolder;
import io.swagger.model.UserAccountType;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * User
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")

@Entity
@Table(name = "users")
public class User   {
  @JsonProperty("id")
  @Id
  @GeneratedValue
  private Integer id = null;
  @JsonProperty("firstName")
  private String firstName = null;
  @JsonProperty("lastName")
  private String lastName = null;
  @JsonProperty("username")
  private String username = null;
  @JsonProperty("password")
  private String password = null;
  @JsonProperty("userType")
  private UserAccountType userType = null;
  @JsonProperty("dayLimit")
  private Integer dayLimit = null;
  @JsonProperty("transactionLimit")
  private Double transactionLimit = null;
  @JsonProperty("creationDate")
  private Date creationDate = null;

  public User id(Integer id) {
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

  public User firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public List<UserAccountType> GetUserAccountTypeAsList() {
      return List.of(this.userType);
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

  public User lastName(String lastName) {
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

  public User username(String username) {
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

  public User password(String password) {
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

  public void setEncryptedPassword(String password) {
    this.password = PasswordEncoderHolder.passwordEncoder.encode(password);
  }

  public User userType(UserAccountType userType) {
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

  public User dayLimit(Integer dayLimit) {
    this.dayLimit = dayLimit;
    return this;
  }

  /**
   * Get dayLimit
   * @return dayLimit
   **/
  @Schema(example = "18", required = true, description = "")
      @NotNull

    public Integer getDayLimit() {
    return dayLimit;
  }

  public void setDayLimit(Integer dayLimit) {
    this.dayLimit = dayLimit;
  }

  public User transactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
    return this;
  }

  /**
   * Get transactionLimit
   * @return transactionLimit
   **/
  @Schema(example = "1000", required = true, description = "")
      @NotNull

    @Valid
    public Double getTransactionLimit() {
    return transactionLimit;
  }

  public void setTransactionLimit(Double transactionLimit) {
    this.transactionLimit = transactionLimit;
  }

  public User creationDate(Date creationDate) {
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
    User user = (User) o;
    return Objects.equals(this.id, user.id) &&
        Objects.equals(this.firstName, user.firstName) &&
        Objects.equals(this.lastName, user.lastName) &&
        Objects.equals(this.username, user.username) &&
        Objects.equals(this.password, user.password) &&
        Objects.equals(this.userType, user.userType) &&
        Objects.equals(this.dayLimit, user.dayLimit) &&
        Objects.equals(this.transactionLimit, user.transactionLimit) &&
        Objects.equals(this.creationDate, user.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, username, password, userType, dayLimit, transactionLimit, creationDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class User {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    userType: ").append(toIndentedString(userType)).append("\n");
    sb.append("    dayLimit: ").append(toIndentedString(dayLimit)).append("\n");
    sb.append("    transactionLimit: ").append(toIndentedString(transactionLimit)).append("\n");
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
