package org.tevlrp.sportapp.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.security.jwt.JwtUser;
import org.tevlrp.sportapp.security.jwt.JwtUserFactory;
import org.tevlrp.sportapp.service.UserService;

import java.util.Optional;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(userOptional.get());
        log.info("IN loadUserByUsername - user with email: {} successfully loaded", email);
        return jwtUser;
    }
}