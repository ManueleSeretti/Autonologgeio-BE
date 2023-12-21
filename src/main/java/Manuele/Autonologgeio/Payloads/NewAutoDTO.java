package Manuele.Autonologgeio.Payloads;

import Manuele.Autonologgeio.Entities.Alimentazione;
import Manuele.Autonologgeio.Entities.Categoria;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewAutoDTO(@NotEmpty(message = "La targa è un campo obbligatorio!")
                         String targa,
                         @NotEmpty(message = "IL brand è un campo obbligatorio!")
                         String brand,
                         @NotEmpty(message = "Il modello è un campo obbligatorio!")
                         String model,

                         Alimentazione alimentazione,
                         Categoria categoria,
                         @NotNull(message = "L' anno' è un campo obbligatorio!")
                         int year,
                         @NotNull(message = "Il numero di  porte è un campo obbligatorio!")
                         int nDoors) {
}
