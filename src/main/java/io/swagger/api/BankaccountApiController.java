package io.swagger.api;

import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.BankAccountService;
import io.swagger.service.UserService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
@CrossOrigin
public class BankaccountApiController implements BankaccountApi {

    private static final Logger log = LoggerFactory.getLogger(BankaccountApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public BankaccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<BankAccount>> bankaccountInitBankAccountsUserIdPost(@Parameter(in = ParameterIn.PATH, description = "user id for the targeted user", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        //if the user role is customer and is trying to execute this API call for another user disallow it
        if (userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            List<BankAccount> resultList = bankAccountService.CreateCurrentAndSavingsAccountForUserId(userId);

            if (resultList.size() < 2) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            else return ResponseEntity.status(HttpStatus.OK).body(resultList);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> allBankAccounts = bankAccountService.GetAllBankAccounts();

        return ResponseEntity.status(HttpStatus.OK).body(allBankAccounts);
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<BankAccount>> getBankAccountByIBAN(@Parameter(in = ParameterIn.PATH, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN) {
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(IBAN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            BankAccount searchResult = bankAccountService.GetBankAccountByIban(IBAN);

            if(searchResult == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            else return ResponseEntity.status(HttpStatus.OK).body(List.of(searchResult));
        }
    }

//    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<BankAccountIbanResponseDTO>> getIbanByFullName(@Parameter(in = ParameterIn.PATH, description = "fullname from which the iban has to be loaded", required=true, schema=@Schema()) @PathVariable("fullName") String fullName) {
        //no preauthorized needed for crossplatform usage, no harm can be done with finding somebody's IBAN
        List<BankAccountIbanResponseDTO> results = bankAccountService.GetCurrentIbansByFullName(fullName);

        if(results == null) {
            //bad input
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
        else if(results.size() == 0) {
            //not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(results);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TotalBalanceResponseDTO>> getTotalBalanceForAccounts(@Parameter(in = ParameterIn.PATH, description = "user id from which the total balance has to be loaded for", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            TotalBalanceResponseDTO result = bankAccountService.GetTotalBalanceByUserId(userId);
            if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            else return ResponseEntity.status(HttpStatus.OK).body(List.of(result));
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<BankAccount>> postBankAccount(@Parameter(in = ParameterIn.DEFAULT, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @Valid @RequestBody NewBankAccountRequestDTO body) {
        BankAccount result = bankAccountService.CreateBankAccountByRequestBody(body);

        if(result == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else return ResponseEntity.status(HttpStatus.OK).body(List.of(result));
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<BankAccount>> putBankAccount(@Parameter(in = ParameterIn.DEFAULT, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @Valid @RequestBody BankAccountRequestDTO body) {
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(body.getIban())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            BankAccount updateResult = bankAccountService.UpdateBankAccountByRequestBody(body);

            if(updateResult == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            else if(updateResult.getIban() == body.getIban() && updateResult.getAbsoluteLimit() == body.getAbsoluteLimit()
                    && updateResult.getAccountStatus().equals(body.getAccountStatus()) &&  updateResult.getAccountType().equals(body.getAccountType())) {
                //all the info from the updated results matches the input info
                return ResponseEntity.status(HttpStatus.OK).body(List.of(updateResult));
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body(List.of(updateResult));
            }
        }


    }

}
