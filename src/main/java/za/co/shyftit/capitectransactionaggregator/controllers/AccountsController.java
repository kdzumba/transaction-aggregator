package za.co.shyftit.capitectransactionaggregator.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.shyftit.capitectransactionaggregator.dtos.CreateAccountDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.EntityPostDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.services.AccountsService;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<EntityPostDTO>> createAccount(@RequestBody CreateAccountDTO request) {
        var result = accountsService.createAccount(request);
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }
}
