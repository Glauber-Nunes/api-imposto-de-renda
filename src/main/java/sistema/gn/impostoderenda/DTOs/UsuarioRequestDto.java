package sistema.gn.impostoderenda.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.gn.impostoderenda.Status;
import sistema.gn.impostoderenda.entities.Usuario;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDto {
    private Long id;
    private String nome;
    private String cpf;
    private double salario;
    private double rendimentoTributavel;
    private Status resultado_imposto;
    public UsuarioRequestDto(Usuario usuario){
        this.id= usuario.getId();
        this.nome= usuario.getNome();
        this.cpf= usuario.getCpf();
        this.salario= usuario.getSalario();
        this.resultado_imposto = usuario.getResultado_imposto();
    }
}
