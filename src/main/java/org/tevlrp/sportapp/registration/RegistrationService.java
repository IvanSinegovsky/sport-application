package org.tevlrp.sportapp.registration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tevlrp.sportapp.appuser.AppUser;
import org.tevlrp.sportapp.appuser.AppUserRole;
import org.tevlrp.sportapp.appuser.AppUserService;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email is not valid!");
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }
}
