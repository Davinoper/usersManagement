package br.com.atech.usersmanagement.service.validation;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.exeception.*;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.user.UsersMock;
import br.com.atech.usersmanagement.utils.PasswordUtils;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class ValidatorTest {
    @Spy
    @InjectMocks
    Validator validator;

    User mockUser;

    ChangeUserPasswordDTO changeUserPasswordDTO;

    final String encryptedPassword = "$2a$10$s1pPAhZkBQivPrGN66fOleveM.FIvEcRPtX4KBo3N4YeXdDFc1fsS";

    final String decryptedPassword = "654321";

    @Mock
    BCryptPasswordEncoder encoder;

    @Mock
    UsersRepository usersRepository;

    @Captor
    ArgumentCaptor<User> captorUser;

    @BeforeEach
    public void loader(){
        mockUser = UsersMock.getCompleteUser();
        changeUserPasswordDTO = UsersMock.getChangePasswordDTO();
    }

    @Test
    void validateEmail_shouldThrowExceptionIfEmailInUse() {
        Mockito.when(usersRepository.findByEmail(Mockito.anyString())).thenReturn(mockUser);
        Assertions.assertThrows(EmailAlreadyUsedException.class, () ->{
           validator.validateEmail((mockUser));
        });

    }

    @Test
    void validateEmail_shouldThrowExceptionIfEmailIsEmpty() {
        mockUser.setEmail("");
        Mockito.when(usersRepository.findByEmail(Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(InvalidUserEmailException.class, () ->{
            validator.validateEmail((mockUser));
        });

    }

    @Test
    void validateUserCreation() {
        validator.validateUserCreation(mockUser);

        Mockito.verify(validator, Mockito.times(1)).validateEmail(captorUser.capture());
        Mockito.verify(validator, Mockito.times(1)).validateUserName(captorUser.capture());
        Mockito.verify(validator, Mockito.times(1)).validateUserPassword(captorUser.capture());
        Mockito.verify(validator, Mockito.times(1)).validateName(captorUser.capture());
        assertEquals(captorUser.getAllValues().get(0), mockUser);
        assertEquals(captorUser.getAllValues().get(1), mockUser);
        assertEquals(captorUser.getAllValues().get(2), mockUser);
        assertEquals(captorUser.getAllValues().get(3), mockUser);

    }

    @Test
    void validateUserUpdate() {
        validator.validateUserUpdate(mockUser);

        Mockito.verify(validator, Mockito.times(1)).validateActiveUser(captorUser.capture());
        Mockito.verify(validator, Mockito.times(1)).validateUserName(captorUser.capture());
        Mockito.verify(validator, Mockito.times(1)).validateName(captorUser.capture());

        assertEquals(captorUser.getAllValues().get(0), mockUser);
        assertEquals(captorUser.getAllValues().get(1), mockUser);
        assertEquals(captorUser.getAllValues().get(2), mockUser);
    }

    @Test
    void validateUserExistence_shouldThrowExceptionIfUserNull() {
        Assertions.assertThrows(UserDontExistsException.class, () ->{
            validator.validateUserExistence(null);
        });
    }

    @Test
    void validateUserName_shouldThrowExceptionIfInvalidUserName() {
        mockUser.setUserName("");
        Mockito.when(usersRepository.findByUserName(Mockito.anyString())).thenReturn(null);
        Assertions.assertThrows(InvalidUserNameException.class, () ->{
            validator.validateUserName((mockUser));
        });
    }

    @Test
    void validateUserName_shouldThrowExceptionIfUserNameExists() {
        Mockito.when(usersRepository.findByUserName(Mockito.anyString())).thenReturn(mockUser);
        Assertions.assertThrows(UserNameAlreadyUsedException.class, () ->{
            validator.validateUserName((mockUser));
        });
    }

    @Test
    void validateName_shouldThrowIfNameIsEmpty() {
        mockUser.setName("");
        Assertions.assertThrows(InvalidNameException.class, () ->{
            validator.validateName((mockUser));
        });
    }

    @Test
    void validateUserPassword_shouldThrowExceptionIfInvalidPassword() {
        mockUser.setPassword("123");
        Assertions.assertThrows(InvalidPasswordException.class, () ->{
            validator.validateUserPassword((mockUser));
        });
    }

    @Test
    void validateUserPasswordChange_shouldThrowIfConfirmPasswordDontMatch() {
        changeUserPasswordDTO.setConfirmNewPassword("wrongPass");
        changeUserPasswordDTO.setOldPassword(decryptedPassword);
        Assertions.assertThrows(IncorretConfirmationPasswordException.class, () ->{
            validator.validateUserPasswordChange(changeUserPasswordDTO,encryptedPassword);
        });
    }

    @Test
    void validateUserPasswordChange_shouldThrowIfOldPasswordDontMatch() {
        changeUserPasswordDTO.setOldPassword("wrongPass");
        changeUserPasswordDTO.setNewPassword(decryptedPassword);
        Assertions.assertThrows(IncorrectUserPasswordException.class, () ->{
            validator.validateUserPasswordChange(changeUserPasswordDTO,encryptedPassword);
        });
    }

    @Test
    void validateActiveUser_shouldThrowIfUserIsDisabled() {
        mockUser.setActive(false);
        Assertions.assertThrows(DisabledUserException.class, () ->{
            validator.validateActiveUser((mockUser));
        });
    }

    @Test
    void findActiveUserByEmail() {
        mockUser.setActive(false);
        Mockito.when(usersRepository.findByEmail(Mockito.anyString())).thenReturn(mockUser);
        Assertions.assertThrows(DisabledUserException.class, () ->{
            validator.findActiveUserByEmail((mockUser.getEmail()));
        });
    }
}