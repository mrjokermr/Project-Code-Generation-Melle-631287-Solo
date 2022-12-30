package io.swagger.api;

import io.swagger.model.LoginRequestDTO;
import io.swagger.model.LoginResponseDTO;
import io.swagger.model.NewUserEmployeeRequestDTO;
import io.swagger.model.NewUserRequestDTO;
import io.swagger.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.stereotype.Controller;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")
@RestController
@Controller
public class UserApiController implements UserApi {

    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private UserService userService;

    @org.springframework.beans.factory.annotation.Autowired
    public UserApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<User>> getAllUserInfo(@Parameter(in = ParameterIn.QUERY, description = "set true or false for desired result" ,schema=@Schema()) @Valid @RequestParam(value = "onlyWithoutBankAccounts", required = false) Boolean onlyWithoutBankAccounts) {


        return ResponseEntity.status(HttpStatus.OK).body(userService.GetAllUsers());
    }

    public ResponseEntity<List<User>> getUserInfoById(@Parameter(in = ParameterIn.PATH, description = "the user id of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("userId") String userId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"firstName\" : \"jantje\",\n  \"lastName\" : \"schuurman\",\n  \"password\" : \"adbj23!9edus\",\n  \"dayLimit\" : 18,\n  \"id\" : 1,\n  \"userType\" : \"Employee\",\n  \"transactionLimit\" : 1000,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\",\n  \"username\" : \"jantje93283\"\n}, {\n  \"firstName\" : \"jantje\",\n  \"lastName\" : \"schuurman\",\n  \"password\" : \"adbj23!9edus\",\n  \"dayLimit\" : 18,\n  \"id\" : 1,\n  \"userType\" : \"Employee\",\n  \"transactionLimit\" : 1000,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\",\n  \"username\" : \"jantje93283\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> postRegisterUser(@Parameter(in = ParameterIn.DEFAULT, description = "new user body for registering the user", required=true, schema=@Schema()) @Valid @RequestBody NewUserRequestDTO body) {


        return ResponseEntity.status(HttpStatus.OK).body(List.of(userService.TestNewUserAdd()));
    }

    public ResponseEntity<List<User>> postUser(@Parameter(in = ParameterIn.DEFAULT, description = "new user body for registering the user", required=true, schema=@Schema()) @Valid @RequestBody NewUserEmployeeRequestDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"firstName\" : \"jantje\",\n  \"lastName\" : \"schuurman\",\n  \"password\" : \"adbj23!9edus\",\n  \"dayLimit\" : 18,\n  \"id\" : 1,\n  \"userType\" : \"Employee\",\n  \"transactionLimit\" : 1000,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\",\n  \"username\" : \"jantje93283\"\n}, {\n  \"firstName\" : \"jantje\",\n  \"lastName\" : \"schuurman\",\n  \"password\" : \"adbj23!9edus\",\n  \"dayLimit\" : 18,\n  \"id\" : 1,\n  \"userType\" : \"Employee\",\n  \"transactionLimit\" : 1000,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\",\n  \"username\" : \"jantje93283\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<User>> putUser(@Parameter(in = ParameterIn.DEFAULT, description = "updated info for this user", required=true, schema=@Schema()) @Valid @RequestBody NewUserRequestDTO body) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<User>>(objectMapper.readValue("[ {\n  \"firstName\" : \"jantje\",\n  \"lastName\" : \"schuurman\",\n  \"password\" : \"adbj23!9edus\",\n  \"dayLimit\" : 18,\n  \"id\" : 1,\n  \"userType\" : \"Employee\",\n  \"transactionLimit\" : 1000,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\",\n  \"username\" : \"jantje93283\"\n}, {\n  \"firstName\" : \"jantje\",\n  \"lastName\" : \"schuurman\",\n  \"password\" : \"adbj23!9edus\",\n  \"dayLimit\" : 18,\n  \"id\" : 1,\n  \"userType\" : \"Employee\",\n  \"transactionLimit\" : 1000,\n  \"creationDate\" : \"2016-08-29T09:12:33.001Z\",\n  \"username\" : \"jantje93283\"\n} ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<User>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<User>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<LoginResponseDTO>> userLogin(@Parameter(in = ParameterIn.DEFAULT, description = "required login info", required=true, schema=@Schema()) @Valid @RequestBody LoginRequestDTO body) {

        LoginResponseDTO responseDTO = userService.Login(body.getUsername(), body.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(List.of(responseDTO));

        //return new ResponseEntity<List<LoginResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
