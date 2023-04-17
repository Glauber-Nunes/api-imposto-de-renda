package sistema.gn.impostoderenda.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sistema.gn.impostoderenda.entities.Usuario;
import sistema.gn.impostoderenda.enums.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioGetDto {

    private String nome;
    private String email;
    private Status resultado_imposto;

    public UsuarioGetDto(Usuario usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.resultado_imposto = usuario.getResultado_imposto();
    }
}
