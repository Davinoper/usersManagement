package br.com.atech.usersmanagement.user;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @InjectMocks
    UserService userService;

    @Mock
    UsersRepository usersRepository;

    static User completeUser;


    @BeforeEach
    public void mockLoader(){
        completeUser = UsersMock.getCompleteUser();
    }

    @Test
    void createWithInvalidEmail(){
        completeUser.setEmail("");

        BDDMockito.when(usersRepository.save(ArgumentMatchers.any(User.class))).thenReturn(completeUser);


    }


}

