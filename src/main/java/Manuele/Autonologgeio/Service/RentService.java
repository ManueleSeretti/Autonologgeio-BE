package Manuele.Autonologgeio.Service;

import Manuele.Autonologgeio.Entities.Auto;
import Manuele.Autonologgeio.Entities.Document;
import Manuele.Autonologgeio.Entities.Rent;
import Manuele.Autonologgeio.Payloads.NewRentDTO;
import Manuele.Autonologgeio.Repository.RentRepository;
import Manuele.Autonologgeio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RentService {
    @Autowired
    private RentRepository rentRepository;

    @Autowired
    private AutoService autoService;

    @Autowired
    private DocumentService documentService;


    public Page<Rent> getAllRent(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return rentRepository.findAll(pageable);
    }

    public Rent save(NewRentDTO body) throws IOException {
        Rent r = new Rent();
        Auto a = autoService.findById(body.autoID());
        Document d = documentService.findById(body.documentId());
        r.setStartRent(body.startRent());
        r.setFinishRent(body.finishRent());
        r.setAuto(a);
        r.setDocumento(d);
        return rentRepository.save(r);
    }

    public Rent findById(long id) throws NotFoundException {
        return rentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public List<Rent> getRentsByDocumento(Document documento) {
        return rentRepository.findByDocumento(documento);
    }

    public Rent findByIdAndUpdate(long id, NewRentDTO body) throws NotFoundException {
        Rent found = rentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        Auto a = autoService.findById(body.autoID());
        Document d = documentService.findById(body.documentId());
        found.setStartRent(body.startRent());
        found.setFinishRent(body.finishRent());
        found.setAuto(a);
        found.setDocumento(d);
        return rentRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Rent found = this.findById(id);
        rentRepository.delete(found);
    }


}
