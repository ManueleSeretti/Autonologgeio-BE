package Manuele.Autonologgeio.Payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewRentDTO(
        LocalDate startRent,
        LocalDate finishRent,
        @NotNull(message = "L' ID dello user è un campo obbligatorio!")
        long documentId,
        @NotNull(message = "L' ID dell auto è un campo obbligatorio!")
        long autoID) {
}
