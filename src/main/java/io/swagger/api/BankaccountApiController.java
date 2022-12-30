package io.swagger.api;

import io.swagger.model.BankAccount;
import io.swagger.model.BankAccountIbanResponseDTO;
import io.swagger.model.BankAccountRequestDTO;
import io.swagger.model.NewBankAccountRequestDTO;
import io.swagger.model.TotalBalanceResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")
@RestController
public class BankaccountApiController implements BankaccountApi {

    private static final Logger log = LoggerFactory.getLogger(BankaccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public BankaccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<BankAccount>> bankaccountInitBankAccountsUserIdPost(@Parameter(in = ParameterIn.PATH, description = "user id for the targeted user", required=true, schema=@Schema()) @PathVariable("userId") String userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccount>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n}, {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccount>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccount>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccount>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n}, {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccount>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccount>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BankAccount>> getBankAccountByIBAN(@Parameter(in = ParameterIn.PATH, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccount>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n}, {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccount>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccount>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BankAccountIbanResponseDTO>> getIbanByFullName(@Parameter(in = ParameterIn.PATH, description = "fullname from which the iban has to be loaded", required=true, schema=@Schema()) @PathVariable("fullName") String fullName) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccountIbanResponseDTO>>(objectMapper.readValue("[ {\n  \"IBAN\" : \"NLHDINHO0235930399\"\n}, {\n  \"IBAN\" : \"NLHDINHO0235930399\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccountIbanResponseDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccountIbanResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TotalBalanceResponseDTO>> getTotalBalanceForAccounts(@Parameter(in = ParameterIn.PATH, description = "user id from which the total balance has to be loaded for", required=true, schema=@Schema()) @PathVariable("userId") String userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<TotalBalanceResponseDTO>>(objectMapper.readValue("[ {\n  \"amount\" : 39404.21\n}, {\n  \"amount\" : 39404.21\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<TotalBalanceResponseDTO>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<TotalBalanceResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BankAccount>> postBankAccount(@Parameter(in = ParameterIn.DEFAULT, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @Valid @RequestBody NewBankAccountRequestDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccount>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n}, {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccount>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccount>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<BankAccount>> putBankAccount(@Parameter(in = ParameterIn.DEFAULT, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @Valid @RequestBody BankAccountRequestDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<BankAccount>>(objectMapper.readValue("[ {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n}, {\n  \"accountStatus\" : \"Savings\",\n  \"IBAN\" : \"NLHDINHO0235930399\",\n  \"balance\" : 1037.56,\n  \"absoluteLimit\" : -300.58,\n  \"accountType\" : \"Active\",\n  \"id\" : 1,\n  \"ownerId\" : 12,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<BankAccount>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<BankAccount>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
