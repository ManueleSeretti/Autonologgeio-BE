package Manuele.Autonologgeio.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cars")
@Getter
@Setter
public class Auto {
    @Id
    @GeneratedValue
    private long id;

    private String targa;
    private String brand;
    private String model;
    private Alimentazione alimentazione;
    private Categoria categoria;
    private String image;
    private int year;
    private int nDoors;
}
