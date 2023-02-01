package br.com.atech.usersmanagement.user;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.dto.UpdateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.exeception.InvalidUserEmailException;
import br.com.atech.usersmanagement.exeception.UserDontExistsException;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.service.UserService;
import br.com.atech.usersmanagement.service.validation.Validator;
import br.com.atech.usersmanagement.utils.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    Validator validator;

    @Captor
    ArgumentCaptor<User> captorUser;

    @Captor
    ArgumentCaptor<String> captorString;

    @Captor
    ArgumentCaptor<ChangeUserPasswordDTO> captorChangePassword;

    CreateUserDTO mockCreateUserDTO;

    User mockUser;

    UpdateUserDTO mockUpdateUserDTO;

    ChangeUserPasswordDTO mockChangeUserPasswordDTO;


    @BeforeEach
    public void mockLoader(){
        mockCreateUserDTO = UsersMock.getCreateUserDTO();
        mockUser = UsersMock.getCompleteUser();
        mockUpdateUserDTO = UsersMock.getupdateUserDTO();
        mockChangeUserPasswordDTO = UsersMock.getChangePasswordDTO();
    };

    @Test
    void create_shouldCallValidateUserCreation(){
        mockCreateUserDTO.setPassword("");
        mockUser.setPassword("");

        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(mockUser);
        BDDMockito.when(modelMapper.map(mockCreateUserDTO,User.class)).thenReturn(mockUser);
        userService.create(mockCreateUserDTO);

        Mockito.verify(validator, Mockito.times(1)).validateUserCreation(captorUser.capture());
        assertEquals(captorUser.getValue(), mockUser);
    };

    @Test
    void update_shoulCallValidateUserUpdateAndExistence(){
        BDDMockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(mockUser));
        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(mockUser);

        userService.update(mockUpdateUserDTO);

        Mockito.verify(validator, Mockito.times(1)).validateUserExistence(captorUser.capture());
        Mockito.verify(validator, Mockito.times(1)).validateUserUpdate(captorUser.capture());
        assertEquals(captorUser.getAllValues().get(0), mockUser);
        assertEquals(captorUser.getAllValues().get(1), mockUser);
    };

    @Test
    void findById_shouldCallValidateExistingUser(){
        BDDMockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));

        userService.findById(Mockito.anyLong());

        Mockito.verify(validator, Mockito.times(1)).validateUserExistence(captorUser.capture());
        assertEquals(captorUser.getValue(), mockUser);

    };

    @Test
    void enable_shoudCallValidateExistingUser(){
        BDDMockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));
        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(mockUser);

        userService.enable(Mockito.anyLong());

        Mockito.verify(validator, Mockito.times(1)).validateUserExistence(captorUser.capture());
        assertEquals(captorUser.getAllValues().get(0), mockUser);
    };

    @Test
    void disable_shouldCallValidateExistingUser(){
        BDDMockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(mockUser));
        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(mockUser);

        userService.disable(Mockito.anyLong());

        Mockito.verify(validator, Mockito.times(1)).validateUserExistence(captorUser.capture());
        assertEquals(captorUser.getValue(), mockUser);
    };

    @Test
    void changeUserPassword_shouldCallValidatePasswordChange(){
        BDDMockito.when(usersRepository.findById(Mockito.anyLong())).thenReturn(Optional.of((mockUser)));
        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(mockUser);
        userService.changeUserPassword(Mockito.anyLong(),mockChangeUserPasswordDTO);

        Mockito.verify(validator, Mockito.times(1)).validateUserPasswordChange(captorChangePassword.capture(), captorString.capture());
        assertEquals(captorChangePassword.getValue(), mockChangeUserPasswordDTO);

    };

    @Test
    void findUserProfile_shouldCallValidateUserExistence(){
        BDDMockito.when(usersRepository.findByEmail(Mockito.anyString())).thenReturn(mockUser);

        userService.findUserProfile(Mockito.anyString());

        Mockito.verify(validator, Mockito.times(1)).validateUserExistence(captorUser.capture());
        assertEquals(captorUser.getValue(), mockUser);
    };



}

