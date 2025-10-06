package za.co.shyftit.capitectransactionaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.shyftit.capitectransactionaggregator.models.User;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
