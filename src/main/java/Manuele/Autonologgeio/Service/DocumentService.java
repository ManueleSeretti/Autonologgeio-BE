package Manuele.Autonologgeio.Service;

import Manuele.Autonologgeio.Entities.Document;
import Manuele.Autonologgeio.Entities.User;
import Manuele.Autonologgeio.Payloads.NewDocumentDTO;
import Manuele.Autonologgeio.Repository.DocumentRepository;
import Manuele.Autonologgeio.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserService userService;

    public Page<Document> getAllDocument(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return documentRepository.findAll(pageable);
    }

    public Document save(NewDocumentDTO body) throws IOException {
        Document d = new Document();
        User u = userService.findById(body.usertId());
        d.setPatente(body.patente());
        d.setCodFiscale(body.codFiscale());
        d.setIndirizzo(body.indirizzo());
        d.setCitta(body.citta());
        d.setCap(body.cap());
        d.setUtente(u);
        return documentRepository.save(d);
    }


    public Document findById(long id) throws NotFoundException {
        return documentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Document findByIdAndUpdate(long id, NewDocumentDTO body) throws NotFoundException {
        Document found = documentRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        found.setPatente(body.patente());
        found.setCodFiscale(body.codFiscale());
        found.setIndirizzo(body.indirizzo());
        found.setCitta(body.citta());
        found.setCap(body.cap());
        return documentRepository.save(found);
    }

    public void findByIdAndDelete(long id) throws NotFoundException {
        Document found = this.findById(id);
        documentRepository.delete(found);
    }

}
