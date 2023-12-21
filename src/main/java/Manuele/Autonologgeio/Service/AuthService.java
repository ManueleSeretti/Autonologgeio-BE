package Manuele.Autonologgeio.Service;

import Manuele.Autonologgeio.Entities.Role;
import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.NewUserDTO;
import Manuele.Autonologgeio.Payloads.UserLoginDTO;
import Manuele.Autonologgeio.Repository.UserRepository;
import Manuele.Autonologgeio.Security.JWTTools;
import Manuele.Autonologgeio.exceptions.BadRequestException;
import Manuele.Autonologgeio.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;

@Service
public class AuthService {
    @Autowired
    private UserService usersService;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private UserRepository usersRepository;

    public String authenticateUser(UserLoginDTO body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        User user = usersService.findByEmail(body.email());

        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if (bcrypt.matches(body.password(), user.getPassword())) {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public User registerUser(NewUserDTO body) throws IOException {

        // verifico se l'email è già utilizzata
        usersRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });

        User newUser = new User();

        newUser.setPassword(bcrypt.encode(body.password())); // $2a$11$wQyZ17wrGu8AZeb2GCTcR.QOotbcVd9JwQnnCeqONWWP3wRi60tAO
        newUser.setEmail(body.email());
        newUser.setRole(Collections.singletonList(Role.USER));
        User savedUser = usersRepository.save(newUser);
        return savedUser;
    }
}
