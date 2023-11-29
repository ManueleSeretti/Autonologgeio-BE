package Manuele.Autonologgeio.Repository;

import Manuele.Autonologgeio.Entities.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
}
