package br.com.atech.usersmanagement.service;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.dto.UpdateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.domain.model.UserProfile;
import br.com.atech.usersmanagement.exeception.DisabledUserException;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import br.com.atech.usersmanagement.service.validation.Validator;
import br.com.atech.usersmanagement.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    private final ModelMapper modelMapper;

    private final Validator validator;

    public User create(CreateUserDTO createUserDTO){
        log.info("UserService.create - input [{}]", createUserDTO);
        User user = modelMapper.map(createUserDTO, User.class);
        validator.validateUserCreation(user);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        log.info("UserService.create - output [{}]", user);
        return PasswordUtils.hideUserPassword(usersRepository.save(user));
    }

    public User update(UpdateUserDTO updateUserDTO){
        log.info("UserService.update - input [{}]", updateUserDTO);
        User user = findById(updateUserDTO.getId());
        validator.validateUserExistence(user);
        user.setUserName(updateUserDTO.getUserName());
        user.setName(updateUserDTO.getName());
        validator.validateUserUpdate(user);
        log.info("UserService.update - output [{}]", user);
        return PasswordUtils.hideUserPassword(usersRepository.save(user));

    }

    public User disable(Long id){
        log.info("UserService.delete- input [{}]", id);
        User user = findById(id);
        validator.validateUserExistence(user);
        user.setActive(false);
        log.info("UserService.delete- input [{}]", user);
        return PasswordUtils.hideUserPassword(usersRepository.save(user));

    }

    public User enable(Long id){
        log.info("UserService.delete- input [{}]", id);
        User user = findById(id);
        validator.validateUserExistence(user);
        user.setActive(true);
        log.info("UserService.delete- input [{}]", user);
        return PasswordUtils.hideUserPassword(usersRepository.save(user));

    }

    public User changeUserPassword(Long id, ChangeUserPasswordDTO changeUserPasswordDTO){
        log.info("UserService.changeUserPassword - input [{}]", changeUserPasswordDTO);
        User user = findById(id);
        validator.validateUserPasswordChange(
            changeUserPasswordDTO,
            user.getPassword()
        );
        user.setPassword(new BCryptPasswordEncoder().encode(changeUserPasswordDTO.getNewPassword()));
        log.info("UserService.changeUserPassword - output [{}]", user);
        return PasswordUtils.hideUserPassword(usersRepository.save(user));
    }

    public UserProfile findUserProfile(String email){
      log.info("UserService.findProfile - input [{}]", email);
      User user = usersRepository.findByEmail(email);
      validator.validateUserExistence(user);
      UserProfile userProfile = modelMapper.map(user, UserProfile.class);
      log.info("UserService.findProfile - output [{}]", userProfile);
      return userProfile;
    }

    public Page<User> findAll(Pageable pageable, Optional<Boolean> active){
        log.info("UserService.findAll- input [{}]");
        Page<User> users;
        if(active.isPresent()){
            users = usersRepository.findAllWithFilter(pageable, active.get());
            Page<User> usersWithHidePassword = users.map(user -> {
                return PasswordUtils.hideUserPassword(user);
            });
            log.info("UserService.findAll- output [{}]", users);
            return usersWithHidePassword;
        }
        users = usersRepository.findAll(pageable);
        log.info("UserService.findAll- output [{}]", users);
        return users;
    }

    public User findById(Long id) {
        log.info("UserService.findById- input [{}]", id);
        User user = usersRepository.findById(id).orElse(null);
        log.info("UserService.findById- input [{}]", user);
        return user;
    }
}
