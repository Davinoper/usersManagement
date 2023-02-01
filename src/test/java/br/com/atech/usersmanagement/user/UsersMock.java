package br.com.atech.usersmanagement.user;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.dto.UpdateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;

public class UsersMock {
    public static User getCompleteUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("user@gmail.com");
        user.setUserName("mockUserComplete");
        user.setName("mockComplete");
        user.setPassword("mockuser123456");
        user.setActive(true);

        return user;
    }

    public static CreateUserDTO getCreateUserDTO() {
        CreateUserDTO createUserDTO = new CreateUserDTO();
        createUserDTO.setEmail("mock@gmail.com");
        createUserDTO.setUserName("mockUserCreate");
        createUserDTO.setName("mockCreate");
        createUserDTO.setPassword("1231243");
        return createUserDTO;
    }

    public static UpdateUserDTO getupdateUserDTO(){
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setId(1L);
        updateUserDTO.setName("mockUser");
        updateUserDTO.setUserName("mockUpdate");

        return updateUserDTO;
    }

    public static ChangeUserPasswordDTO getChangePasswordDTO(){
        ChangeUserPasswordDTO changeUserPasswordDTO = new ChangeUserPasswordDTO();
        changeUserPasswordDTO.setOldPassword("123456");
        changeUserPasswordDTO.setNewPassword("654321");
        changeUserPasswordDTO.setConfirmNewPassword("654321");
        return changeUserPasswordDTO;
    }
}
