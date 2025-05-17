package com.headlinehive.service;

import com.headlinehive.model.User;
import java.util.Optional;

public interface UserService {
    boolean existsByEmail(String email);
    void saveUser(User user);
    Optional<User> findByEmail(String email);
}
