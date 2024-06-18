package com.orbitaltech.mongo_login.integration.models.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements GrantedAuthority {

    private Role role;


    @Override
    public String getAuthority() {
        return role.getName();
    }


}
