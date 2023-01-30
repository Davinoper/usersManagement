package br.com.atech.usersmanagement.infra.repository;

import br.com.atech.usersmanagement.domain.dto.CreateUserDTO;
import br.com.atech.usersmanagement.domain.model.User;
import org.hibernate.annotations.NamedNativeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface UsersRepository extends JpaRepository<User,Long> {

    @Query(value = " SELECT u FROM User u "
            + " WHERE LOWER (u.name ) LIKE %:searchTerm% "
            + " OR LOWER (u.email) LIKE %:searchTerm% "
            + " OR LOWER (u.userName) LIKE %:searchTerm% ")
    User findByEmailNameOrUserName(String searchTerm);
}
