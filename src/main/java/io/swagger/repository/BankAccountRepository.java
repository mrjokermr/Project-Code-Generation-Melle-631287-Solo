package io.swagger.repository;

import io.swagger.model.BankAccount;
import io.swagger.model.BankAccountStatus;
import io.swagger.model.BankAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    List<BankAccount> findByownerId(Integer ownerId); //get current and savings bankaccounts

    BankAccount findByIban(String iban);

    BankAccount findByOwnerIdAndAccountType(Integer ownerId, BankAccountType bankAccountType);
}
