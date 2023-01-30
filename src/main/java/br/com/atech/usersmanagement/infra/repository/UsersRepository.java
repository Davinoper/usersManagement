package br.com.atech.usersmanagement.infra.repository;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import org.hibernate.annotations.NamedNativeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface UsersRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT u FROM User u "
    + "WHERE (u.email) LIKE %:email%"
    )
    User findByEmail(String email);
}
