package za.co.shyftit.capitectransactionaggregator.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.dtos.TransactionDTO;
import za.co.shyftit.capitectransactionaggregator.models.Transaction;
import za.co.shyftit.capitectransactionaggregator.repositories.TransactionsRepository;

import java.time.Instant;
import java.util.List;

@Service
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;

    public TransactionsService(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public ResponseObject<List<TransactionDTO>> getTransactionsByDateRange(Instant startDate, Instant endDate, Pageable pageable) {
        var response = new ResponseObject<List<TransactionDTO>>();
        try {
            var transactionsPage = transactionsRepository.findByDateBetween(startDate, endDate, pageable);
            buildResponse(response, transactionsPage);

        } catch(Exception e) {
            response.setMessage(String.format("An error occurred when trying to retrieve transactions between: %s and %s", startDate, endDate));
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseObject<List<TransactionDTO>> getTransactionsByCategory(String categoryName, Pageable pageable) {
        var response = new ResponseObject<List<TransactionDTO>>();
        try {
            var transactionsPage = transactionsRepository.findByCategoryName(categoryName, pageable);
            buildResponse(response, transactionsPage);
        } catch(Exception e) {
            response.setMessage(String.format("An error occurred when trying to retrieve transactions for category: %s", categoryName));
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private void buildResponse(ResponseObject<List<TransactionDTO>> response, Page<Transaction> transactionsPage) {
        var transactions = transactionsPage.getContent();
        var transactionDTOs = transactions.stream().map(t -> new TransactionDTO(
                        t.getId(),
                        t.getAccount().getAccountNumber(),
                        t.getType().getName(),
                        t.getCategory().getName(),
                        t.getAmount(),
                        t.getDescription(),
                        t.getDate())).toList();
        response.setMessage("Transactions retrieved successfully");
        response.setHttpStatus(HttpStatus.OK);
        response.setResult(transactionDTOs);
    }
}
