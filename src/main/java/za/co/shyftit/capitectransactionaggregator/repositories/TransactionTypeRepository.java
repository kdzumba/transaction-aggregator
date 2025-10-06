package za.co.shyftit.capitectransactionaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.shyftit.capitectransactionaggregator.models.TransactionType;

public interface TransactionTypeRepository extends JpaRepository<TransactionType, Long> {
    TransactionType findByName(String type);
}