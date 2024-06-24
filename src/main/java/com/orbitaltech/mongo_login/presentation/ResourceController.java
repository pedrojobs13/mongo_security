package com.orbitaltech.mongo_login.presentation;

import com.orbitaltech.mongo_login.integration.models.embedded.Role;
import com.orbitaltech.mongo_login.integration.models.entities.User;
import com.orbitaltech.mongo_login.integration.repository.UserRepository;
import com.orbitaltech.mongo_login.presentation.dto.LoginResponseDTO;
import com.orbitaltech.mongo_login.presentation.dto.RegisterDTO;
import com.orbitaltech.mongo_login.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private TokenService tokenService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Hello Admin!";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/user")
    public String user() {
        return "Hello User!";
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CREATOR')")
    @GetMapping("/creator")
    public String poster(){
        return "creator User!";
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO data) {
        if (repository.findUserByUsername(data.username()) != null) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        Role role = new Role(data.role());
        User newUser = new User(data.username(), encryptedPassword, data.email());
        newUser.addRole(role);
        repository.save(newUser);

        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody RegisterDTO data) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
