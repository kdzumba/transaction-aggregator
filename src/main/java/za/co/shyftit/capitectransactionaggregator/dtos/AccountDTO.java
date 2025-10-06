package za.co.shyftit.capitectransactionaggregator.dtos;

import java.time.Instant;

public record AccountDTO(
        Long id,
        Long userId,
        String username,
        String accountNumber,
        Instant dateCreated
) {}
