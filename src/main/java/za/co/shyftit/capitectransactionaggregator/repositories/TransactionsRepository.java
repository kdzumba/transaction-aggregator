package za.co.shyftit.capitectransactionaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.shyftit.capitectransactionaggregator.models.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
}
