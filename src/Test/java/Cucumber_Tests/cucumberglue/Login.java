package Cucumber_Tests.cucumberglue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.boot.web.server.LocalServerPort;

public class Login {

    @LocalServerPort
    String port;

    @When("^I call the login endpoint$")
    public void whenCallFunction() {
        System.out.println("test");
    }
}
