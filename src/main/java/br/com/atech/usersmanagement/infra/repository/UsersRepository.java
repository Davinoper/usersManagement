package br.com.atech.usersmanagement.infra.repository;

import br.com.atech.usersmanagement.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsersRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT u FROM User u "
    + "WHERE (u.active) = :active")
    Page<User> findAllWithFilter(Pageable pageable, boolean active);

    @Query(value ="SELECT u FROM User u "
    + "WHERE (u.email) = :email"
    )
    User findByEmail(String email);

    @Query(value ="SELECT u FROM User u "
            + "WHERE (u.userName) = :userName"
    )
    User findByUserName(String userName);

    @Query(value = " SELECT u FROM User u "
            + " WHERE LOWER (u.name ) LIKE %:searchTerm% "
            + " OR LOWER (u.email) LIKE %:searchTerm% "
            + " OR LOWER (u.userName) LIKE %:searchTerm% ")
    User findByEmailNameOrUserName(String searchTerm);
}
