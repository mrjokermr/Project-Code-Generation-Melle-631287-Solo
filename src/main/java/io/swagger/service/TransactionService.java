package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.BankAccountRepository;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UserService userService;


    public Transaction CreateANewTransaction(TransactionResponseAndRequestDTO body) {
        if(IbanExistsAndIsValidForTransaction(body.getIbanTo()) && IbanExistsAndIsValidForTransaction(body.getIbanFrom())) {
            //ibans are valid
            if(!body.getIbanFrom().equals(body.getIbanTo())) {
                //ibans are not the same so nothing wrong with the ibans
                BankAccount fromBa = bankAccountService.GetBankAccountByIban(body.getIbanFrom());
                BankAccount toBa = bankAccountService.GetBankAccountByIban(body.getIbanTo());

                Double amountToTransfer = body.getAmount();

                if(BankAccountCanPerformThisTransaction(fromBa, amountToTransfer)) {
                    //bank account is able to perform this transfer
                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionType(Transaction.TransactionTypeEnum.REGULAR);
                    newTransaction.setAmount(amountToTransfer);
                    newTransaction.setCreationDate(new Date());
                    newTransaction.setIbanFrom(fromBa.getIban());
                    newTransaction.setIbanTo(toBa.getIban());
                    newTransaction.setUserPerforming(body.getUserPerforming());

                    //update the balances fro mthe bank accounts
                    fromBa.setBalance(fromBa.getBalance() - amountToTransfer);
                    toBa.setBalance(toBa.getBalance() + amountToTransfer);

                    bankAccountService.SaveBankAccount(fromBa);
                    bankAccountService.SaveBankAccount(toBa);

                    transactionRepository.save(newTransaction);
                    return newTransaction;
                }


            }
        }

        return null; //else ends up here
    }

    public Boolean BankAccountCanPerformThisTransaction(BankAccount bankAccount, Double amountToTransfer) {
        //check the daily limit and transactionlimit and balance (can become lower than)

        //-	Balance cannot become lower than a certain number defined per account, referred to as absolute limit
        //-	The cumulative value of transactions occurring on a day cannot surpass a certain number defined per user, referred to as day limit
        //-	The maximum amount per transaction cannot be higher than a certain number defined per user, referred to as transaction limit
        User bankAccountOwner = userService.GetUserInfoById(bankAccount.getOwnerId());

        if(bankAccountOwner == null) return false;
        else {
            //check the conditions
            boolean dayLimitCondition = false;
            if(AmountOfTransactionsPerformedTodayByUserId(bankAccountOwner.getId()) <= bankAccountOwner.getDayLimit()) dayLimitCondition = true;

            boolean transactionAmountLimitCondition = false;
            if(amountToTransfer <= bankAccountOwner.getTransactionLimit()) transactionAmountLimitCondition = true;

            boolean bankAccountBalanceCondition = false;
            if(bankAccount.getBalance() - amountToTransfer >= bankAccount.getAbsoluteLimit()) bankAccountBalanceCondition = true;

            //check if conditions are met and if so return true else return false
            if(dayLimitCondition && transactionAmountLimitCondition && bankAccountBalanceCondition) return true;
            else return false;
        }


    }

    private Integer AmountOfTransactionsPerformedTodayByUserId(Integer userId) {
        List<Transaction> allPerformedTransactionsByUser = transactionRepository.findByUserPerforming(userId);

        Integer transactionsPerformedTodayCounter = 0;
        Date today = new Date();

        for(Transaction t : allPerformedTransactionsByUser) {
            long hoursDifference = ((today.getTime() - t.getCreationDate().getTime()) / 1000) / 3600;
            if(hoursDifference < 24) transactionsPerformedTodayCounter++;
        }

        return transactionsPerformedTodayCounter;
    }

    private Boolean IbanExistsAndIsValidForTransaction(String iban) {
        BankAccount targetBankAccount = bankAccountService.GetBankAccountByIban(iban);

        if(targetBankAccount == null) return false;
        else {
            //account needs to be active
            if(targetBankAccount.getAccountType().equals(BankAccountType.CURRENT) && targetBankAccount.getAccountStatus().equals(BankAccountStatus.ACTIVE)) {
                return true;
            }
            else return false;
        }
    }

}
