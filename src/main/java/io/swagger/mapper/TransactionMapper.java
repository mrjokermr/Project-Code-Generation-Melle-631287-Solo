package io.swagger.mapper;

import io.swagger.model.Transaction;
import io.swagger.model.TransactionRequestDTO;
import io.swagger.model.TransactionResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class TransactionMapper {

    public static TransactionResponseDTO TransactionToResponseDTO(Transaction t) {
        TransactionResponseDTO responseObject = new TransactionResponseDTO();

        responseObject.setAmount(t.getAmount());
        responseObject.setTransactionType(t.getTransactionType());
        responseObject.setIbanFrom(t.getIbanFrom());
        responseObject.setIbanTo(t.getIbanTo());
        //responseObject.setCreationDate(t.getCreationDate());
        responseObject.setUserPerforming(t.getUserPerforming());

        return responseObject;
    }

    public static List<TransactionResponseDTO> TransactionsToTransactionsResponseList(List<Transaction> originalList) {
        List<TransactionResponseDTO> responseList = new ArrayList<>();

        for(Transaction t : originalList) {
            TransactionResponseDTO trd = new TransactionResponseDTO();

            trd.setTransactionType(t.getTransactionType());
            trd.setUserPerforming(t.getUserPerforming());
            trd.setAmount(t.getAmount());
            trd.setIbanTo(t.getIbanTo());
            trd.setIbanFrom(t.getIbanFrom());
            trd.setCreationDate(t.getCreationDate());

            responseList.add(trd);
        }

        return responseList;
    }

    public static TransactionResponseDTO TransactionRequestToTransactionResponseDTO(TransactionRequestDTO t, Integer userPerformingId) {
        TransactionResponseDTO transactionResponse = new TransactionResponseDTO();

        transactionResponse.setTransactionType(t.getTransactionType());
        transactionResponse.setAmount(t.getAmount());
        transactionResponse.setIbanFrom(t.getIbanFrom());
        transactionResponse.setIbanTo(t.getIbanTo());
        transactionResponse.setUserPerforming(userPerformingId);

        return transactionResponse;
    }
}
