package sistema.gn.impostoderenda.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import sistema.gn.impostoderenda.Status;

import java.io.Serializable;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @CPF
    private String cpf;
    @Column(nullable = false)
    private double salario;

    private double rendimentoTributavel;

    private Status resultado_imposto;

    private String observacao;
}
