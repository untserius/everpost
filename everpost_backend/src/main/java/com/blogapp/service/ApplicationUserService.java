package com.blogapp.service;

import com.blogapp.repository.ApplicationUserRepository;
import com.blogapp.security.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService implements UserDetailsService {

    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserService(@Qualifier("mysqlDB") ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> byUsername = applicationUserRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            ApplicationUser applicationUser = byUsername.get();
            return applicationUser;
        }
        throw new UsernameNotFoundException(String.format("Username %s not found", username));
    }
}
