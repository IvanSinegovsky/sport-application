package org.tevlrp.sportapp.dto;

import lombok.Data;
import org.tevlrp.sportapp.model.User;

@Data
public class UserRegistrationDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User toUser(){
        User user = new User();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }

    public static UserRegistrationDto fromUser(User user) {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

        userRegistrationDto.setFirstName(user.getFirstName());
        userRegistrationDto.setLastName(user.getLastName());
        userRegistrationDto.setEmail(user.getEmail());
        userRegistrationDto.setPassword(user.getPassword());

        return userRegistrationDto;
    }
}
