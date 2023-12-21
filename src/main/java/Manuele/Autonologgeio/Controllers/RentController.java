package Manuele.Autonologgeio.Controllers;

import Manuele.Autonologgeio.Entities.Rent;
import Manuele.Autonologgeio.Payloads.NewRentDTO;
import Manuele.Autonologgeio.Service.RentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/rents")
@OpenAPIDefinition(
        security = {@SecurityRequirement(name = "bearerAuth")}
)
public class RentController {
    @Autowired
    private RentService rentService;

    @GetMapping("")
    public Page<Rent> getAllRent(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String orderBy) {
        return rentService.getAllRent(page, size, orderBy);
    }


    @PostMapping("")
    public Rent creaRent(@RequestBody @Validated NewRentDTO rent) throws IOException {
        return rentService.save(rent);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public Rent updateRent(@RequestBody @Validated NewRentDTO rent, @PathVariable long id) throws IOException {
        return rentService.findByIdAndUpdate(id, rent);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    public Rent findById(@PathVariable int id) {
        return rentService.findById(id);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) {
        rentService.findByIdAndDelete(id);
    }

}