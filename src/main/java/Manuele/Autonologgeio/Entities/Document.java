package Manuele.Autonologgeio.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
public class Document {
    @Id
    @GeneratedValue
    private long id;
    private String patente;
    private String codFiscale;
    private String indirizzo;
    private String Citta;
    private String Cap;

    @OneToOne
    @JoinColumn(name = "utente_id")
    private User utente;
}
