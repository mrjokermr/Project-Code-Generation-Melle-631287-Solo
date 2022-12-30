package io.swagger;

import io.swagger.configuration.LocalDateConverter;
import io.swagger.configuration.LocalDateTimeConverter;

import io.swagger.model.User;
import io.swagger.model.UserAccountType;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.oas.annotations.EnableOpenApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Date;

@SpringBootApplication
@EnableOpenApi
@ComponentScan(basePackages = { "io.swagger", "io.swagger.api" , "io.swagger.configuration"})
public class Swagger2SpringBoot implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }


        User employee = new User();
        employee.setUsername("CarolinaVeldman2022");
        employee.transactionLimit(30.0);
        employee.setCreationDate(new Date());
        employee.setDayLimit(10);
        employee.setFirstName("Carolina");
        employee.setLastName("Veldman");
        employee.setUserType(UserAccountType.ROLE_EMPLOYEE);
        employee.setPassword(passwordEncoder.encode("geheim"));

        User customer = new User();
        customer.setUsername("FerdinantHogewaard2022");
        customer.transactionLimit(30.0);
        customer.setCreationDate(new Date());
        customer.setDayLimit(10);
        customer.setFirstName("Ferdinant");
        customer.setLastName("Hogewaard");
        customer.setUserType(UserAccountType.ROLE_CUSTOMER);
        customer.setPassword(passwordEncoder.encode("geheim"));


        userRepository.save(employee);
        userRepository.save(customer);
    }

    public static void main(String[] args) throws Exception {
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    @Configuration
    static class CustomDateConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new LocalDateConverter("yyyy-MM-dd"));
            registry.addConverter(new LocalDateTimeConverter("yyyy-MM-dd'T'HH:mm:ss.SSS"));
        }
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
