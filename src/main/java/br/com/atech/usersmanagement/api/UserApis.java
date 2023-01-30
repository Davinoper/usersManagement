package br.com.atech.usersmanagement.api;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.infra.entity.UserEntity;
import br.com.atech.usersmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserApis {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserEntity> create(@RequestBody CreateUserDTO  createUserDTO){
        try{
            UserEntity user = userService.create(createUserDTO);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<UserEntity> delete(@PathVariable Long id){
        try{
            UserEntity user = userService.delete(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
