package Manuele.Autonologgeio.Payloads;

import Manuele.Autonologgeio.Entities.Document;
import Manuele.Autonologgeio.Entities.Rent;
import Manuele.Autonologgeio.Entities.User;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ProfiloDTO(
        @NotEmpty(message = "L'email è un campo obbligatorio!")
        User user,
        @NotEmpty(message = "La password è un campo obbligatorio!")
        Document documet,
       
        List<Rent> rentList
) {

}
