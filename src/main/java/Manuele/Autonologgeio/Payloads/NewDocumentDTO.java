package Manuele.Autonologgeio.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewDocumentDTO(@NotEmpty(message = "Il name è un campo obbligatorio!")
                             String patente,
                             @NotEmpty(message = "IL surname è un campo obbligatorio!")
                             String codFiscale,
                             @NotEmpty(message = "L'email è un campo obbligatorio!")
                             String indirizzo,
                             @NotEmpty(message = "L'email è un campo obbligatorio!")
                             String citta,
                             @NotEmpty(message = "L'email è un campo obbligatorio!")
                             String cap,

                             @NotNull(message = "L' ID dello user è un campo obbligatorio!")
                             long usertId
) {
}
