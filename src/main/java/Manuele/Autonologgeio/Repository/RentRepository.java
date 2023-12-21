package Manuele.Autonologgeio.Repository;

import Manuele.Autonologgeio.Entities.Document;
import Manuele.Autonologgeio.Entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    @Query("SELECT COUNT(r) FROM Rent r WHERE r.auto.id = :autoId AND r.startRent <= :endDate AND r.finishRent >= :startDate")
    long countRentsByAutoAndPeriod(long autoId, LocalDate startDate, LocalDate endDate);

    List<Rent> findByDocumento(Document documento);
}
