package poultry_sales.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poultry_sales.api.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
