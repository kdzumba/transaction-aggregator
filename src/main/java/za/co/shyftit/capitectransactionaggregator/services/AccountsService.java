package za.co.shyftit.capitectransactionaggregator.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.shyftit.capitectransactionaggregator.dtos.CreateAccountDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.EntityPostDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.models.Account;
import za.co.shyftit.capitectransactionaggregator.models.User;
import za.co.shyftit.capitectransactionaggregator.repositories.AccountsRepository;
import za.co.shyftit.capitectransactionaggregator.repositories.UsersRepository;

import java.time.Instant;
import java.util.Optional;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final UsersRepository usersRepository;

    public AccountsService(AccountsRepository accountsRepository, UsersRepository usersRepository) {
        this.accountsRepository = accountsRepository;
        this.usersRepository = usersRepository;
    }

    public ResponseObject<EntityPostDTO> createAccount(CreateAccountDTO request) {
        var response = new ResponseObject<EntityPostDTO>();

        try {
            Optional<User> userOpt = Optional.empty();

            if (request.userId() != null) {
                userOpt = usersRepository.findById(request.userId());
            } else if (request.username() != null) {
                userOpt = usersRepository.findByUsername(request.username());
            }

            if (userOpt.isEmpty()) {
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
                response.setMessage("User not found for provided username or ID");
                return response;
            }

            User user = userOpt.get();

            Account account = new Account();
            account.setUser(user);
            account.setAccountNumber(request.accountNumber());
            account.setDateCreated(Instant.now());

            Account saved = accountsRepository.save(account);

            response.setHttpStatus(HttpStatus.CREATED);
            response.setMessage("Account created successfully");
            response.setResult(new EntityPostDTO(saved.getId(), null));
        } catch (Exception e) {
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error creating account: " + e.getMessage());
        }

        return response;
    }
}
