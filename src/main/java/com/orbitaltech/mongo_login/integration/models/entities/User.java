package com.orbitaltech.mongo_login.integration.models.entities;

import com.orbitaltech.mongo_login.integration.models.embedded.Role;
import com.orbitaltech.mongo_login.integration.models.embedded.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String password;
    private String email;
    private Set<UserRole> roles = new HashSet<>();


    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        roles.add(role);
    }


    public boolean hasRole(String roleName) {
        return getRoles().stream()
                .map(role -> role.getAuthority().equals(roleName))
                .findFirst()
                .isPresent();
    }

    @Override
    public Set<UserRole> getAuthorities() {
        return this.roles;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
