//package cucumberTestsSecondAttempt.glue;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import cucumberTestsSecondAttempt.CucumberContextConfig;
//import io.cucumber.java.en.And;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import io.cucumber.spring.CucumberContextConfiguration;
//import io.swagger.model.LoginRequestDTO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//
//@SpringBootTest(classes = CucumberContextConfig.class)
//@CucumberContextConfiguration
//public class LoginStepDefinition {
//
//    HttpHeaders httpHeaders = new HttpHeaders();
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private ResponseEntity<String> response;
//
//    private LoginRequestDTO loginDto;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @LocalServerPort
//    private int port;
//
//    private String token;
//    public LoginStepDefinition() {
//
//    }
//
//    @Given("^I have a valid user object$")
//    public void iHaveAValidUserObject() {
//        loginDto = new LoginRequestDTO("CarolinaVeldman2022","geheim");
//    }
//
//    @When("I call the login endpoint")
//    public void iCallTheLoginEndpoint() throws JsonProcessingException {
//        httpHeaders.add("Content-Type", "application/json");
//        response = restTemplate.exchange(
//                "/login",
//                HttpMethod.POST,
//                new HttpEntity<>(mapper.writeValueAsString(loginDto), httpHeaders), String.class
//        );
//    }
//
//    @Then("I receive a status of {int}")
//    public void iReceiveAStatusOf(int arg0) {
//    }
//
//    @And("I get a JWT-token")
//    public void iGetAJWTToken() {
//    }
//
//}
