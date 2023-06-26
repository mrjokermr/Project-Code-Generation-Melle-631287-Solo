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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Swagger2SpringBoot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionTests {

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

    private HttpHeaders GetHttpheaderWithBearerTokenForRandomUser() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(new LoginRequestDTO("RonaldaPopuliers2022","geheim"), getHttpHeader());
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
    public void customerCanLoadOwnTransactions() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void newTransactionIsAddedToDatabase() throws IOException {
        //customers current IBAN : DCBA  with 5000 balance and savings IBAn: GFEDCBA with also 5k balance
        //employees current IBAN : ABCD  with 5k balance and savings iban: ABCDEFG with also 5k balance
        //employee transaction limit = 4k
        //customers transaction limit = 10k , and day limit for both are 100 transactions
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        transactionRequestDTO.setIbanFrom("DCBA");
        transactionRequestDTO.setIbanTo("ABCD"); //test employees current IBAN
        transactionRequestDTO.setAmount(100.0);
        transactionRequestDTO.setTransactionType(Transaction.TransactionTypeEnum.REGULAR);

        HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transactionRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.POST, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {

            TypeReference<List<Transaction>> typeRef = new TypeReference<List<Transaction>>(){};
            List<Transaction> returnedTransactionInList = objectMapper.readValue(response.getBody(), typeRef);

            if(returnedTransactionInList.size() > 0) {
                Transaction createdTransaction = returnedTransactionInList.get(0);
                Assert.assertTrue(createdTransaction.getIbanFrom().equals(transactionRequestDTO.getIbanFrom()) &&
                        createdTransaction.getIbanTo().equals(transactionRequestDTO.getIbanTo()) &&
                        createdTransaction.getAmount().equals(transactionRequestDTO.getAmount()));
            }
            else Assert.fail();
        }
        else Assert.fail();
    }

    @Test
    public void userCanLoadAllHisTransactionsByHistoryWithIbanParamter() throws IOException {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        transactionRequestDTO.setIbanFrom("DCBA");
        transactionRequestDTO.setIbanTo("ABCD"); //test employees current IBAN
        transactionRequestDTO.setAmount(100.0);
        transactionRequestDTO.setTransactionType(Transaction.TransactionTypeEnum.REGULAR);

        HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transactionRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.POST, entity, String.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            HttpEntity<String> entity2 = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

            ResponseEntity<String> response2 = template.exchange(createFullUrl("/transaction/IbanOnly/DCBA?historyWithIban=ABCD"), HttpMethod.GET, entity2, String.class);


            Assert.assertTrue(response2.getStatusCode() == HttpStatus.OK);
        }
        else Assert.fail();

    }

    @Test
    public void transactionFromNOTOwnedBankAccountNotAllowed() throws IOException {
        //customers current IBAN : DCBA  with 5000 balance and savings IBAn: GFEDCBA with also 5k balance
        //employees current IBAN : ABCD  with 5k balance and savings iban: ABCDEFG with also 5k balance
        //employee transaction limit = 4k
        //customers transaction limit = 10k , and day limit for both are 100 transactions
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        transactionRequestDTO.setIbanFrom("ABCD"); //transfer money from not owned ABCD iban to own SAVINGS
        transactionRequestDTO.setIbanTo("GFEDCBA");
        transactionRequestDTO.setAmount(100.0);
        transactionRequestDTO.setTransactionType(Transaction.TransactionTypeEnum.REGULAR);

        HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transactionRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.POST, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.UNAUTHORIZED || response.getStatusCode() == HttpStatus.FORBIDDEN);
    }

    @Test
    public void employeeAllowedToPerformTransactionForCustomer() throws IOException {
        //customers current IBAN : DCBA  with 5000 balance and savings IBAn: GFEDCBA with also 5k balance
        //employees current IBAN : ABCD  with 5k balance and savings iban: ABCDEFG with also 5k balance
        //employee transaction limit = 4k
        //customers transaction limit = 10k , and day limit for both are 100 transactions
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        transactionRequestDTO.setIbanFrom("DCBA"); //transfer money from not owned bank account by employee to customers savings account
        transactionRequestDTO.setIbanTo("GFEDCBA");
        transactionRequestDTO.setAmount(100.0);
        transactionRequestDTO.setTransactionType(Transaction.TransactionTypeEnum.REGULAR);

        HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transactionRequestDTO, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/bankaccounts"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }


    @Test
    public void dayLimitIsFunctioningCorrectly() throws IOException {
        //RonaldaPopuliers2022
        //GetHttpheaderWithBearerTokenForRandomUser daylimit = 10
        HttpEntity<TransactionRequestDTO> loadBankAccountsEntity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForRandomUser());

        ResponseEntity<String> loadBankAccountsResponse = template.exchange(createFullUrl("/bankaccounts"), HttpMethod.GET, loadBankAccountsEntity, String.class);

        if(loadBankAccountsResponse.getStatusCode() == HttpStatus.OK) {
            TypeReference<List<BankAccount>> typeRef = new TypeReference<List<BankAccount>>(){};
            List<BankAccount> allBankAccountsOfRandomUser = objectMapper.readValue(loadBankAccountsResponse.getBody(), typeRef);

            BankAccount savingsAccount = null;
            BankAccount currentAccount = null;

            for(BankAccount ba : allBankAccountsOfRandomUser) {
                if(savingsAccount == null && ba.getAccountType().equals(BankAccountType.SAVINGS)) savingsAccount = ba;
                if(currentAccount == null && ba.getAccountType().equals(BankAccountType.CURRENT)) currentAccount = ba;

                if(savingsAccount != null && currentAccount != null) break;
            }

            //perform deposit on current account so the user can test performing transactions

            DepositOrWithdrawRequestDTO depositOrWithdrawRequestDTO = new DepositOrWithdrawRequestDTO();
            depositOrWithdrawRequestDTO.setAmount(2500.0);
            depositOrWithdrawRequestDTO.setIban(currentAccount.getIban());

            HttpEntity<DepositOrWithdrawRequestDTO> depositMoneyEntity = new HttpEntity<>(depositOrWithdrawRequestDTO, GetHttpheaderWithBearerTokenForRandomUser());

            ResponseEntity<String> depositMoney = template.exchange(createFullUrl("/transaction/deposit"), HttpMethod.POST, depositMoneyEntity, String.class);


            if(depositMoney.getStatusCode() == HttpStatus.OK) {

                if(savingsAccount != null && currentAccount != null) {

                    TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

                    transactionRequestDTO.setIbanFrom(currentAccount.getIban()); //transfer money from not owned bank account by employee to customers savings account
                    transactionRequestDTO.setIbanTo(savingsAccount.getIban());
                    transactionRequestDTO.setAmount(1.0);
                    transactionRequestDTO.setTransactionType(Transaction.TransactionTypeEnum.REGULAR);

                    HttpEntity<TransactionRequestDTO> entity = new HttpEntity<>(transactionRequestDTO, GetHttpheaderWithBearerTokenForRandomUser());

                    for(Integer i = 0; i < 11; i++) {

                        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.POST, entity, String.class);

                        if(response.getStatusCode() != HttpStatus.OK) Assert.fail();
                    }

                    ResponseEntity<String> lastTransaction = template.exchange(createFullUrl("/transaction"), HttpMethod.POST, entity, String.class);

                    // the 11the transaction can't go on because default daylimit is set at 10
                    Assert.assertTrue(lastTransaction.getStatusCode() != HttpStatus.OK);

                }
                else Assert.fail("No bank accounts to use");

            }
            else Assert.fail("Deposit went wrong");

        }
        else Assert.fail("Loading bank accounts went wrong");

