package io.swagger.mapper;

import io.swagger.model.User;
import io.swagger.model.UserResponseDTO;

public class UserMapper {

    public static UserResponseDTO UserToUserResponseDTO(User u) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(u.getId());
        userResponseDTO.setFirstName(u.getFirstName());
        userResponseDTO.setLastName(u.getLastName());
        userResponseDTO.setUsername(u.getUsername());
        userResponseDTO.setCreationDate(u.getCreationDate());
        userResponseDTO.setDayLimit(u.getDayLimit());
        userResponseDTO.setTransactionLimit(u.getTransactionLimit());
        userResponseDTO.setUserType(u.getUserType());

        return userResponseDTO;
    }
}
