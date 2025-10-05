package za.co.shyftit.capitectransactionaggregator.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionDTO(
        Long id,
        String accountNumber,
        String type,
        String category,
        BigDecimal amount,
        String description,
        Instant date)
{}
