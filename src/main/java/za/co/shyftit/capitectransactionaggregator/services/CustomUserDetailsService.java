package za.co.shyftit.capitectransactionaggregator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.shyftit.capitectransactionaggregator.dtos.EntityPostDTO;
import za.co.shyftit.capitectransactionaggregator.dtos.ResponseObject;
import za.co.shyftit.capitectransactionaggregator.models.User;
import za.co.shyftit.capitectransactionaggregator.repositories.UsersRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return user;
    }

    public ResponseObject<EntityPostDTO> createUser(String username, String rawPassword) {
        var response = new ResponseObject<EntityPostDTO>();

        try {
            if (userRepository.findByUsername(username).isPresent()) {
                response.setMessage("Username already exists");
                response.setHttpStatus(HttpStatus.BAD_REQUEST);
            }

            User user = new User();
            user.setUsername(username);
            user.setPasswordHash(passwordEncoder.encode(rawPassword));
            user.setDateCreated(Instant.now());
            userRepository.save(user);

            response.setMessage("User successfully created");
            response.setHttpStatus(HttpStatus.CREATED);
            response.setResult(new EntityPostDTO(user.getId(), null));
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
