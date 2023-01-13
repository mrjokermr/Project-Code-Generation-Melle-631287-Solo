/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.36).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package io.swagger.api;

import io.swagger.model.BankAccount;
import io.swagger.model.BankAccountIbanResponseDTO;
import io.swagger.model.BankAccountRequestDTO;
import io.swagger.model.NewBankAccountRequestDTO;
import io.swagger.model.TotalBalanceResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CookieValue;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")
@Validated
public interface BankaccountApi {

    @Operation(summary = "create savings and current bankaccounts for desired user", description = "creates a savings and/or current bank account if user has not got one yet, and returns the created bankaccount(s)", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "returns the created bank accounts", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "401", description = "not allowed to perform this action") })
    @RequestMapping(value = "/bankaccounts/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<List<BankAccount>> bankaccountInitBankAccountsUserIdPost(@Parameter(in = ParameterIn.PATH, description = "user id for the targeted user", required=true, schema=@Schema()) @PathVariable("userId") Integer userId);


    @Operation(summary = "get all bank accounts info if allowed", description = "Get Bank account information if you have the right rights ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "search results matching criteria", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "401", description = "not allowed to load information") })
    @RequestMapping(value = "/bankaccounts",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<BankAccount>> getAllBankAccounts();


    @Operation(summary = "get bank account info by IBAN", description = "Get Bank account information if you have the right rights ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "search results matching criteria", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "401", description = "not allowed to load information for this IBAN") })
    @RequestMapping(value = "/bankaccount/{IBAN}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<BankAccount>> getBankAccountByIBAN(@Parameter(in = ParameterIn.PATH, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN);


    @Operation(summary = "get IBAN(s) by full name", description = "Be aware that persons can have the same full name and the result will lists multiple current IBANs with extra info", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "search results matching criteria", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccountIbanResponseDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "401", description = "not allowed to load this information") })
    @RequestMapping(value = "/bankaccounts/{fullName}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<BankAccountIbanResponseDTO>> getIbanByFullName(@Parameter(in = ParameterIn.PATH, description = "fullname from which the iban has to be loaded", required=true, schema=@Schema()) @PathVariable("fullName") String fullName);


    @Operation(summary = "total balance from all bankaccounts", description = "", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "search results matching criteria", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TotalBalanceResponseDTO.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "401", description = "not allowed to load information for this user") })
    @RequestMapping(value = "/bankaccount/totalBalance/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<TotalBalanceResponseDTO>> getTotalBalanceForAccounts(@Parameter(in = ParameterIn.PATH, description = "user id from which the total balance has to be loaded for", required=true, schema=@Schema()) @PathVariable("userId") Integer userId);


    @Operation(summary = "create a new bankaccount", description = "Post Bank account information if you have the right rights ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "succesfully changed information", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "401", description = "not allowed to change information for this IBAN") })
    @RequestMapping(value = "/bankaccount",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.POST)
    ResponseEntity<List<BankAccount>> postBankAccount(@Parameter(in = ParameterIn.DEFAULT, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @Valid @RequestBody NewBankAccountRequestDTO body);


    @Operation(summary = "update bank account info by IBAN", description = "Put Bank account information if you have the right rights ", security = {
        @SecurityRequirement(name = "bearerAuth")    }, tags={ "bankaccount" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "succesfully changed information", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = BankAccount.class)))),
        
        @ApiResponse(responseCode = "400", description = "bad input parameter"),
        
        @ApiResponse(responseCode = "401", description = "not allowed to change information for this IBAN") })
    @RequestMapping(value = "/bankaccount",
        produces = { "application/json" }, 
        consumes = { "application/json" }, 
        method = RequestMethod.PUT)
    ResponseEntity<List<BankAccount>> putBankAccount(@Parameter(in = ParameterIn.DEFAULT, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @Valid @RequestBody BankAccountRequestDTO body);

}

