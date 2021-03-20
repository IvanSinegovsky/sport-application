package org.tevlrp.sportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tevlrp.sportapp.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
