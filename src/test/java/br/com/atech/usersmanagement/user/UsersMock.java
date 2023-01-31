package br.com.atech.usersmanagement.user;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;

public class UsersMock {
    public static User getCompleteUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUserName("mockUser");
        user.setPassword("mockuser123456");
        user.setActive(true);

        return user;
    }

    public CreateUserDTO getCreateUserDTOWithEmptyEmail() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("");
        createUserDTO.setUserName("mockUser");
        createUserDTO.setName("mocke");
        createUserDTO.setPassword("1231243");
        return createUserDTO;
    }
}
