package za.co.shyftit.capitectransactionaggregator.dtos;

public record CreateAccountDTO(
        Long userId,
        String username,
        String accountNumber
) {}