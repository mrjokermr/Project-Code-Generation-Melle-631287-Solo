package io.swagger.mapper;

import io.swagger.model.User;
import io.swagger.model.UserResponseDTO;

import java.util.ArrayList;
import java.util.List;

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

    public static List<UserResponseDTO> UserListToUserResponseDTOList(List<User> users) {
        List<UserResponseDTO> usersResponse = new ArrayList<>();

        for(User u : users) {
            usersResponse.add(UserToUserResponseDTO(u));
        }

        return usersResponse;
    }
}
