package Manuele.Autonologgeio.Repository;

import Manuele.Autonologgeio.Entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {

}
