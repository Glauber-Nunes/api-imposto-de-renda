package sistema.gn.impostoderenda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String emailRementente;

    private String Destinatario;

    @Column(columnDefinition = "TEXT")
    private String texto1;

    @Column(columnDefinition = "TEXT")
    private String texto2;

    private String rodape;

}
