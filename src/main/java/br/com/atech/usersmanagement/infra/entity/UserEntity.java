package br.com.atech.usersmanagement.infra.entity;

import br.com.atech.usersmanagement.domain.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String userName;
    private boolean active;

    private UserEntity (User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.userName = user.getUserName();
        this.active = true;
    }

    public static UserEntity create(User user){
        return new UserEntity(user);
    }
}
