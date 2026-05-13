package br.com.fiap.tc4.services;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import br.com.fiap.tc4.models.Feedback;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailService {

    @ConfigProperty(name = "resend.api.key")
    String apiKey;

    public void enviarEmail(Feedback feedback) {
        Resend resend = new Resend(apiKey);

        String urgencia = feedback.getNota() > 5 ? "MODERADA" : "CRÍTICA";

        String html = """
                <strong>Caros Administradores,</strong>
                </br>
                <p>Um novo feedback foi enviado e necessita de atenção:</p>
                </br>
                <p><strong>Data:</strong> %s</p>
                <p><strong>Urgência:</strong> %s</p>
                <p><strong>Nota:</strong> %d</p>
                </br>
                %s
                </br>
                </br>
                <p>Att.</p>
                <strong>EQUIPE DE ADMINSTRAÇÃO DE FEEDBACKS</strong>
                """.formatted(feedback.getData().toString(), urgencia, feedback.getNota(), feedback.getDescricao());

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Plataforma de Feedbacks <onboarding@resend.dev>")
                .to("joao.passone@gmail.com")
                .subject("[%s] Um novo feedback necessita de atenção".formatted(urgencia))
                .html(html)
                .build();
    
        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

}
