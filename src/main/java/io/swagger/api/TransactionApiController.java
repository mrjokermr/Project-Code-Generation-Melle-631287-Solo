package io.swagger.api;


import io.swagger.mapper.TransactionMapper;
import io.swagger.model.DepositOrWithdrawRequestDTO;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Transaction;
import io.swagger.model.TransactionResponseAndRequestDTO;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")
@RestController
@CrossOrigin
public class TransactionApiController implements TransactionApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<TransactionResponseAndRequestDTO>> depositTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for withdrawing or depositing money", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdrawRequestDTO body) {

        return new ResponseEntity<List<TransactionResponseAndRequestDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionResponseAndRequestDTO>> getTransactionByIban(@Parameter(in = ParameterIn.PATH, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "fromDate", required = false) Date fromDate, @Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "toDate", required = false) Date toDate, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be bigger than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountBiggerThan", required = false) Double amountBiggerThan, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be smaller than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountSmallerThan", required = false) Double amountSmallerThan, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be equal to the given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountEquals", required = false) Double amountEquals) {

        return new ResponseEntity<List<TransactionResponseAndRequestDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionResponseAndRequestDTO>> getTransactionByUserId(@Parameter(in = ParameterIn.PATH, description = "user id of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("userId") String userId) {


        return new ResponseEntity<List<TransactionResponseAndRequestDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<Transaction>> postTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for creating a transaction", required=true, schema=@Schema()) @Valid @RequestBody TransactionResponseAndRequestDTO body) {
        //if user is CUSTOMER the ibanFROM has to be his own
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(null,body.getIbanFrom())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            Transaction result = transactionService.CreateANewTransaction(body);

            if(result == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            else return ResponseEntity.status(HttpStatus.OK).body(List.of(result));
        }

    }

    public ResponseEntity<List<TransactionResponseAndRequestDTO>> withdrawTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for withdrawing or depositing money", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdrawRequestDTO body) {

        return new ResponseEntity<List<TransactionResponseAndRequestDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
