package com.blogapp.repository;

import com.blogapp.security.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository {
    Optional<ApplicationUser> findByUsername(String username);
}
