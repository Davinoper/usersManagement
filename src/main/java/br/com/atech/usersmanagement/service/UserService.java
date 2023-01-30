package br.com.atech.usersmanagement.service;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.dto.UpdateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.domain.model.UserProfile;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.service.validator.Validator;
import br.com.atech.usersmanagement.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    private final ModelMapper modelMapper;

    public User create(CreateUserDTO createUserDTO){
        log.info("UserService.create - input [{}]", createUserDTO);
        User user = modelMapper.map(createUserDTO, User.class);
        Validator.validateUserCreation(user);
        isValidEmail(user.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        log.info("UserService.create - output [{}]", user);
        return usersRepository.save(user);
    }

    public User update(UpdateUserDTO updateUserDTO){
        log.info("UserService.update - input [{}]", updateUserDTO);
        User user = findById(updateUserDTO.getId());
        if(user != null){
            User userModel = modelMapper.map(updateUserDTO, User.class);
            Validator.validateUserUpdate(userModel);
            userModel.setPassword(user.getPassword());
            userModel.setEmail(user.getEmail());
            log.info("UserService.update - output [{}]", userModel);
            return usersRepository.save(userModel);
        }
        return null;
    }

    public User disable(Long id){
        log.info("UserService.delete- input [{}]", id);
        User user = findById(id);
        if(user != null){
            user.setActive(false);
            log.info("UserService.delete- input [{}]", user);
            return usersRepository.save(user);
        }
        return null;
    }

    public User enable(Long id){
        log.info("UserService.delete- input [{}]", id);
        User user = findById(id);
        if(user != null){
            user.setActive(true);
            log.info("UserService.delete- input [{}]", user);
            return usersRepository.save(user);
        }
        return null;
    }

    public User changeUserPassword(Long id, ChangeUserPasswordDTO changeUserPasswordDTO){
        log.info("UserService.changeUserPassword - input [{}]", changeUserPasswordDTO);
        User user = findById(id);
        Validator.validateUserPasswordChange(
            changeUserPasswordDTO,
            user.getPassword()
        );
        user.setPassword(new BCryptPasswordEncoder().encode(changeUserPasswordDTO.getNewPassword()));
        log.info("UserService.changeUserPassword - output [{}]", user);
        return usersRepository.save(user);
    }

    public UserProfile findUserProfile(String email){
      User user = usersRepository.findByEmail(email);
      UserProfile userProfile = modelMapper.map(user, UserProfile.class);
      return userProfile;
    }

    public Page<User> findAll(Pageable pageable){
        log.info("UserService.findAll- input [{}]");
        Page<User> users = usersRepository.findAll(pageable);
        log.info("UserService.findAll- output [{}]", users);
        return users;
    }


    public User findById(Long id){
        log.info("UserService.findById- input [{}]", id);
        User user = usersRepository.findById(id).orElseThrow();
        log.info("UserService.findById- input [{}]", user);
        return user;
    }

    public void isValidEmail(String email){
        User user = usersRepository.findByEmail(email);
        if(user != null){
            throw new RuntimeException();
        }
    }
}
