package Manuele.Autonologgeio.Payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewUserDTO(@NotEmpty(message = "Il name è un campo obbligatorio!")
                         String name,
                         @NotEmpty(message = "IL surname è un campo obbligatorio!")
                         String surname,
                         @NotEmpty(message = "L'email è un campo obbligatorio!")
                         String email,
                         @NotEmpty(message = "La password è un campo obbligatorio!")
                         String password) {
}
