package Manuele.Autonologgeio.exceptions;


import java.time.LocalDate;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("L'elemento con id: " + id + " non è stato trovato. Riprovare con un id diverso");
    }

    public NotFoundException(double fattura) {
        super("La fattura con importo: " + fattura + " non è stato trovato. Riprovare con un importo diverso");
    }

    public NotFoundException(String elem) {
        super("L'elemento: " + elem + " non è stato trovato. Riprovare");
    }

    public NotFoundException(LocalDate data) {
        super("L'elemento in data: " + data + " non corrisponde a nulla. Riprova con un'altra data");
    }

    public NotFoundException(double importo1, double importo2) {
        super("Non sono state trovate fatture con importi in questo range");
    }
}
