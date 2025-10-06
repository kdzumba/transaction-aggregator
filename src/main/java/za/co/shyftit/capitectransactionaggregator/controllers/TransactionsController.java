package za.co.shyftit.capitectransactionaggregator.controllers;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.shyftit.capitectransactionaggregator.dtos.EntityPostDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.dtos.TransactionDTO;
import za.co.shyftit.capitectransactionaggregator.services.TransactionsService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<ResponseObject<List<TransactionDTO>>> getTransactionsByDateRange(
            @RequestParam Instant startDate,
            @RequestParam Instant endDate,
            Pageable pageable) {
        var result = transactionsService.getTransactionsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }

    @GetMapping("/by-category")
    public ResponseEntity<ResponseObject<List<TransactionDTO>>> getTransactionsByCategory(
            @RequestParam String category,
            Pageable pageable
    ) {
        var result = transactionsService.getTransactionsByCategory(category, pageable);
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<EntityPostDTO>> createTransaction(@RequestBody TransactionDTO dto) {
        var result = transactionsService.createTransaction(dto);
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }
}
