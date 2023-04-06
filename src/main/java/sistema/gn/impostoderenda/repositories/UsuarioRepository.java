package sistema.gn.impostoderenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema.gn.impostoderenda.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}