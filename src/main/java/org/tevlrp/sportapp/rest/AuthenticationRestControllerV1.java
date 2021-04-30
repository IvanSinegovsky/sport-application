package org.tevlrp.sportapp.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.UserAuthenticationRequestDto;
import org.tevlrp.sportapp.dto.UserRegistrationDto;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.repository.WorkoutRepository;
import org.tevlrp.sportapp.security.jwt.JwtTokenProvider;
import org.tevlrp.sportapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.tevlrp.sportapp.service.WorkoutService;
import org.tevlrp.sportapp.service.impl.WorkoutServiceImpl;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationRestControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody UserAuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userService.findByEmail(email);

            if (user == null) {
                throw new UsernameNotFoundException("User with email: " + email + " does not exist");
            }

            String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());

            Map<Object, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    @PostMapping("registration")
    public ResponseEntity register(@RequestBody UserRegistrationDto userRegistrationDto) {
        log.info(userRegistrationDto.toString());

        User user = userRegistrationDto.toUser();
        User registeredUser = userService.register(user);

        if (registeredUser == null) {
            throw new BadCredentialsException("Cannot register user with such credentials");
        }

        UserAuthenticationRequestDto requestDto = new UserAuthenticationRequestDto(
                registeredUser.getEmail(),
                registeredUser.getPassword()
        );

        return login(requestDto);
    }

    //todo beta test method
    @GetMapping("auth")
    public ResponseEntity auth(@RequestHeader Map<String, String> headers) {
        String oldToken = headers.get("authorization");
        Long userId = Long.valueOf(jwtTokenProvider.getId(oldToken));

        User user = userService.findById(userId);
        String newToken = jwtTokenProvider.createToken(userId, user.getEmail());

        Map<Object, Object> response = new HashMap<>();
        response.put("token", newToken);

        return ResponseEntity.ok(response);
    }
}
