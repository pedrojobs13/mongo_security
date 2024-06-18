package com.orbitaltech.mongo_login.presentation;

import com.orbitaltech.mongo_login.integration.models.embedded.Role;
import com.orbitaltech.mongo_login.integration.models.embedded.UserRole;
import com.orbitaltech.mongo_login.integration.models.entities.User;
import com.orbitaltech.mongo_login.integration.repository.UserRepository;
import com.orbitaltech.mongo_login.presentation.dto.RegisterDTO;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }


    @PostMapping("/auth/register")
    public ResponseEntity register(@RequestBody RegisterDTO data) {
        if (repository.findUserByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());


        Role role = new Role("ROLE_ADMIN");
        UserRole userRole = new UserRole(role);

        User newUser = new User(data.username(), encryptedPassword, data.email(), userRole);

        repository.save(newUser);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/auth/login")
    public ResponseEntity login(@RequestBody RegisterDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/entrar")
    public ResponseEntity entrar() {


        return ResponseEntity.ok().build();
    }
}
