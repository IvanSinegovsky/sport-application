package org.tevlrp.sportapp.repository;

import org.tevlrp.sportapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
