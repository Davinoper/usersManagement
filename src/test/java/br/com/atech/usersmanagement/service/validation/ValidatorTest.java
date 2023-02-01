package br.com.atech.usersmanagement.service.validation;

import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.exeception.EmailAlreadyUsedException;
import br.com.atech.usersmanagement.exeception.InvalidUserEmailException;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.user.UsersMock;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)

class ValidatorTest {
    @Spy
    @InjectMocks
    Validator validator;

    User mockUser;

    @Mock
    UsersRepository usersRepository;

    @Captor
    ArgumentCaptor<User> captorUser;

    @BeforeEach
    public void loader(){
        mockUser = UsersMock.getCompleteUser();
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
    }

    @Test
    void validateUserWithoutId() {
    }

    @Test
    void validateUserExistence() {
    }

    @Test
    void validateUserName() {
    }

    @Test
    void validateName() {
    }

    @Test
    void validateUserPassword() {
    }

    @Test
    void validateUserPasswordChange() {
    }

    @Test
    void validateActiveUser() {
    }

    @Test
    void findActiveUserByEmail() {
    }
}