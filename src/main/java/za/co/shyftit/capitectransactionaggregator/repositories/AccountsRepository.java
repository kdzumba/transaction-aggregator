package za.co.shyftit.capitectransactionaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.shyftit.capitectransactionaggregator.models.Account;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String s);
}