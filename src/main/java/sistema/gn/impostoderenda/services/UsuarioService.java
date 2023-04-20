package sistema.gn.impostoderenda.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sistema.gn.impostoderenda.DTOs.UsuarioGetDto;
import sistema.gn.impostoderenda.DTOs.UsuarioRequestDto;
import sistema.gn.impostoderenda.enums.Status;
import sistema.gn.impostoderenda.entities.Usuario;
import sistema.gn.impostoderenda.repositories.UsuarioRepository;
import sistema.gn.impostoderenda.services.exceptions.NotFound;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public Usuario save(UsuarioRequestDto usuarioRequestDto) {

        Usuario usuario = Usuario.builder()
                .id(null)
                .nome(usuarioRequestDto.getNome())
                .cpf(usuarioRequestDto.getCpf())
                .email(usuarioRequestDto.getEmail())
                .salario(usuarioRequestDto.getSalario())
                .build();

        usuarioRepository.save(usuario);
        this.mensagenObservacao(usuario);// metodo que depende do salario
        this.calcularImpostoDeRenda(usuario);

        return usuario;
    }

    public UsuarioGetDto consultarResultado(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        usuario.orElseThrow(() -> new NotFound("Resultado Não Encontrado"));

        return new UsuarioGetDto(usuario.get());
    }

    public void delete(Long id) {

        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }

        throw new NotFound("Resultado Não Encontrado");
    }

    private void calcularImpostoDeRenda(Usuario usuario) {

        double valor_limite_ano_2022 = 28559.7; // variavel local
        usuario.setValor_limite_ano_2022(valor_limite_ano_2022);

        double resultado = usuario.getSalario() * 12;
        usuario.setRendimentoTributavel(resultado);//recebe o valor do salario multiplicado por 12

        if (resultado < valor_limite_ano_2022) {
            usuario.setResultado_imposto(Status.VOCE_ESTA_INSENTO_DA_DECLARAÇAO_DO_IMPOSTO_DE_RENDA);
        } else {
            usuario.setResultado_imposto(Status.VOCE_ESTA_OBRIGADO_A_DECLARAR_O_IMPOSTO_DE_RENDA);
            usuario.setObservacao("Verifique Seu Email");
            emailService.enviarEmailContato(usuario);
        }

        usuarioRepository.save(usuario);
    }

    //ALERTA O USUARIO QUE NAO ESTA OBRIGADO MAS PODE TER UM VALOR A REESTITUIR
    private void mensagenObservacao(Usuario usuario) {

        if (usuario.getSalario() >= 2200 && usuario.getSalario() <= 2375) {
            usuario.setObservacao("UM EMAIL FOI ENVIADO PARA VOÇE");
            emailService.enviarEmailObservacaoReestituicao(usuario);
        }

        usuarioRepository.save(usuario);

    }
}
