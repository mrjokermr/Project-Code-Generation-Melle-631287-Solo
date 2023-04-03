package io.swagger.api;


import io.swagger.mapper.TransactionMapper;
import io.swagger.model.DepositOrWithdrawRequestDTO;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Transaction;
import io.swagger.model.TransactionRequestDTO;
import io.swagger.model.TransactionResponseDTO;
import io.swagger.service.BankAccountService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<TransactionResponseDTO>> depositTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for withdrawing or depositing money", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdrawRequestDTO body) {
        //withdraw/deposit actions can only be performed by the employee/customer him-/herself so the targeted iban has to be owned by the authorized user
        //CustomerIsExecutingApiCallThatIsNotTargetedForHimself function can't be used because it allows a customer to perform this transaction
        if(userService.AuthUserIsOwnerOfThisIban(body.getIban())) {
            Transaction newTransaction = transactionService.WithdrawOrDepositMoney(body, Transaction.TransactionTypeEnum.DEPOSIT);

            if(newTransaction != null)
                return ResponseEntity.status(HttpStatus.OK).body(List.of(TransactionMapper.TransactionToResponseDTO(newTransaction)));
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")

    public ResponseEntity<List<TransactionResponseDTO>> getTransaction(@Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate, @Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date toDate, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be bigger than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountBiggerThan", required = false) Double amountBiggerThan, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be smaller than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountSmallerThan", required = false) Double amountSmallerThan, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be equal to the given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountEquals", required = false) Double amountEquals, @Parameter(in = ParameterIn.QUERY, description = "Provide a iban to get transactions related with that iban",schema=@Schema()) @Valid @RequestParam(value = "historyWithIban", required = false) String historyWithIban) {
        //get transactions by logged in user
        Integer userId = userService.GetCurrentAuthorizedUserId();
        List<Transaction> allUserTransactions = null;

        if(userId == null || userId < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else {
            //set the allUserTransactions based on the optional parameters
            //optional parameters are: combination [fromDate, toDate] , [(amountBiggerThan || or && amountSmallerThan) || amountEquals]
            if(fromDate != null || toDate != null) {
                //one of the filters is set
                allUserTransactions = transactionService.GetTransactionsById(userId,fromDate, toDate);
            }
            else if(amountBiggerThan != null || amountSmallerThan != null || amountEquals != null) {
                //one of these filters is set
                if(amountEquals == null) allUserTransactions = transactionService.GetTransactionsById(userId,amountBiggerThan,amountSmallerThan);
                else allUserTransactions = transactionService.GetTransactionsById(userId,amountEquals);
            }
            else {
                //no filter is set so return all unfiltered transactions
                allUserTransactions = transactionService.GetTransactionsById(userId);
            }

            //filter historyWithIban option, only keep transactions in the list that contain the historyWithIban at the fromIban or toIban
            if(historyWithIban != null) {
                allUserTransactions = transactionService.FilterListTransactionsRelatedToIbanOnly(allUserTransactions, historyWithIban);
            }

            //return the info
            if(allUserTransactions == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            else if(allUserTransactions.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(TransactionMapper.TransactionsToTransactionsResponseList(allUserTransactions));
            else {
                return ResponseEntity.status(HttpStatus.OK).body(TransactionMapper.TransactionsToTransactionsResponseList(allUserTransactions));
            }
        }

    }

    public ResponseEntity<List<TransactionResponseDTO>> getTransactionByIban(@Parameter(in = ParameterIn.PATH, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN, @Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "fromDate", required = false) Date fromDate, @Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "toDate", required = false) Date toDate, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be bigger than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountBiggerThan", required = false) Double amountBiggerThan, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be smaller than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountSmallerThan", required = false) Double amountSmallerThan, @Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be equal to the given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountEquals", required = false) Double amountEquals, @Parameter(in = ParameterIn.QUERY, description = "Provide a Iban to get transactions related to that iban" ,schema=@Schema()) @Valid @RequestParam(value = "historyWithIban", required = false) String historyWithIban) {
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(IBAN)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            List<Transaction> allUserTransactions = null;

            if(IBAN == null || IBAN == "") return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            else {
                //set the allUserTransactions based on the optional parameters
                //optional parameters are: combination [fromDate, toDate] , [(amountBiggerThan || or && amountSmallerThan) || amountEquals]
                if(fromDate != null || toDate != null) {
                    //one of the filters is set
                    allUserTransactions = transactionService.GetTransactionsByIban(IBAN,fromDate, toDate);
                }
                else if(amountBiggerThan != null || amountSmallerThan != null || amountEquals != null) {
                    //one of these filters is set
                    if(amountEquals == null) allUserTransactions = transactionService.GetTransactionsByIban(IBAN,amountBiggerThan,amountSmallerThan);
                    else allUserTransactions = transactionService.GetTransactionsByIban(IBAN,amountEquals);
                }
                else {
                    //no filter is set so return all unfiltered transactions
                    allUserTransactions = transactionService.GetTransactionsByIban(IBAN);
                }

                //filter historyWithIban option, only keep transactions in the list that contain the historyWithIban at the fromIban or toIban
                if(historyWithIban != null) {
                    allUserTransactions = transactionService.FilterListTransactionsRelatedToIbanOnly(allUserTransactions, historyWithIban);
                }


                //return the info
                if(allUserTransactions == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                else if(allUserTransactions.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(TransactionMapper.TransactionsToTransactionsResponseList(allUserTransactions));
                else {
                    return ResponseEntity.status(HttpStatus.OK).body(TransactionMapper.TransactionsToTransactionsResponseList(allUserTransactions));
                }
            }

        }
    }


    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionByUserId(@Parameter(in = ParameterIn.PATH, description = "user id of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        //check if user is employee it can execute this command for any userID , if a CUSTOMER performs this code it has to be called for this own userID
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            //get transactions by logged in user
            if (userId == null || userId < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

            List<Transaction> allUserTransactions = transactionService.GetTransactionsById(userId);
            //return the info
            if (allUserTransactions.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(TransactionMapper.TransactionsToTransactionsResponseList(allUserTransactions));
            else {
                return ResponseEntity.status(HttpStatus.OK).body(TransactionMapper.TransactionsToTransactionsResponseList(allUserTransactions));
            }
        }
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity postTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for creating a transaction", required=true, schema=@Schema()) @Valid @RequestBody TransactionRequestDTO body) {
        //if user is CUSTOMER the ibanFROM has to be his own
        //changed the methods return type to repsonse entity to give some feedback
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(body.getIbanFrom())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            Transaction result = transactionService.CreateANewTransaction(TransactionMapper.TransactionRequestToTransactionResponseDTO(body,userService.GetCurrentAuthorizedUserId()));

            if(result == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionService.GetReasonWhyTransactionFailed());
            else return ResponseEntity.status(HttpStatus.OK).body(List.of(result));
        }

    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<TransactionResponseDTO>> withdrawTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for withdrawing or depositing money", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdrawRequestDTO body) {
        //withdraw/deposit actions can only be performed by the employee/customer him-/herself so the targeted iban has to be owned by the authorized user
        //CustomerIsExecutingApiCallThatIsNotTargetedForHimself function can't be used because it allows a customer to perform this transaction
        if(userService.AuthUserIsOwnerOfThisIban(body.getIban())) {
            Transaction newTransaction = transactionService.WithdrawOrDepositMoney(body, Transaction.TransactionTypeEnum.WITHDRAW);

            if(newTransaction != null)
                return ResponseEntity.status(HttpStatus.OK).body(List.of(TransactionMapper.TransactionToResponseDTO(newTransaction)));
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

}
