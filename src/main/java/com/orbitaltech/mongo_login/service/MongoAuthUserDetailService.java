package com.orbitaltech.mongo_login.service;

import com.orbitaltech.mongo_login.integration.models.embedded.Role;
import com.orbitaltech.mongo_login.integration.models.entities.User;
import com.orbitaltech.mongo_login.integration.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class MongoAuthUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(userName);

        for (GrantedAuthority grantedAuthorities : user.getAuthorities()) {
            user.addRole(new Role(grantedAuthorities.getAuthority()));
        }


        return user;
    }


}
