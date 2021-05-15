package org.tevlrp.sportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tevlrp.sportapp.dto.AdminUserDto;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/admin/")
public class AdminControllerV1 {
    private final UserService userService;

    @Autowired
    public AdminControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(userOptional.get());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
