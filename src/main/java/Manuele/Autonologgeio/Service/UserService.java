package Manuele.Autonologgeio.Service;

import Manuele.Autonologgeio.Entities.Document;
import Manuele.Autonologgeio.Entities.Rent;
import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.NewUserDTO;
import Manuele.Autonologgeio.Payloads.ProfiloDTO;
import Manuele.Autonologgeio.Repository.DocumentRepository;
import Manuele.Autonologgeio.Repository.RentRepository;
import Manuele.Autonologgeio.Repository.UserRepository;
import Manuele.Autonologgeio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private RentRepository rentRepository;


    public Page<User> getAllUser(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return userRepository.findAll(pageable);
    }

    public User save(NewUserDTO body) throws IOException {
        User u = new User();
        u.setSurname(body.surname());
        u.setName(body.name());
        u.setEmail(body.email());
        u.setPassword(bcrypt.encode(body.password()));
        return userRepository.save(u);
    }

    public User findById(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public ProfiloDTO getProfileComplete(UserDetails currentUser) throws NotFoundException {
        User u = userRepository.findByEmail(currentUser.getUsername()).orElseThrow(() -> new NotFoundException(currentUser.getUsername()));
        Document d = u.getDocument();
        List<Rent> rentList = rentRepository.findByDocumento(d);
        return new ProfiloDTO(u, d, rentList);
    }

    public User findByIdAndUpdate(long id, NewUserDTO body) throws NotFoundException {
        User found = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        found.setSurname(body.surname());
        found.setName(body.name());
        found.setEmail(body.email());
        found.setPassword(bcrypt.encode(body.password()));
        return userRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        User found = this.findById(id);
        userRepository.delete(found);
    }

    public User findByEmail(String email) throws Exception {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User con email " + email + " non trovato"));
    }


}
