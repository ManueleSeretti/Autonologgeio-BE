package Manuele.Autonologgeio.Service;

import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.NewUserDTO;
import Manuele.Autonologgeio.Repository.UserRepository;
import Manuele.Autonologgeio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
//    private Cloudinary cloudinary;


    public Page<User> getAllUser(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return userRepository.findAll(pageable);
    }

    public User findById(long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(long id, NewUserDTO body) throws NotFoundException {
        User found = userRepository.findById(id).get();
        found.setSurname(body.surname());
        found.setName(body.name());
        found.setEmail(body.email());
        //found.setPassword(bcrypt.encode(body.password()));
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

//    public String uploadAvatar(long id, MultipartFile file) throws IOException {
//        try {
//            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//            String imageUrl = (String) uploadResult.get("url");
//
//            User utente = userRepository.findById(id).orElse(null);
//            if (utente != null) {
//                utente.setAvatar(imageUrl);
//                userRepository.save(utente);
//            }
//
//            return imageUrl;
//        } catch (IOException e) {
//            throw new RuntimeException("Impossibile caricare l'immagine", e);
//        }
//    }
}
