package br.com.atech.usersmanagement.infra.repository;

import br.com.atech.usersmanagement.infra.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity,Long> {
}
