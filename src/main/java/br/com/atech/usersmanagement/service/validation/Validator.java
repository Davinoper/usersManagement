package br.com.atech.usersmanagement.service.validation;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final UsersRepository usersRepository;

    public void validateUserCreation(User user){
        validateUserWithoutId(user);
        validateUserName(user);
        validateUserPassword(user);
        validateName(user);
        validateEmail(user);
    }

    public void  validateUserUpdate(User user){
        validateName(user);
        validateUserName(user);
    }

    public void validateUserWithoutId(User user){
        if(user.getId() != null){
            throw new RuntimeException();
        }
    }

    public void validateUserName(User user){
        User userExists = usersRepository.findByEmailNameOrUserName(user.getUserName());

        if(user.getUserName().isEmpty() || userExists != null){
            throw new RuntimeException();
        }
    }

    public void validateName(User user){
        if(user.getName().isEmpty()){
            throw new RuntimeException();
        }
    }

    public void validateUserPassword(User user){
        if(user.getPassword().length() < 6){
            throw new RuntimeException();
        }
    }

    public void validateUserPasswordChange(ChangeUserPasswordDTO changeUserPasswordDTO, String encryptedPassword){
        boolean validationSentence = PasswordUtils.compareEncryptedPassword(
            changeUserPasswordDTO.getOldPassword(),
            encryptedPassword
        ) && changeUserPasswordDTO.getNewPassword().equals(
            changeUserPasswordDTO.getConfirmNewPassword()
        );

        if(!validationSentence){
            throw new RuntimeException();
        }
    }

    public void validateEmail(User user){
        User userExists = usersRepository.findByEmailNameOrUserName(user.getEmail());
        if(userExists == null){
            throw new RuntimeException();
        }
    }
}
