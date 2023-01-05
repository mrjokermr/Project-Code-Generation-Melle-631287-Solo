package io.swagger.repository;

import io.swagger.model.BankAccount;
import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByIbanFromOrIbanTo(String ibanFrom, String ibanTo);
    List<Transaction> findByUserPerforming(Integer userPerforming);
}
