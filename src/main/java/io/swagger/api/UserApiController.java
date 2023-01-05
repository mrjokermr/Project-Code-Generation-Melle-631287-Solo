package io.swagger.api;

import io.swagger.mapper.UserMapper;
import io.swagger.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2022-12-30T12:05:52.189Z[GMT]")
@RestController
@Controller
@CrossOrigin
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

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<UserResponseDTO>> getUserInfo() {
        User currentUser = userService.GetCurrentUserByAuthorization();

        if(currentUser != null) return ResponseEntity.status(HttpStatus.OK).body(List.of(UserMapper.UserToUserResponseDTO(currentUser)));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserResponseDTO>> getAllUserInfo(@Parameter(in = ParameterIn.QUERY, description = "set true or false for desired result" ,schema=@Schema()) @Valid @RequestParam(value = "onlyWithoutBankAccounts", required = false) Boolean onlyWithoutBankAccounts) {
        //returns a users list mapped so that the response doens't include a hashed password
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.UserListToUserResponseDTOList(userService.GetAllUsers(onlyWithoutBankAccounts)));
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<UserResponseDTO>> getUserInfoById(@Parameter(in = ParameterIn.PATH, description = "the user id of which information has to be loaded", required=true, schema=@Schema()) @PathVariable("userId") Integer userId) {
        if(userService.CustomerIsExecutingApiCallThatIsNotTargetedForHimself(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        else {
            UserResponseDTO foundUser = UserMapper.UserToUserResponseDTO(userService.GetUserInfoById(userId));
            if(foundUser ==  null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            else return ResponseEntity.status(HttpStatus.OK).body(List.of(foundUser));
        }
    }

    //Needs to be kept public for everyone to access
    public ResponseEntity<List<UserResponseDTO>> postRegisterUser(@Parameter(in = ParameterIn.DEFAULT, description = "new user body for registering the user", required=true, schema=@Schema()) @Valid @RequestBody NewUserRequestDTO body) {
        //A user registers him/her-self
        User result = userService.RegisterUserAsCustomer(body);

        if(result != null) return ResponseEntity.status(HttpStatus.OK).body(List.of(UserMapper.UserToUserResponseDTO(result)));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<UserResponseDTO>> postUser(@Parameter(in = ParameterIn.DEFAULT, description = "new user body for registering the user", required=true, schema=@Schema()) @Valid @RequestBody NewUserEmployeeRequestDTO body) {
        User result = userService.RegisterUserByAEmployee(body);

        if(result != null) return ResponseEntity.status(HttpStatus.OK).body(List.of(UserMapper.UserToUserResponseDTO(result)));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CUSTOMER')")
    public ResponseEntity<List<UserResponseDTO>> putUser(@Parameter(in = ParameterIn.DEFAULT, description = "updated info for this user", required=true, schema=@Schema()) @Valid @RequestBody UpdateUserRequestDTO body) {
        User result = userService.UpdateUserInfoByBody(body);

        if (result != null) return ResponseEntity.status(HttpStatus.OK).body(List.of(UserMapper.UserToUserResponseDTO(result)));
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    //Needs to be kept public for everyone to access
    public ResponseEntity<List<LoginResponseDTO>> userLogin(@Parameter(in = ParameterIn.DEFAULT, description = "required login info", required=true, schema=@Schema()) @Valid @RequestBody LoginRequestDTO body) {

        LoginResponseDTO responseDTO = userService.Login(body.getUsername(), body.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(List.of(responseDTO));

        //return new ResponseEntity<List<LoginResponseDTO>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
