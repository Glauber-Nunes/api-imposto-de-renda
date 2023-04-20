package sistema.gn.impostoderenda.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import sistema.gn.impostoderenda.entities.Email;
import sistema.gn.impostoderenda.entities.Usuario;
import sistema.gn.impostoderenda.repositories.EmailRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Configuration configuration;
    @Autowired
    private EmailRepository emailRepository;
    @Value("${spring.mail.username}")
    private String emailRemetenteProperties;

    public void enviarEmailContato(Usuario usuario) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        Email emailModel = new Email();

        emailModel.setTexto1("Vi que voçe precisa declarar imposto de renda certo?, então enviamos para voçe o nosso contato :)");
        emailModel.setTexto2("Telefone whatsapp (81)-9832-8272");
        emailModel.setRodape("esse email foi gerado automaticamente por nosso software, por favor não o responda");
        emailModel.setDestinatario(usuario.getEmail()); // usuario que recebeu o email
        emailModel.setEmailRementente(emailRemetenteProperties); // email do properties que vai enviar para o usuario

        Map<String, Object> propriedades = new HashMap<>();
        propriedades.put("nome", usuario.getNome());
        propriedades.put("mensagem1", emailModel.getTexto1());
        propriedades.put("mensagem2", emailModel.getTexto2());
        propriedades.put("rodape", emailModel.getRodape());

        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Meu Imposto De Renda");
            mimeMessageHelper.setFrom(emailModel.getEmailRementente());
            mimeMessageHelper.setTo(usuario.getEmail());
            mimeMessageHelper.setText(this.getConteudoTemplate(propriedades), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        emailRepository.save(emailModel);
    }

    public void enviarEmailObservacaoReestituicao(Usuario usuario) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        Email emailModel = new Email();

        Map<String, Object> propriedades = new HashMap<>();
        propriedades.put("nome", usuario.getNome());
        propriedades.put("mensagem1", "Voçe Pode ter Reestituiçao para receber de imposto de renda, entre em contato com o nosso escritorio");
        propriedades.put("mensagem2", "Telefone whatsapp (81)-9832-8272");
        propriedades.put("rodape", "esse email foi gerado automaticamente por nosso software, por favor não o responda");
        try {

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject("Reestituição Imposto De Renda");
            mimeMessageHelper.setFrom(emailModel.getEmailRementente());
            mimeMessageHelper.setTo(usuario.getEmail());

            mimeMessageHelper.setText(this.getConteudoTemplate(propriedades), true);

            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String getConteudoTemplate(Map<String, Object> model) {
        StringBuffer content = new StringBuffer();

        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("email-flth"), model));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
