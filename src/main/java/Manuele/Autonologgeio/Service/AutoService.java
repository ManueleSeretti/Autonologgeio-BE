package Manuele.Autonologgeio.Service;

import Manuele.Autonologgeio.Entities.Auto;
import Manuele.Autonologgeio.Payloads.NewAutoDTO;
import Manuele.Autonologgeio.Repository.AutoRepository;
import Manuele.Autonologgeio.Repository.RentRepository;
import Manuele.Autonologgeio.exceptions.NotFoundException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AutoService {
    @Autowired
    private AutoRepository autoRepository;
    

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private RentRepository rentRepository;


    public List<Auto> getAllAuto() {
        return autoRepository.findAll();
    }

    public Auto save(NewAutoDTO body) throws IOException {
        Auto a = new Auto();
        a.setTarga(body.targa());
        a.setBrand(body.brand());
        a.setModel(body.model());
        a.setCategoria(body.categoria());
        a.setAlimentazione(body.alimentazione());
        a.setYear(body.year());
        a.setNDoors(body.nDoors());
        return autoRepository.save(a);

    }

    public Auto findById(long id) throws NotFoundException {
        return autoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Auto> getAllAutoDisponibili(LocalDate d1, LocalDate d2) {
        List<Auto> listaFiltrata = new ArrayList<>();
        List<Auto> autoList = autoRepository.findAll();
        listaFiltrata = autoList.stream().map(auto -> {
            long id = auto.getId();
            long x = rentRepository.countRentsByAutoAndPeriod(id, d1, d2);
            if (x > 0) {
                return auto;
            }
            return null;
        }).toList();
        return listaFiltrata;
    }


    public Auto findByIdAndUpdate(long id, NewAutoDTO body) throws NotFoundException {
        Auto found = autoRepository.findById(id).get();
        found.setTarga(body.targa());
        found.setBrand(body.brand());
        found.setModel(body.model());
        found.setCategoria(body.categoria());
        found.setAlimentazione(body.alimentazione());
        found.setYear(body.year());
        found.setNDoors(body.nDoors());
        return autoRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Auto found = this.findById(id);
        autoRepository.delete(found);
    }


    public String uploadImage(long id, MultipartFile file) throws IOException {
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");

            Auto utente = autoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
            if (utente != null) {
                utente.setImage(imageUrl);
                autoRepository.save(utente);
            }

            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }
    }
}
