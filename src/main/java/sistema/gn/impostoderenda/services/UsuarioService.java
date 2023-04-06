package sistema.gn.impostoderenda.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.gn.impostoderenda.DTOs.UsuarioRequestDto;
import sistema.gn.impostoderenda.Status;
import sistema.gn.impostoderenda.entities.Usuario;
import sistema.gn.impostoderenda.repositories.UsuarioRepository;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario save(UsuarioRequestDto usuarioRequestDto) {

        Usuario usuario = Usuario.builder()
                .id(null)
                .nome(usuarioRequestDto.getNome())
                .cpf(usuarioRequestDto.getCpf())
                .salario(usuarioRequestDto.getSalario())
                .build();

        usuarioRepository.save(usuario);
        this.mensagenObservacao(usuario);
        this.calcularImpostoDeRenda(usuario);

        return usuario;
    }

    private void calcularImpostoDeRenda(Usuario usuario) {

        double TETO = 28559.7;

        double resultado = usuario.getSalario() * 12;
        usuario.setRendimentoTributavel(resultado);//recebe o valor do salario multiplicado por 12

        if (resultado < TETO) {
            usuario.setResultado_imposto(Status.VOCE_ESTA_INSENTO_DA_OBRIGATORIEDADE);
        } else {
            usuario.setResultado_imposto(Status.VOCE_ESTA_OBRIGADO_A_DECLARAR_O_IMPOSTO_DE_RENDA);

        }

        usuarioRepository.save(usuario);

    }

    //ALERTA O USUARIO QUE NAO ESTA OBRIGADO MAS PODE TER UM VALOR A REESTITUIR
    private void mensagenObservacao(Usuario usuario) {

        if (usuario.getSalario() >= 2200 && usuario.getSalario() <= 2370) {
            usuario.setObservacao("ATENÇAO VOÇE NÃO ESTA OBRIGADO A DECLARAR , MAS PODE TER REESTITUIÇAO A RECEBER, CONSULTE UMA CONTABILIDADE COM SEU INFORME DE RENDIMENTO");
        }

        usuarioRepository.save(usuario);

    }
}
