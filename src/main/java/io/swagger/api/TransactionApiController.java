package io.swagger.api;


import io.swagger.model.DepositOrWithdrawRequestDTO;
import java.util.Date;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.TransactionResponseDTO;
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
public class TransactionApiController implements TransactionApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<List<TransactionResponseDTO>> depositTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for withdrawing or depositing money", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdrawRequestDTO body) {

        return new ResponseEntity<List<TransactionResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionResponseDTO>> getTransactionByIban(@Parameter(in = ParameterIn.PATH, description = "iban of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("IBAN") String IBAN,@Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "fromDate", required = false) Date fromDate,@Parameter(in = ParameterIn.QUERY, description = "set a date from which the transactions have to be loaded" ,schema=@Schema()) @Valid @RequestParam(value = "toDate", required = false) Date toDate,@Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be bigger than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountBiggerThan", required = false) Double amountBiggerThan,@Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be smaller than given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountSmallerThan", required = false) Double amountSmallerThan,@Parameter(in = ParameterIn.QUERY, description = "the transaction amount has to be equal to the given value" ,schema=@Schema()) @Valid @RequestParam(value = "amountEquals", required = false) Double amountEquals) {

        return new ResponseEntity<List<TransactionResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionResponseDTO>> getTransactionByUserId(@Parameter(in = ParameterIn.PATH, description = "user id of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("userId") String userId) {


        return new ResponseEntity<List<TransactionResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionResponseDTO>> postTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for creating a transaction", required=true, schema=@Schema()) @Valid @RequestBody TransactionResponseDTO body) {

        return new ResponseEntity<List<TransactionResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<TransactionResponseDTO>> withdrawTransaction(@Parameter(in = ParameterIn.DEFAULT, description = "transaction body for withdrawing or depositing money", required=true, schema=@Schema()) @Valid @RequestBody DepositOrWithdrawRequestDTO body) {

        return new ResponseEntity<List<TransactionResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
