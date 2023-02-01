package br.com.atech.usersmanagement.user;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.dto.UpdateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.service.UserService;
import br.com.atech.usersmanagement.service.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    ModelMapper modelMapper;

    @Captor
    ArgumentCaptor<User> captorUser;

    @Mock
    Validator validator;

    CreateUserDTO mockCreateUserDTO;

    User mockUser;

    UpdateUserDTO mockUpdateUserDTO;


    @BeforeEach
    public void mockLoader(){
        mockCreateUserDTO = UsersMock.getCreateUserDTO();
        mockUser = UsersMock.getCompleteUser();
        mockUpdateUserDTO = UsersMock.getupdateUserDTO();
    }

    @Test
    void create_shouldNotcreateWithInvalidEmail(){
        mockCreateUserDTO.setEmail("");
        mockUser.setEmail("");

        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(mockUser);
        BDDMockito.when(modelMapper.map(mockCreateUserDTO,User.class)).thenReturn(mockUser);
        userService.create(mockCreateUserDTO);

        Mockito.verify(validator, Mockito.times(1)).validateUserCreation(captorUser.capture());
        assertEquals(captorUser.getValue(), mockUser);
    }

    @Test
    void createWithInvalidName(){};


    @Test
    void createWithInvalidUserName(){};


    @Test
    void createWithInvalidPassword(){};

    @Test
    void createWithExisistingEmail(){};

    @Test
    void updateWithInvalidId(){};

    @Test
    void updateWithInvalidName(){};

    @Test
    void updateWithInvalidUserName(){};

    @Test
    void updateWithDisabledUser(){};

    @Test
    void findByIdWithInvalidId(){};

    @Test
    void enableWithInvalidId(){};

    @Test
    void disableWithInvalidId(){};

    @Test
    void changeUserPasswordWithWrongConfirmationPass(){};

    @Test
    void changeUserPasswordWithWrongOldPass(){};

    @Test
    void findUserProfileWithInvalidEmail(){};



}

