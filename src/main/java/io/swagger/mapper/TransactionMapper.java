package io.swagger.mapper;

import io.swagger.model.Transaction;
import io.swagger.model.TransactionResponseAndRequestDTO;

public class TransactionMapper {

    public static TransactionResponseAndRequestDTO TransactionToResponseAndRequestDTO(Transaction t) {
        TransactionResponseAndRequestDTO responseObject = new TransactionResponseAndRequestDTO();

        responseObject.setAmount(t.getAmount());
        responseObject.setTransactionType(t.getTransactionType());
        responseObject.setIbanFrom(t.getIbanFrom());
        responseObject.setIbanTo(t.getIbanTo());
        //responseObject.setCreationDate(t.getCreationDate());
        responseObject.setUserPerforming(t.getUserPerforming());

        return responseObject;
    }
}
