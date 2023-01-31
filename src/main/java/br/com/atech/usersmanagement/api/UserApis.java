package br.com.atech.usersmanagement.api;

import br.com.atech.usersmanagement.domain.dto.ChangeUserPasswordDTO;
import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.dto.UpdateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import br.com.atech.usersmanagement.domain.model.UserProfile;
import br.com.atech.usersmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserApis {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody CreateUserDTO  createUserDTO){
        User user = userService.create(createUserDTO);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/disable")
    public ResponseEntity<User> disable(@PathVariable Long id){
        User user = userService.disable(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<User> enable(@PathVariable Long id){
        User user = userService.enable(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<User> update(@RequestBody UpdateUserDTO updateUserDTO){
        User user = userService.update(updateUserDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/{id}/change-password")
    public ResponseEntity<User> changeUserPassword(
        @PathVariable Long id,
        @RequestBody ChangeUserPasswordDTO changeUserPasswordDTO
    ){
        User user = userService.changeUserPassword(id,changeUserPasswordDTO);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<Page<User>> findByEmailNameOrUserName( @PathVariable String searchTerm,@PageableDefault(page = 0, size = 100) Pageable pageable){
        Page<User> users = userService.findByEmailNameOrUserName(pageable, searchTerm);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{active}/filter")
    public ResponseEntity<Page<User>> findAll(@PathVariable Optional<Boolean> active, @PageableDefault(page = 0, size = 100) Pageable pageable)
    {
        Page<User> users = userService.findAll(pageable, active);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{email}/profile")
    public ResponseEntity<UserProfile> findUserProfile(@PathVariable String email){
        UserProfile userProfile = userService.findUserProfile(email);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

}