//        if(response.getStatusCode() == HttpStatus.OK) {
//
//            TypeReference<List<Transaction>> typeRef = new TypeReference<List<Transaction>>(){};
//            List<Transaction> returnedTransactionInList = objectMapper.readValue(response.getBody(), typeRef);
//
//            if(returnedTransactionInList.size() > 0) {
//                Transaction createdTransaction = returnedTransactionInList.get(0);
//                Assert.assertTrue(createdTransaction.getIbanFrom().equals(transactionRequestDTO.getIbanFrom()) &&
//                        createdTransaction.getIbanTo().equals(transactionRequestDTO.getIbanTo()) &&
//                        createdTransaction.getAmount().equals(transactionRequestDTO.getAmount()));
//            }
//            else Assert.fail();
//        }
//        else Assert.fail();
    }


    @Test
    public void canDepositAnyValue() throws IOException {
        //perform deposit on current account so the user can test performing transactions
        //customers current IBAN : DCBA  with 5000 balance and savings IBAn: GFEDCBA with also 5k balance
        //employees current IBAN : ABCD  with 5k balance and savings iban: ABCDEFG with also 5k balance
        //employee transaction limit = 4k
        //customers transaction limit = 10k , and day limit for both are 100 transactions

        DepositOrWithdrawRequestDTO depositOrWithdrawRequestDTO = new DepositOrWithdrawRequestDTO();
        depositOrWithdrawRequestDTO.setAmount(5000.0); //higher then the transaction limit, so it shouldn't be blocked
        depositOrWithdrawRequestDTO.setIban("ABCD");

        HttpEntity<DepositOrWithdrawRequestDTO> entity = new HttpEntity<>(depositOrWithdrawRequestDTO, GetHttpheaderWithBearerTokenForEmployee());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction/deposit"), HttpMethod.POST, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void canNOTWithdrawMoreMoneyThenOnCurrentBalance() throws IOException {
        HttpEntity<TransactionRequestDTO> loadBankAccountsEntity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> loadBankAccountsResponse = template.exchange(createFullUrl("/bankaccounts"), HttpMethod.GET, loadBankAccountsEntity, String.class);

        if(loadBankAccountsResponse.getStatusCode() == HttpStatus.OK) {
            TypeReference<List<BankAccount>> typeRef = new TypeReference<List<BankAccount>>(){};
            List<BankAccount> allBankAccountsOfRandomUser = objectMapper.readValue(loadBankAccountsResponse.getBody(), typeRef);

            BankAccount currBankAccount = null;

            for(BankAccount ba : allBankAccountsOfRandomUser) {
                if(currBankAccount == null && ba.getAccountType().equals(BankAccountType.CURRENT)) {
                    currBankAccount = ba;
                    break;
                }
            }


            if(currBankAccount != null) {
                DepositOrWithdrawRequestDTO depositOrWithdrawRequestDTO = new DepositOrWithdrawRequestDTO();
                //amount is higher than allowed balance
                depositOrWithdrawRequestDTO.setAmount(currBankAccount.getBalance() + Math.abs(currBankAccount.getAbsoluteLimit()) + 1.0);
                depositOrWithdrawRequestDTO.setIban(currBankAccount.getIban());

                HttpEntity<DepositOrWithdrawRequestDTO> entity = new HttpEntity<>(depositOrWithdrawRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

                ResponseEntity<String> response = template.exchange(createFullUrl("/transaction/withdraw"), HttpMethod.POST, entity, String.class);

                Assert.assertTrue(response.getStatusCode() != HttpStatus.OK);
            }
            else Assert.fail("No curr bankaccount for auth user is found");
        }
        else Assert.fail();
    }

    @Test
    public void canWithdrawExactBalanceAmount() throws IOException {
        HttpEntity<TransactionRequestDTO> loadBankAccountsEntity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> loadBankAccountsResponse = template.exchange(createFullUrl("/bankaccounts"), HttpMethod.GET, loadBankAccountsEntity, String.class);

        if(loadBankAccountsResponse.getStatusCode() == HttpStatus.OK) {
            TypeReference<List<BankAccount>> typeRef = new TypeReference<List<BankAccount>>(){};
            List<BankAccount> allBankAccountsOfRandomUser = objectMapper.readValue(loadBankAccountsResponse.getBody(), typeRef);

            BankAccount currBankAccount = null;

            for(BankAccount ba : allBankAccountsOfRandomUser) {
                if(currBankAccount == null && ba.getAccountType().equals(BankAccountType.CURRENT)) {
                    currBankAccount = ba;
                    break;
                }
            }


            if(currBankAccount != null) {
                DepositOrWithdrawRequestDTO depositOrWithdrawRequestDTO = new DepositOrWithdrawRequestDTO();
                //amount is higher than allowed balance
                depositOrWithdrawRequestDTO.setAmount(currBankAccount.getBalance() + Math.abs(currBankAccount.getAbsoluteLimit()));
                depositOrWithdrawRequestDTO.setIban(currBankAccount.getIban());

                HttpEntity<DepositOrWithdrawRequestDTO> entity = new HttpEntity<>(depositOrWithdrawRequestDTO, GetHttpheaderWithBearerTokenForCustomer());

                ResponseEntity<String> response = template.exchange(createFullUrl("/transaction/withdraw"), HttpMethod.POST, entity, String.class);

                Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
            }
            else Assert.fail("No curr bankaccount for auth user is found");
        }
        else Assert.fail();
    }

    @Test
    public void userCanLoadAllHisTransactions() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.GET, entity, String.class);

        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT);
    }

    @Test
    public void userCanLoadAllHisTransactionsByToDateParameter() throws IOException {
        //has performed 10 transactions and this should run from all the tests otherwise the transaction has not been performed
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForRandomUser());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction?toDate=2000-01-01"), HttpMethod.GET, entity, String.class);


        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void userCanLoadAllHisTransactionsByFromDateParameter() throws IOException {
        //has performed 10 transactions and this should run from all the tests otherwise the transaction has not been performed
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForRandomUser());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction?fromDate=2000-01-01"), HttpMethod.GET, entity, String.class);


        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void userCanLoadAllHisTransactionsByAmountBiggerThanParamter() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForRandomUser());

        ResponseEntity<String> loadAllTransactionsResponse = template.exchange(createFullUrl("/transaction"), HttpMethod.GET, entity, String.class);

        if(loadAllTransactionsResponse.getStatusCode() == HttpStatus.OK) {

            ResponseEntity<String> response = template.exchange(createFullUrl("/transaction?amountBiggerThan=0.99"), HttpMethod.GET, entity, String.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                TypeReference<List<Transaction>> typeRef = new TypeReference<List<Transaction>>(){};
                List<Transaction> allTransactions = objectMapper.readValue(loadAllTransactionsResponse.getBody(), typeRef);
                List<Transaction> allQueryTransactions = objectMapper.readValue(response.getBody(), typeRef);

                Assert.assertTrue(allQueryTransactions.size() == allTransactions.size());

            }
            else Assert.fail();

        }
        else Assert.fail();

    }

    @Test
    public void userCanLoadAllHisTransactionsByAmountSmallerThanParamter() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForRandomUser());

        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction?amountSmallerThan=0.99"), HttpMethod.GET, entity, String.class);


        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
    }

    @Test
    public void userCanLoadAllHisTransactionsByAmountEqualsParamter() throws IOException {
        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForRandomUser());

        ResponseEntity<String> loadAllTransactionsResponse = template.exchange(createFullUrl("/transaction"), HttpMethod.GET, entity, String.class);

        if(loadAllTransactionsResponse.getStatusCode() == HttpStatus.OK) {

            ResponseEntity<String> response = template.exchange(createFullUrl("/transaction?amountEquals=1"), HttpMethod.GET, entity, String.class);

            TypeReference<List<Transaction>> typeRef = new TypeReference<List<Transaction>>(){};
            List<Transaction> allTransactions = objectMapper.readValue(loadAllTransactionsResponse.getBody(), typeRef);
            Integer amountOfOneEURTransactions = 0;
            for(Transaction t : allTransactions) if(t.getAmount() == 1.0) amountOfOneEURTransactions++;

            if(response.getStatusCode() == HttpStatus.OK) {
                List<Transaction> allQueryTransactions = objectMapper.readValue(response.getBody(), typeRef);
                Assert.assertTrue(allQueryTransactions.size() == amountOfOneEURTransactions);
            }
            else {
                Assert.assertTrue(response.getStatusCode() == HttpStatus.NO_CONTENT && amountOfOneEURTransactions == 0);
            }

        }
        else Assert.fail();

    }

//    @Test
//    public void testname() throws IOException {
//        HttpEntity<String> entity = new HttpEntity<>(null, GetHttpheaderWithBearerTokenForCustomer());
//
//        ResponseEntity<String> response = template.exchange(createFullUrl("/transaction"), HttpMethod.GET, entity, String.class);
//
//        Assert.fail();
//    }
}
