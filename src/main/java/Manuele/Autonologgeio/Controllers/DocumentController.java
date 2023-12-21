package Manuele.Autonologgeio.Controllers;

import Manuele.Autonologgeio.Entities.Document;
import Manuele.Autonologgeio.Payloads.NewDocumentDTO;
import Manuele.Autonologgeio.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/documets")
//@OpenAPIDefinition(
////        security = {@SecurityRequirement(name = "bearerAuth")}
//)
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<Document> getAllAuto(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String orderBy) {
        return documentService.getAllDocument(page, size, orderBy);
    }

    @PostMapping("")

//    @SecurityRequirement(name = "bearerAuth")
    public Document creaDocument(@RequestBody @Validated NewDocumentDTO document) throws IOException {
        return documentService.save(document);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    public Document updateDocumet(@RequestBody @Validated NewDocumentDTO document, @PathVariable long id) throws IOException {
        return documentService.findByIdAndUpdate(id, document);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    public Document findById(@PathVariable int id) {
        return documentService.findById(id);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) {
        documentService.findByIdAndDelete(id);
    }

}
