package io.bootify.trollys.mapper;

import io.bootify.trollys.dto.UserDTO;
import io.bootify.trollys.entity.User;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setPassword(user.getPassword()); // Пароль может быть исключен в зависимости от ситуации
        return userDTO;
    }

    public static User toUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        return user;
    }

}
