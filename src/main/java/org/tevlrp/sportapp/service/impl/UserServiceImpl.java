package org.tevlrp.sportapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.Role;
import org.tevlrp.sportapp.model.Status;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.repository.RoleRepository;
import org.tevlrp.sportapp.repository.UserRepository;
import org.tevlrp.sportapp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(user);

        log.info("IN UserServiceImpl register - user: {} successfully registered", registeredUser);

        return Optional.ofNullable(registeredUser);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN UserServiceImpl getAll - {} users found", result.size());
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User result = userRepository.findByEmail(email);
        log.info("IN UserServiceImpl findByUsername - user: {} found by email: {}", result, email);
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<User> findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        return Optional.ofNullable(result);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN UserServiceImpl delete - user with id: {} successfully deleted", id);
    }
}
