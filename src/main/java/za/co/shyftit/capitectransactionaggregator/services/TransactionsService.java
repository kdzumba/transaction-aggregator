package za.co.shyftit.capitectransactionaggregator.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import za.co.shyftit.capitectransactionaggregator.dtos.TransactionDTO;
import za.co.shyftit.capitectransactionaggregator.repositories.TransactionsRepository;

import java.time.Instant;

@Service
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;

    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public Page<TransactionDTO> getTransactionsByDateRange(Instant startDate, Instant endDate, Pageable pageable) {
        return transactionsRepository.findByDateBetween(startDate, endDate, pageable)
                .map(t -> new TransactionDTO(
                        t.getId(),
                        t.getAccount().getAccountNumber(),
                        t.getType().getName(),
                        t.getCategory().getName(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getDate()
                ));
    }

    public Page<TransactionDTO> getTransactionsByCategory(String categoryName, Pageable pageable) {
        return transactionsRepository.findByCategoryName(categoryName, pageable)
                .map(t -> new TransactionDTO(
                        t.getId(),
                        t.getAccount().getAccountNumber(),
                        t.getType().getName(),
                        t.getCategory().getName(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getDate()
                ));
    }
}
