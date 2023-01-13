package JUnit_Tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.Swagger2SpringBoot;
import io.swagger.model.*;
import io.swagger.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTests {

    @LocalServerPort
    private int port;

    @Autowired
    UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();


//    Test methods are:
//            • Always annotated with @Test
//            • Always void
//            • Named properly and that name can be a long one
//            • Assert that:
//            • the response equals something we expect
//            • Something in the response is true or false
//                We often work with the response body, or responsestatus.

    private TestRestTemplate template = new TestRestTemplate();

    private String createFullUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    private HttpHeaders getHttpHeader() {
        return new HttpHeaders();
    }

    private HttpHeaders GetHttpheaderWithBearerTokenForCustomer() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(new LoginRequestDTO("FerdinantHogewaard2022","geheim"), getHttpHeader());
        ResponseEntity<String> response = template.exchange(createFullUrl("/user/login"), HttpMethod.POST, entity, String.class);

        ResponseEntity<String> test = response;

        if(!response.getStatusCode().isError()) {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<List<LoginResponseDTO>> typeRef = new TypeReference<List<LoginResponseDTO>>(){};
            List<LoginResponseDTO> loginResponseDTO = mapper.readValue(response.getBody(), typeRef);
            headers.add("Authorization","Bearer " + loginResponseDTO.get(0).getToken());

            return headers;
        }
        else {
            return null;
        }
    }

    private HttpHeaders GetHttpheaderWithBearerTokenForEmployee() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(new LoginRequestDTO("CarolinaVeldman2022","geheim"), getHttpHeader());
        ResponseEntity<String> response = template.exchange(createFullUrl("/user/login"), HttpMethod.POST, entity, String.class);

        ResponseEntity<String> test = response;

        if(!response.getStatusCode().isError()) {
            TypeReference<List<LoginResponseDTO>> typeRef = new TypeReference<List<LoginResponseDTO>>(){};
            List<LoginResponseDTO> loginResponseDTO = objectMapper.readValue(response.getBody(), typeRef);
            headers.add("Authorization","Bearer " + loginResponseDTO.get(0).getToken());

            return headers;
        }
        else {
            return null;
        }
    }

    @Test
    public void notAllowedToGetAllUsersBecauseNoAuthTokenIsProvided() {
        HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());

        ResponseEntity<String> response = template.exchange(createFullUrl("/users"), HttpMethod.GET, entity, String.class);

        Assert.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void loginFunctionalityIsWorking() throws IOException {
        Assert.assertTrue(GetHttpheaderWithBearerTokenForCustomer() != null);
    }

    @Test
    public void userCanLoadHisOwnInformation() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/user"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void userWithCustomerRightsCantLoadAllUsers() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/users"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue( Integer.parseInt(response.getStatusCode().toString().split(" ")[0]) >= 300);
    }

    @Test
    public void userWithEmployeeRightsCanLoadAllUsers() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/users"), HttpMethod.GET, entity, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void customerCanUpdateOwnInformation() throws IOException {
        UpdateUserRequestDTO updateUserRequestDTO = new UpdateUserRequestDTO();

        //BearerTokenCustomer user info: id = 6, firstName = Ferdinant, lastName = Hogewaard, username = FerdinantHogewaard2022,
        //userType = Customer, dayLimit default = 100, transactionLimit = 10000.0
        updateUserRequestDTO.setTargetUserId(6);
        updateUserRequestDTO.setDayLimit(200);
        updateUserRequestDTO.setFirstName("Ferdinant");
        updateUserRequestDTO.setLastName("Hogewaard");
        updateUserRequestDTO.setTransactionLimit(10000.0);

        HttpEntity<UpdateUserRequestDTO> entity = new HttpEntity<>(updateUserRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/user"), HttpMethod.PUT, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            TypeReference<List<UserResponseDTO>> typeRef = new TypeReference<List<UserResponseDTO>>(){};
            List<UserResponseDTO> userUpdateResponseDTO = objectMapper.readValue(response.getBody(), typeRef);

            User updatedUser = userService.GetUserInfoById(6);
            Assert.assertTrue(updatedUser.getDayLimit() == 200);
        }
        else Assert.fail();
    }

    @Test
    public void customerNotAllowedToChangeOtherCustomersInfo() throws IOException {
        UpdateUserRequestDTO updateUserRequestDTO = new UpdateUserRequestDTO();

        //BearerTokenCustomer user info: id = 6, firstName = Ferdinant, lastName = Hogewaard, username = FerdinantHogewaard2022,
        //userType = Customer, dayLimit default = 100, transactionLimit = 10000.0
        updateUserRequestDTO.setTargetUserId(7); //other id then him/her-self
        updateUserRequestDTO.setDayLimit(200);
        updateUserRequestDTO.setFirstName("Ferdinant");
        updateUserRequestDTO.setLastName("Hogewaard");
        updateUserRequestDTO.setTransactionLimit(10000.0);

        HttpEntity<UpdateUserRequestDTO> entity = new HttpEntity<>(updateUserRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/user"), HttpMethod.PUT, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void customerNotAllowedToLoadOtherUserInfo() throws IOException {

        HttpEntity<UpdateUserRequestDTO> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        //logged in user id = 6
        ResponseEntity<String> response = template.exchange(createFullUrl("/user/7"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void anyoneCanRegisterAnAccount() throws IOException {
        NewUserRequestDTO newUserRequestDTO = new NewUserRequestDTO();

        newUserRequestDTO.setFirstName("NewPerson");
        newUserRequestDTO.setPassword("geheim");
        newUserRequestDTO.setLastName("Schuurman");
        newUserRequestDTO.setUsername("NewPersonSchuurman2023");
        newUserRequestDTO.setTransactionLimit(10000.0);
        newUserRequestDTO.setDayLimit(100);

        HttpEntity<NewUserRequestDTO> entity = new HttpEntity<>(newUserRequestDTO, new HttpHeaders());

        ResponseEntity<String> response = template.exchange(createFullUrl("/user/register"), HttpMethod.POST, entity, String.class);

        Assert.assertTrue( response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void employeeCanRegisterAnotherUser() throws IOException {
        NewUserEmployeeRequestDTO newUserEmployeeRequestDTO = new NewUserEmployeeRequestDTO();

        newUserEmployeeRequestDTO.setUserType(UserAccountType.ROLE_CUSTOMER);
        newUserEmployeeRequestDTO.setDayLimit(100);
        newUserEmployeeRequestDTO.setFirstName("CreatedByEmployee");
        newUserEmployeeRequestDTO.setPassword("geheim");
        newUserEmployeeRequestDTO.setTransactionLimit(10000.0);
        newUserEmployeeRequestDTO.setUsername("CreatedByEmployeeUganda2023");
        newUserEmployeeRequestDTO.setLastName("Uganda");

        HttpEntity<NewUserEmployeeRequestDTO> entity = new HttpEntity<>(newUserEmployeeRequestDTO, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/user"), HttpMethod.POST, entity, String.class);

        Assert.assertTrue( response.getStatusCode() == HttpStatus.OK);
    }
    
}
