package za.co.shyftit.capitectransactionaggregator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.shyftit.capitectransactionaggregator.models.TransactionCategory;

import java.util.Optional;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long> {
    Optional<TransactionCategory> findByName(String category);
}