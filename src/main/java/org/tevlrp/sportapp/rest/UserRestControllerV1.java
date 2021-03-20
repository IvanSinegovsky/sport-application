package org.tevlrp.sportapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tevlrp.sportapp.dto.UserDto;
import org.tevlrp.sportapp.model.User;
import org.tevlrp.sportapp.service.UserService;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto userDto = UserDto.fromUser(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
