package sistema.gn.impostoderenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.gn.impostoderenda.entities.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
}