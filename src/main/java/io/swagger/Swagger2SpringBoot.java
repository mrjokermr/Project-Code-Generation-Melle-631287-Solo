package io.swagger;

import io.swagger.Security.PasswordEncoderHolder;
import io.swagger.configuration.LocalDateConverter;
import io.swagger.configuration.LocalDateTimeConverter;

import io.swagger.model.*;
import io.swagger.repository.BankAccountRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.BankAccountService;
import io.swagger.service.UserService;
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
import java.util.List;

@SpringBootApplication
@EnableOpenApi
@ComponentScan(basePackages = { "io.swagger", "io.swagger.api" , "io.swagger.configuration"})
public class Swagger2SpringBoot implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    BankAccountService bankAccountService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... arg0) throws Exception {
        //main program constructor

        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }

        PasswordEncoderHolder.SetPasswordEncoder(this.passwordEncoder); //to save the autowired password encoder for global access

        CreateDummyDataAndSaveIt();
    }

    private void CreateDummyDataAndSaveIt() {

        CreateAndSaveBanksOwnAccount();

        User employee = new User();
        employee.setUsername("CarolinaVeldman2022");
        employee.setTransactionLimit(4000.0);
        employee.setCreationDate(new Date());
        employee.setDayLimit(1);
        employee.setFirstName("Carolina");
        employee.setLastName("Veldman");
        employee.setUserType(UserAccountType.ROLE_EMPLOYEE);
        employee.setEncryptedPassword("geheim");
        userRepository.save(employee);

        List<BankAccount> resultOne = bankAccountService.CreateCurrentAndSavingsAccountForUserId(userRepository.findByUsername(employee.getUsername()).getId());
        for(BankAccount ba : resultOne) {
            if(ba.getAccountType().equals(BankAccountType.CURRENT)) {
                ba.setIban("ABCD");
                ba.setAbsoluteLimit(-500.0);
                ba.setBalance(5000.0);
                bankAccountRepository.save(ba);
            }
        }


        User customer = new User();
        customer.setUsername("FerdinantHogewaard2022");
        customer.setTransactionLimit(10000.0);
        customer.setCreationDate(new Date());
        customer.setDayLimit(10);
        customer.setFirstName("Ferdinant");
        customer.setLastName("Hogewaard");
        customer.setUserType(UserAccountType.ROLE_CUSTOMER);
        customer.setEncryptedPassword("geheim");

        userRepository.save(customer);

        List<BankAccount> resultTwo = bankAccountService.CreateCurrentAndSavingsAccountForUserId(userRepository.findByUsername(customer.getUsername()).getId());
        for(BankAccount ba : resultTwo) {
            if(ba.getAccountType().equals(BankAccountType.CURRENT)) {
                ba.setIban("DCBA");
                ba.setAbsoluteLimit(-500.0);
                ba.setBalance(5000.0);
                bankAccountRepository.save(ba);
            }
        }




        //one user with duplicate name for testing purpose of the posibility that users have the same fullname
        CreateAndSaveAFakeUser("Yasmin","Tiereliers",UserAccountType.ROLE_EMPLOYEE,true);
        CreateAndSaveAFakeUser("Yasmin","Tiereliers",UserAccountType.ROLE_CUSTOMER,true);
        CreateAndSaveAFakeUser("Patrick","Van der Stier",UserAccountType.ROLE_CUSTOMER,true);
        CreateAndSaveAFakeUser("Sanne","Mets",UserAccountType.ROLE_CUSTOMER,true);
        CreateAndSaveAFakeUser("Yuri","van Gelder",UserAccountType.ROLE_CUSTOMER,false);
        CreateAndSaveAFakeUser("Joseph","Kralinger",UserAccountType.ROLE_CUSTOMER,true);
        CreateAndSaveAFakeUser("Ronalda","Populiers",UserAccountType.ROLE_CUSTOMER,false);

        //init bank accounts for dummy users and employee and customer
    }

    private void CreateAndSaveAFakeUser(String firstName, String lastName, UserAccountType type, Boolean createBankAccountsForUser) {
        User dummyUser = new User();
        if(userRepository.findByUsername(firstName + lastName + "2022") != null) {
            dummyUser.setUsername(firstName + lastName + "20222");
        }
        else {
            dummyUser.setUsername(firstName + lastName + "2022");
        }
        dummyUser.setTransactionLimit(30.0);
        dummyUser.setCreationDate(new Date());
        dummyUser.setDayLimit(10);
        dummyUser.setFirstName(firstName);
        dummyUser.setLastName(lastName);
        dummyUser.setUserType(UserAccountType.ROLE_CUSTOMER);
        dummyUser.setEncryptedPassword("geheim");

        userRepository.save(dummyUser);

        if(createBankAccountsForUser) {
            bankAccountService.CreateCurrentAndSavingsAccountForUserId(userRepository.findByUsername(dummyUser.getUsername()).getId());
        }
    }

    private void CreateAndSaveBanksOwnAccount() {
        User bankOwner = new User();
        bankOwner.setUsername("BankOwner");
        bankOwner.setTransactionLimit(0.0);
        bankOwner.setCreationDate(new Date());
        bankOwner.setDayLimit(0);
        bankOwner.setFirstName("Bank");
        bankOwner.setLastName("OwnerAsd");
        bankOwner.setUserType(UserAccountType.ROLE_BANKADMIN);
        bankOwner.setEncryptedPassword("geheim");

        userRepository.save(bankOwner);

        //create the bank account owned by the bank itself
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban("NL01INHO0000000001");
        bankAccount.setAccountType(BankAccountType.CURRENT);
        bankAccount.setAbsoluteLimit(10000000.0);
        bankAccount.setOwnerId(userRepository.findByUsername(bankOwner.getUsername()).getId());
        bankAccount.setCreationDate(new Date());
        bankAccount.setAccountStatus(BankAccountStatus.ACTIVE);
        bankAccount.setBalance(0.0);

        bankAccountRepository.save(bankAccount);
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
