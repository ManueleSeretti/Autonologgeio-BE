package Manuele.Autonologgeio.Controllers;

import Manuele.Autonologgeio.Entities.Auto;
import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.NewUserDTO;
import Manuele.Autonologgeio.Payloads.UserLoginDTO;
import Manuele.Autonologgeio.Payloads.UserLoginSuccessDTO;
import Manuele.Autonologgeio.Service.AuthService;
import Manuele.Autonologgeio.Service.AutoService;
import Manuele.Autonologgeio.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AutoService autoService;


    @GetMapping("/autos")
    public List<Auto> getAllAuto() {
        return autoService.getAllAuto();
    }

    @GetMapping("/filter")
    public List<Auto> getAllAutoDisponibili(@RequestParam LocalDate d1,
                                            @RequestParam LocalDate d2) {
        return autoService.getAllAutoDisponibili(d1, d2);
    }

    @PostMapping("/login")
    public UserLoginSuccessDTO login(@RequestBody UserLoginDTO body) throws Exception {
        System.out.println(body);
        return new UserLoginSuccessDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        System.out.println(body);
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
