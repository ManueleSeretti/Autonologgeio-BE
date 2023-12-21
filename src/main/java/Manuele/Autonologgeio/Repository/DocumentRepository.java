package Manuele.Autonologgeio.Repository;

import Manuele.Autonologgeio.Entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
