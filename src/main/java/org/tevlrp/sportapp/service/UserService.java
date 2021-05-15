package org.tevlrp.sportapp.service;

import org.tevlrp.sportapp.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> register(User user);

    List<User> getAll();

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    void delete(Long id);
}
