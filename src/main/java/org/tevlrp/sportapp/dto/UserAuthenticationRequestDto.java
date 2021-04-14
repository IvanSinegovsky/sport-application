package org.tevlrp.sportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserAuthenticationRequestDto {
    private String email;
    private String password;
}
