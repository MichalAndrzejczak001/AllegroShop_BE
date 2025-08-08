package spring.project.allegroshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.project.allegroshop.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
