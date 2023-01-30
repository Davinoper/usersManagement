package br.com.atech.usersmanagement.service;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.infra.entity.UserEntity;
import br.com.atech.usersmanagement.infra.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UsersRepository usersRepository;

    private final ModelMapper modelMapper;

    public UserEntity create(CreateUserDTO createUserDTO){
        log.info("UserService.create - input [{}]", createUserDTO);
         User user = modelMapper.map(createUserDTO, User.class);
         if(user.validPassword()){
             log.info("UserService.create - output [{}]", createUserDTO);
            return usersRepository.save(UserEntity.create(user));
         } else{
             throw new RuntimeException("A senha do usuário não é valida");
         }
    }

    public UserEntity update(CreateUserDTO createUserDTO){
        log.info("UserService.update - input [{}]", createUserDTO);
        User user = modelMapper.map(createUserDTO, User.class);
        if(user.validPassword()){
            log.info("UserService.create - output [{}]", createUserDTO);
            return usersRepository.save(UserEntity.create(user));
        } else{
            throw new RuntimeException("A senha do usuário não é valida");
        }
    }

    public UserEntity delete(Long id){
        log.info("UserService.delete- input [{}]", id);
        UserEntity user = findById(id);
        if(user != null){
            user.setActive(false);
            log.info("UserService.delete- input [{}]", user);
            return usersRepository.save(user);
        }else{
            throw new RuntimeException("O usuário com o id " + id +" não existe");
        }

    }

    public List<UserEntity> findAll(){
        log.info("UserService.findAll- input [{}]");
        List<UserEntity> users = usersRepository.findAll();
        log.info("UserService.findAll- output [{}]", users);
        return users;
    }


    public UserEntity findById(Long id){
        log.info("UserService.findById- input [{}]", id);
        UserEntity user = usersRepository.findById(id).orElseThrow();
        log.info("UserService.findById- input [{}]", user;
        return user;
    }
}
