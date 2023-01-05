package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private String reasonWhyTransactionFailed = "";

    public String GetReasonWhyTransactionFailed() { return this.reasonWhyTransactionFailed; }

    private void SetReasonWhyTransactionFailed(String reason) { this.reasonWhyTransactionFailed = reason; }


    //END TRANSACTIONS GET WITH FILTERS METHODS BY USERID

    public List<Transaction> GetTransactionsById(Integer userId) {
        return transactionRepository.findByUserPerforming(userId);
    }

    public List<Transaction> GetTransactionsById(Integer userId, Date fromDate, Date toDate) {
        try {

            List<Transaction> usersTransactions = GetTransactionsById(userId);
            List<Transaction> filteredTransactionsList = new ArrayList<>();

            for(Transaction t : usersTransactions) {
                if(fromDate != null && toDate != null) {
                    if(t.getCreationDate().after(fromDate) && t.getCreationDate().before(toDate)) filteredTransactionsList.add(t);
                }
                else if(fromDate != null) {
                    if(t.getCreationDate().after(fromDate)) filteredTransactionsList.add(t);
                }
                else if(toDate != null) {
                    if(t.getCreationDate().after(toDate)) filteredTransactionsList.add(t);
                }
            }

            return filteredTransactionsList;

        }
        catch(Exception e) {
            return null;
        }
    }

    public List<Transaction> GetTransactionsById(Integer userId, Double amountBiggerThan, Double amountSmallerThan) {
        List<Transaction> usersTransactions = GetTransactionsById(userId);
        List<Transaction> filteredTransactionsList = new ArrayList<>();

        for(Transaction t : usersTransactions) {
            if(amountBiggerThan != null && amountSmallerThan != null) {
                if(t.getAmount() < amountSmallerThan && t.getAmount() > amountBiggerThan) filteredTransactionsList.add(t);
            }
            else if(amountBiggerThan != null) {
                if(t.getAmount() > amountBiggerThan) filteredTransactionsList.add(t);
            }
            else if(amountSmallerThan != null) {
                if(t.getAmount() < amountSmallerThan) filteredTransactionsList.add(t);
            }
        }

        return filteredTransactionsList;
    }

    public List<Transaction> GetTransactionsById(Integer userId, Double amountEquals) {
        List<Transaction> usersTransactions = GetTransactionsById(userId);
        List<Transaction> filteredTransactionsList = new ArrayList<>();

        for(Transaction t : usersTransactions) {
            if(amountEquals != null) {
                if(t.getAmount() == amountEquals) filteredTransactionsList.add(t);
            }
        }

        return filteredTransactionsList;
    }
    //END TRANSACTIONS GET WITH FILTERS METHODS BY USERID

    //START TRANSACTIONS GET WITH FILTERS METHODS BY IBAN
    public List<Transaction> GetTransactionsByIban(String iban) {
        return transactionRepository.findByIbanFromOrIbanTo(iban,iban);
    }

    public List<Transaction> GetTransactionsByIban(String iban, Date fromDate, Date toDate) {
        try {

            List<Transaction> usersTransactions = GetTransactionsByIban(iban);
            List<Transaction> filteredTransactionsList = new ArrayList<>();

            for(Transaction t : usersTransactions) {
                if(fromDate != null && toDate != null) {
                    if(t.getCreationDate().after(fromDate) && t.getCreationDate().before(toDate)) filteredTransactionsList.add(t);
                }
                else if(fromDate != null) {
                    if(t.getCreationDate().after(fromDate)) filteredTransactionsList.add(t);
                }
                else if(toDate != null) {
                    if(t.getCreationDate().after(toDate)) filteredTransactionsList.add(t);
                }
            }

            return filteredTransactionsList;

        }
        catch(Exception e) {
            return null;
        }
    }

    public List<Transaction> GetTransactionsByIban(String iban, Double amountBiggerThan, Double amountSmallerThan) {
        List<Transaction> usersTransactions = GetTransactionsByIban(iban);
        List<Transaction> filteredTransactionsList = new ArrayList<>();

        for(Transaction t : usersTransactions) {
            if(amountBiggerThan != null && amountSmallerThan != null) {
                if(t.getAmount() < amountSmallerThan && t.getAmount() > amountBiggerThan) filteredTransactionsList.add(t);
            }
            else if(amountBiggerThan != null) {
                if(t.getAmount() > amountBiggerThan) filteredTransactionsList.add(t);
            }
            else if(amountSmallerThan != null) {
                if(t.getAmount() < amountSmallerThan) filteredTransactionsList.add(t);
            }
        }

        return filteredTransactionsList;
    }

    public List<Transaction> GetTransactionsByIban(String iban, Double amountEquals) {
        List<Transaction> usersTransactions = GetTransactionsByIban(iban);
        List<Transaction> filteredTransactionsList = new ArrayList<>();

        for(Transaction t : usersTransactions) {
            if(amountEquals != null) {
                if(t.getAmount() == amountEquals) filteredTransactionsList.add(t);
            }
        }

        return filteredTransactionsList;
    }


    //END TRANSACTIONS GET WITH FILTERS METHODS BY IBAN
    public Transaction CreateANewTransaction(TransactionResponseDTO body) {
        SetReasonWhyTransactionFailed("");

        //first check is for bankaccount is active and is a current account
        if(IbanExistsAndIsValidForTransaction(body.getIbanTo(), body.getIbanFrom())) {
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

    public List<Transaction> FilterListTransactionsRelatedToIbanOnly(List<Transaction> originalList, String requiredIban) {
        List<Transaction> listToReturn = new ArrayList<>();

        for(Transaction t : originalList) {
            if(t.getIbanTo().equals(requiredIban) || t.getIbanFrom().equals(requiredIban)) listToReturn.add(t);
        }

        return listToReturn;
    }

    public Boolean BankAccountCanPerformThisTransaction(BankAccount bankAccount, Double amountToTransfer) {
        //check the daily limit and transactionlimit and balance (can become lower than)

        //-	Balance cannot become lower than a certain number defined per account, referred to as absolute limit
        //-	The cumulative value of transactions occurring on a day cannot surpass a certain number defined per user, referred to as day limit
        //-	The maximum amount per transaction cannot be higher than a certain number defined per user, referred to as transaction limit
        try {
            User bankAccountOwner = userService.GetUserInfoById(bankAccount.getOwnerId());

            if(bankAccountOwner == null) {
                SetReasonWhyTransactionFailed("Bankaccount doesn't exist");
                return false;
            }
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
                else {
                    String reason = "";
                    if(!dayLimitCondition) reason += "Daylimit surpassed. ";
                    if(!transactionAmountLimitCondition) reason += "Transactionlimit surpassed. ";
                    if(!bankAccountBalanceCondition) reason += "Balance to low. ";
                    SetReasonWhyTransactionFailed(reason);
                    return false;
                }
            }

        }
        catch(Exception e) {
            return false;
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

    private Boolean IbanExistsAndIsValidForTransaction(String ibanFrom, String ibanTo) {
        try {

            BankAccount targetBankAccountFrom = bankAccountService.GetBankAccountByIban(ibanFrom);
            BankAccount targetBankAccountTo = bankAccountService.GetBankAccountByIban(ibanTo);

            //bank accounts must exist
            if(targetBankAccountFrom == null || targetBankAccountTo == null) {
                SetReasonWhyTransactionFailed("One of the bankaccounts doesn't exist");
                return false;
            }
                //account needs to be active
            else if(!targetBankAccountFrom.getAccountStatus().equals(BankAccountStatus.ACTIVE) ||
                    !targetBankAccountTo.getAccountStatus().equals(BankAccountStatus.ACTIVE) ) {
                SetReasonWhyTransactionFailed("One of the bankaccounts is inactive");
                return false;
            }
            else {
                //if a person is transferring money between his own active bankaccounts is it always allowed.
                if(targetBankAccountFrom.getOwnerId().equals(targetBankAccountTo.getOwnerId())) return true;
                else {
                    //TO bankaccount needs to be of type CURRENT and the FROM bank account can't be a savings account
                    if(targetBankAccountTo.getAccountType().equals(BankAccountType.CURRENT) && targetBankAccountFrom.getAccountType().equals(BankAccountType.CURRENT)) {
                        return true;
                    }
                    else {
                        SetReasonWhyTransactionFailed("Transferring money from/to the wrong bankaccount");
                        return false;
                    }
                }
            }

        }
        catch(Exception e) {
            SetReasonWhyTransactionFailed("Something went wrong try again later");
            return false;
        }
    }

}
