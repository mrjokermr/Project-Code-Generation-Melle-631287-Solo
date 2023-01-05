package io.swagger.service;

import io.swagger.mapper.BankAccountMapper;
import io.swagger.model.*;
import io.swagger.repository.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserService userService;

    private BankAccount CreateBlankBankAccount(Integer ownerId, BankAccountType type, BankAccountStatus status) {
        BankAccount newBlankBankAccount = new BankAccount();

        newBlankBankAccount.setIban(generateRandomUniqueIban());
        newBlankBankAccount.setOwnerId(ownerId);

        newBlankBankAccount.setAccountType(type);
        newBlankBankAccount.setAccountStatus(status);

        newBlankBankAccount.setBalance(0.0);
        newBlankBankAccount.setAbsoluteLimit(0.0);

        newBlankBankAccount.setCreationDate(new Date());


        return newBlankBankAccount;
    }

    public List<BankAccount> GetAllBankAccounts() {
        List<BankAccount> allBankAccounts = bankAccountRepository.findAll();

        allBankAccounts.removeIf(b -> b.getIban().equals("NL01INHO0000000001")); //remove the banks own account from the return list

        return allBankAccounts;
    }

    public BankAccount CreateBankAccountByRequestBody(NewBankAccountRequestDTO body) {
        BankAccount newBankAccount = CreateBlankBankAccount(body.getOwnerId(),body.getAccountType(),body.getAccountStatus());
        newBankAccount.setAbsoluteLimit(body.getAbsoluteLimit());

        bankAccountRepository.save(newBankAccount);

        return newBankAccount;
    }

    public BankAccount UpdateBankAccountByRequestBody(BankAccountRequestDTO body) {
        BankAccount bankAccountTarget = bankAccountRepository.findByIban(body.getIban());

        if(bankAccountTarget == null) return null;
        else {
            bankAccountTarget.setAccountType(body.getAccountType());
            bankAccountTarget.setAccountStatus(body.getAccountStatus());
            bankAccountTarget.setAbsoluteLimit(body.getAbsoluteLimit());

            bankAccountRepository.save(bankAccountTarget);
            return bankAccountTarget;
        }
    }

    public List<BankAccount> GetAllBankAccountsForUser(Integer userId) {
        return bankAccountRepository.findByownerId(userId);
    }

    public BankAccount GetBankAccountByIban(String iban) {
        return bankAccountRepository.findByIban(iban);
    }

    public List<BankAccountIbanResponseDTO> GetCurrentIbansByFullName(String fullName) {
        //first get the users where the full name is like
        //split the first word to the full name and the rest is the lastname
        int idx = fullName.lastIndexOf(' ');
        if (idx == -1) return null; //only one word has been send

        String firstName = fullName.substring(0, idx);
        String lastName  = fullName.substring(idx + 1);

        List<User> usersMatchingFullName = userService.GetMatchingUsersByFirstAndLastName(firstName, lastName);

        List<BankAccountIbanResponseDTO> bankAccountsIbanResponse = new ArrayList<>();

        for(User u : usersMatchingFullName) {
            bankAccountsIbanResponse.add(BankAccountMapper.BankAccountToBAIbanReponseDTO(
                    bankAccountRepository.findByOwnerIdAndAccountType(u.getId(), BankAccountType.CURRENT),u
            ));
        }

        return bankAccountsIbanResponse;
    }

    public TotalBalanceResponseDTO GetTotalBalanceByUserId(Integer targetUserId) {
        List<BankAccount> bankAccountsTarget = bankAccountRepository.findByownerId(targetUserId);

        if(bankAccountsTarget.size() > 0) {
            Double totalBalance = 0.0;
            for(BankAccount ba : bankAccountsTarget) totalBalance += ba.getBalance();

            return new TotalBalanceResponseDTO(totalBalance);
        }
        else {
            return null;
        }
    }


    public void SaveBankAccount(BankAccount bankAccount) {
        bankAccountRepository.save(bankAccount);
    }
    public List<BankAccount> CreateCurrentAndSavingsAccountForUserId(Integer targetUserId) {
        List<BankAccount> currentBankAccounts = bankAccountRepository.findByownerId(targetUserId);

        if(currentBankAccounts.stream().noneMatch(b -> b.getAccountType() == BankAccountType.CURRENT)) {

            //user has not yet got a current account
            BankAccount newCurrentAccount = this.CreateBlankBankAccount(targetUserId, BankAccountType.CURRENT, BankAccountStatus.ACTIVE );
            currentBankAccounts.add(newCurrentAccount);

            bankAccountRepository.save(newCurrentAccount);

        }
        if(currentBankAccounts.stream().noneMatch(b -> b.getAccountType() == BankAccountType.SAVINGS)) {
            //user has not yet got a savings account
            BankAccount newSavingsAccount = this.CreateBlankBankAccount(targetUserId, BankAccountType.SAVINGS, BankAccountStatus.ACTIVE);

            currentBankAccounts.add(newSavingsAccount);

            bankAccountRepository.save(newSavingsAccount);
        }

        return currentBankAccounts;
    }
    public String generateRandomUniqueIban() {
        boolean succes = true;
        String newIban = "";
        List<BankAccount> allBankAccounts = bankAccountRepository.findAll();
        do {
            succes = true;
            newIban = BankAccountService.IbanStringGenerator();
            for (int i = 0; i < allBankAccounts.size(); i++) {
                BankAccount bankAccount = allBankAccounts.get(i);
                String ibanToCheck = bankAccount.getIban();
                if (newIban == ibanToCheck) {
                    succes = false;
                }
            }
        } while(succes == false);

        return newIban;
    }

    private static String IbanStringGenerator() {
        Random random = new Random();
        String IBAN = "NL";
        int index = random.nextInt(10);
        IBAN += Integer.toString(index);
        index = random.nextInt(10);
        IBAN += Integer.toString(index);
        IBAN += "INHO0";
        for (int i = 0; i < 9; i++) {
            int n = random.nextInt(10);
            IBAN += Integer.toString(n);
        }

        return IBAN;
    }
}
