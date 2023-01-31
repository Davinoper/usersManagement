package br.com.atech.usersmanagement.service.validation;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.exeception.*;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    private final UsersRepository usersRepository;

    public void validateUserCreation(User user){
        validateEmail(user);
        validateUserWithoutId(user);
        validateUserName(user);
        validateUserPassword(user);
        validateName(user);

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

    public void validateUserExistence(User user){
        if(user == null){
            throw new UserDontExistsException();
        }
    }

    public void validateUserName(User user){
        User userExists = usersRepository.findByUserName(user.getUserName());
        System.out.println(user.getUserName());
        if(user.getUserName().isEmpty()){
            throw new InvalidUserNameException();
        }
        if(userExists != null){
            throw new UserNameAlreadyUsedException();
        }
    }

    public void validateName(User user){
        if(user.getName().isEmpty()){
            throw new InvalidNameException();
        }
    }

    public void validateUserPassword(User user){
        if(user.getPassword().length() < 6){
            throw new InvalidPasswordException();
        }
    }

    public void validateUserPasswordChange(ChangeUserPasswordDTO changeUserPasswordDTO, String encryptedPassword){
        boolean firstValidationSentence = PasswordUtils.compareEncryptedPassword(
            changeUserPasswordDTO.getOldPassword(),
            encryptedPassword
        );
        boolean secondValidationSentence = changeUserPasswordDTO.getNewPassword().equals(
                changeUserPasswordDTO.getConfirmNewPassword()
        );

        if(!firstValidationSentence){
            throw new RuntimeException();
        }

        if(!secondValidationSentence){
            throw new RuntimeException();
        }
    }

    public void validateEmail(User user){
        User userExists = usersRepository.findByEmail(user.getEmail());
        if(userExists != null){
            throw new EmailAlreadyUsedException();
        }
    }
}
