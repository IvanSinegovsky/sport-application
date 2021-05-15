package org.tevlrp.sportapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.tevlrp.sportapp.dto.UserAuthenticationRequestDto;
import org.tevlrp.sportapp.dto.UserRegistrationDto;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.security.jwt.JwtTokenProvider;
import org.tevlrp.sportapp.service.UserService;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthenticationControllerV1 {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserAuthenticationRequestDto requestDto) {
        try {
            String email = requestDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, requestDto.getPassword()));
            User user = userService.findByEmail(email);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());

            Map<String, String> body = new HashMap<>();
            body.put("email", email);
            body.put("token", token);

            return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("registration")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserRegistrationDto userRegistrationDto) {
        log.info(userRegistrationDto.toString());

        User user = userRegistrationDto.toUser();
        User registeredUser = userService.register(user);

        if (registeredUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserAuthenticationRequestDto requestDto = new UserAuthenticationRequestDto(
                registeredUser.getEmail(),
                registeredUser.getPassword()
        );

        return login(requestDto);
    }

    @GetMapping("auth")
    public ResponseEntity<Map<String, String>> auth(@RequestHeader Map<String, String> headers) {
        String oldToken = headers.get("authorization");
        Long userId = Long.valueOf(jwtTokenProvider.getId(oldToken));

        User user = userService.findById(userId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        String newToken = jwtTokenProvider.createToken(userId, user.getEmail());

        Map<String, String> body = new HashMap<>();
        body.put("token", newToken);

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
