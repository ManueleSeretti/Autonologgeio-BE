package Manuele.Autonologgeio.Controllers;

import Manuele.Autonologgeio.Entities.Auto;
import Manuele.Autonologgeio.Payloads.NewAutoDTO;
import Manuele.Autonologgeio.Service.AutoService;
import Manuele.Autonologgeio.Service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/autos")

public class AutoController {
    @Autowired
    private AutoService autoService;
    @Autowired
    private RentService rentService;

    @GetMapping("")
    public List<Auto> getAllAuto() {
        return autoService.getAllAuto();
    }

    @GetMapping("/filter")
    public List<Auto> getAllAutoDisponibili(@RequestParam LocalDate d1,
                                            @RequestParam LocalDate d2) {
        return autoService.getAllAutoDisponibili(d1, d2);
    }


    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    public Auto creaAuto(@RequestBody @Validated NewAutoDTO auto) throws IOException {
        return autoService.save(auto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    public Auto updateAuto(@RequestBody @Validated NewAutoDTO auto, @PathVariable long id) throws IOException {
        return autoService.findByIdAndUpdate(id, auto);
    }

    @GetMapping("/{id}")
    public Auto findById(@PathVariable int id) {
        return autoService.findById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
//    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) {
        autoService.findByIdAndDelete(id);
    }

}
