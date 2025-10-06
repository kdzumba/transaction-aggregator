package za.co.shyftit.capitectransactionaggregator.services;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.shyftit.capitectransactionaggregator.dtos.EntityPostDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.dtos.TransactionDTO;
import za.co.shyftit.capitectransactionaggregator.models.*;
import za.co.shyftit.capitectransactionaggregator.repositories.AccountsRepository;
import za.co.shyftit.capitectransactionaggregator.repositories.TransactionCategoryRepository;
import za.co.shyftit.capitectransactionaggregator.repositories.TransactionTypeRepository;
import za.co.shyftit.capitectransactionaggregator.repositories.TransactionsRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsService {
    private final TransactionsRepository transactionsRepository;
    private final AccountsRepository accountsRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionCategoryRepository transactionCategoryRepository;

    public TransactionsService(TransactionsRepository transactionsRepository,
                               AccountsRepository accountsRepository,
                               TransactionTypeRepository transactionTypeRepository,
                               TransactionCategoryRepository transactionCategoryRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
        this.transactionTypeRepository = transactionTypeRepository;
        this.transactionCategoryRepository = transactionCategoryRepository;
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

    @Transactional
    public ResponseObject<EntityPostDTO> createTransaction(TransactionDTO dto) {
        ResponseObject<EntityPostDTO> response = new ResponseObject<>();

        try {
            Optional<Account> accountOpt = accountsRepository.findByAccountNumber(dto.accountNumber());
            if (accountOpt.isEmpty()) {
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("Account not found for account number: " + dto.accountNumber());
                return response;
            }
            Account account = accountOpt.get();

            TransactionCategory category = transactionCategoryRepository.findByName(dto.category()).orElse(null);
            if (category == null) {
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("Invalid transaction category: " + dto.category());
                return response;
            }

            TransactionType type = transactionTypeRepository.findByName(dto.type());

            // Build and save transaction
            Transaction transaction = new Transaction();
            transaction.setAccount(account);
            transaction.setType(type);
            transaction.setCategory(category);
            transaction.setAmount(dto.amount());
            transaction.setDescription(dto.description());
            transaction.setDate(dto.date());
            transaction.setDateCreated(Instant.now());

            Transaction saved = transactionsRepository.save(transaction);

            TransactionDTO resultDTO = new TransactionDTO(
                    saved.getId(),
                    saved.getAccount().getAccountNumber(),
                    saved.getType().getName(),
                    saved.getCategory().getName(),
                    saved.getAmount(),
                    saved.getDescription(),
                    saved.getDate()
            );

            response.setHttpStatus(HttpStatus.CREATED);
            response.setMessage("Transaction created successfully");
            response.setResult(new EntityPostDTO(resultDTO.id(), null));
            return response;

        } catch (Exception e) {
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error creating transaction: " + e.getMessage());
            return response;
        }
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
