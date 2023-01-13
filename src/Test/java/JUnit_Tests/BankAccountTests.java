package JUnit_Tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.Swagger2SpringBoot;
import io.swagger.model.*;
import io.swagger.service.BankAccountService;
import io.swagger.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountTests {

    @LocalServerPort
    private int port;

    @Autowired
    UserService userService;

    @Autowired
    BankAccountService bankAccountService;

    private ObjectMapper objectMapper = new ObjectMapper();

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

    private User createNewUserAccount(String username) throws IOException {
        NewUserEmployeeRequestDTO newUserEmployeeRequestDTO = new NewUserEmployeeRequestDTO();

        newUserEmployeeRequestDTO.setUserType(UserAccountType.ROLE_CUSTOMER);
        newUserEmployeeRequestDTO.setDayLimit(100);
        newUserEmployeeRequestDTO.setFirstName("CreatedByEmployee");
        newUserEmployeeRequestDTO.setPassword("geheim");
        newUserEmployeeRequestDTO.setTransactionLimit(10000.0);
        newUserEmployeeRequestDTO.setUsername(username);
        newUserEmployeeRequestDTO.setLastName("Uganda");

        HttpEntity<NewUserEmployeeRequestDTO> entity = new HttpEntity<>(newUserEmployeeRequestDTO, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/user"), HttpMethod.POST, entity, String.class);

        TypeReference<List<User>> typeRef = new TypeReference<List<User>>(){};
        List<User> newUser = objectMapper.readValue(response.getBody(), typeRef);

        return newUser.get(0);
    }

    @Test
    public void customerCanNOTCreateASingleBankAccountForSelf() throws IOException {
        //customer id = 6

        NewBankAccountRequestDTO newBankAccountRequestDTO = new NewBankAccountRequestDTO();
        newBankAccountRequestDTO.setAccountStatus(BankAccountStatus.ACTIVE);
        newBankAccountRequestDTO.setAccountType(BankAccountType.CURRENT);
        newBankAccountRequestDTO.setAbsoluteLimit(200.0);
        newBankAccountRequestDTO.setOwnerId(6);

        HttpEntity<NewBankAccountRequestDTO> entity = new HttpEntity<>(newBankAccountRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount"), HttpMethod.POST, entity, String.class);

        Assert.assertTrue(response.getStatusCode() != HttpStatus.OK);
    }

    @Test
    public void customerCanCreateSavingsAndCurrentAccountForSelf() throws IOException {
        //customer id = 6
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccounts/6"), HttpMethod.POST, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void createCurrentAndSavingsBankaccountForUserByEmployee() throws IOException {
        User newUser = createNewUserAccount("CreatedByEmployeeUganda2023");

        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl(String.format("/bankaccounts/%s",newUser.getId().toString())), HttpMethod.POST, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void getIbanByFullname() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl(String.format("/bankaccounts/%s","Carolina Veldman")), HttpMethod.GET, entity, String.class);

        TypeReference<List<BankAccountIbanResponseDTO>> typeRef = new TypeReference<List<BankAccountIbanResponseDTO>>(){};
        List<BankAccountIbanResponseDTO> ibanResponseDto = objectMapper.readValue(response.getBody(), typeRef);


        if(ibanResponseDto.get(0) != null) {
            Assert.assertTrue(response.getStatusCode() == HttpStatus.OK && Objects.equals(ibanResponseDto.get(0).getOwnersFirstName(), "Carolina") &&
                    Objects.equals(ibanResponseDto.get(0).getOwnersLastName(), "Veldman"));
        }
        else Assert.fail();
    }

    @Test
    public void getTotalBalanceForUserSelf() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount/totalBalance/6"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void customerCantGetTotalBalanceOfOtherUser() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount/totalBalance/7"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() != HttpStatus.OK);
    }

    @Test
    public void employeeCanSeeTotalBalanceOfOtherUser() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount/totalBalance/6"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void customerCanUpdateBankAccountFromSelf() throws IOException {
        BankAccountIbanResponseDTO initialBankAccount = bankAccountService.GetCurrentIbansByFullName("Ferdinant Hogewaard").get(0);

        BankAccountRequestDTO bankAccountRequestDTO = new BankAccountRequestDTO();
        bankAccountRequestDTO.setAccountStatus(BankAccountStatus.INACTIVE);
        bankAccountRequestDTO.setAccountType(BankAccountType.CURRENT);
        bankAccountRequestDTO.setAbsoluteLimit(200.0);
        bankAccountRequestDTO.setIban(initialBankAccount.getIban());

        HttpEntity<BankAccountRequestDTO> entity = new HttpEntity<>(bankAccountRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount"), HttpMethod.PUT, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            BankAccount updatedBankAccount = bankAccountService.GetBankAccountByIban(initialBankAccount.getIban());
            Assert.assertTrue(updatedBankAccount.getAccountStatus() == BankAccountStatus.INACTIVE);
        }
        else Assert.fail();
    }

    @Test
    public void customerCanNOTUpdateBankAccountFromOther() throws IOException {
        BankAccountIbanResponseDTO initialBankAccount = bankAccountService.GetCurrentIbansByFullName("Carolina Veldman").get(0);

        BankAccountRequestDTO bankAccountRequestDTO = new BankAccountRequestDTO();
        bankAccountRequestDTO.setAccountStatus(BankAccountStatus.INACTIVE);
        bankAccountRequestDTO.setAccountType(BankAccountType.CURRENT);
        bankAccountRequestDTO.setAbsoluteLimit(200.0);
        bankAccountRequestDTO.setIban(initialBankAccount.getIban());

        HttpEntity<BankAccountRequestDTO> entity = new HttpEntity<>(bankAccountRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount"), HttpMethod.PUT, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void employeeCanUpdateBankAccountOfOthers() throws IOException {
        BankAccountIbanResponseDTO initialBankAccount = bankAccountService.GetCurrentIbansByFullName("Ferdinant Hogewaard").get(0);

        BankAccountRequestDTO bankAccountRequestDTO = new BankAccountRequestDTO();
        bankAccountRequestDTO.setAccountStatus(BankAccountStatus.INACTIVE);
        bankAccountRequestDTO.setAccountType(BankAccountType.CURRENT);
        bankAccountRequestDTO.setAbsoluteLimit(200.0);
        bankAccountRequestDTO.setIban(initialBankAccount.getIban());

        HttpEntity<BankAccountRequestDTO> entity = new HttpEntity<>(bankAccountRequestDTO, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount"), HttpMethod.PUT, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            BankAccount updatedBankAccount = bankAccountService.GetBankAccountByIban(initialBankAccount.getIban());
            Assert.assertTrue(updatedBankAccount.getAccountStatus() == BankAccountStatus.INACTIVE);
        }
        else Assert.fail();
    }

    @Test
    public void customerNOTAllowedToGetAllBankAccounts() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccounts"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED || response.getStatusCode() == HttpStatus.FORBIDDEN);
    }

    @Test
    public void employeeAllowedToGetAllBankAccounts() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccounts"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void testname() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccount"), HttpMethod.GET, entity, String.class);

        Assert.fail();
    }

}
