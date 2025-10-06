package za.co.shyftit.capitectransactionaggregator.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.shyftit.capitectransactionaggregator.dtos.CreateUserDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.EntityPostDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.services.CustomUserDetailsService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseObject<EntityPostDTO>> createUser(@RequestBody CreateUserDTO request) {
        var result = userDetailsService.createUser(request.username(), request.password());
        return ResponseEntity.status(result.getHttpStatus()).body(result);
    }
}
