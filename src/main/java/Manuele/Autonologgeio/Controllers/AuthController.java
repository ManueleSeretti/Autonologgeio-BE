package Manuele.Autonologgeio.Controllers;

import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.NewUserDTO;
import Manuele.Autonologgeio.Payloads.UserLoginDTO;
import Manuele.Autonologgeio.Payloads.UserLoginSuccessDTO;
import Manuele.Autonologgeio.Service.AuthService;
import Manuele.Autonologgeio.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body) throws Exception {

        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authService.registerUser(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
