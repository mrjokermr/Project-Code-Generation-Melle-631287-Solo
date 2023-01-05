package io.swagger.mapper;

import io.swagger.model.BankAccount;
import io.swagger.model.BankAccountIbanResponseDTO;
import io.swagger.model.User;

public class BankAccountMapper {

    public static BankAccountIbanResponseDTO BankAccountToBAIbanReponseDTO(BankAccount ba, User baOwner) {
        BankAccountIbanResponseDTO baIbanResponseDTO = new BankAccountIbanResponseDTO();
        baIbanResponseDTO.setIban(ba.getIban());
        baIbanResponseDTO.setOwnersFirstName(baOwner.getFirstName());
        baIbanResponseDTO.setOwnersLastName(baOwner.getLastName());
        baIbanResponseDTO.setOwnerId(baOwner.getId());

        return baIbanResponseDTO;
    }
}
