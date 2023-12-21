package Manuele.Autonologgeio.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "rents")
@Getter
@Setter
public class Rent {
    @Id
    @GeneratedValue
    private long id;

    private LocalDate startRent;
    private LocalDate finishRent;

    @ManyToOne
    @JoinColumn(name = "documento_id")
    private Document documento;

    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;


}
