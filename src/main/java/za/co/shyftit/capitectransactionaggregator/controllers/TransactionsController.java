package za.co.shyftit.capitectransactionaggregator.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.shyftit.capitectransactionaggregator.dtos.TransactionDTO;
import za.co.shyftit.capitectransactionaggregator.services.TransactionsService;

import java.time.Instant;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/by-date-range")
    public Page<TransactionDTO> getTransactionsByDateRange(
            @RequestParam Instant startDate,
            @RequestParam Instant endDate,
            Pageable pageable) {
        return transactionsService.getTransactionsByDateRange(startDate, endDate, pageable);
    }

    @GetMapping("/by-category")
    public Page<TransactionDTO> getTransactionsByCategory(
            @RequestParam String category,
            Pageable pageable
    ) {
        return transactionsService.getTransactionsByCategory(category, pageable);
    }
}
