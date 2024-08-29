package com.blogapp.repository;

import com.blogapp.entity.User;
import com.blogapp.security.ApplicationUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository("mysqlDB")
public class AuthRepository implements ApplicationUserRepository{

    private final UserRepository userRepository;

    public AuthRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<ApplicationUser> findByUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);

        Optional<ApplicationUser> applicationUser = byUsername.map(user -> {
            Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());

            return new ApplicationUser(
                    user.getUsername(),
                    user.getPassword(),
                    authorities,
                    true,
                    true,
                    true,
                    true
            );
        });
        return applicationUser;
    }
}
