package za.co.shyftit.capitectransactionaggregator.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import za.co.shyftit.capitectransactionaggregator.models.Transaction;

import java.awt.print.Pageable;
import java.time.Instant;

public interface TransactionsRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findByDateBetween(Instant startDate, Instant endDate, Pageable pageable);

    @Query("SELECT t FROM Transaction t WHERE t.category.name = :categoryName")
    Page<Transaction> findByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);
}
