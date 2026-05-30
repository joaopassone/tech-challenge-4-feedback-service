package br.com.fiap.tc4.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.resend.Resend;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;

import br.com.fiap.tc4.models.Feedback;
import br.com.fiap.tc4.utils.DateFormatter;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmailService {

    @ConfigProperty(name = "resend.api.key")
    String apiKey;

    public void enviarEmailFeedback(Feedback feedback) {
        Resend resend = new Resend(apiKey);

        String urgencia = definirUrgencia(feedback.getNota());

        String dataFormatada = DateFormatter.formatarData(feedback.getData().toString());

        String html = """
                <p><strong>Caros Administradores,</strong></p>
                <p>Um novo feedback foi enviado e necessita de atenção:</p>
                <p>
                    <strong>Data:</strong> %s<br>
                    <strong>Urgência:</strong> %s<br>
                    <strong>Nota:</strong> %d
                    <strong>Feedback:</strong><br>
                %s
                <br>
                <p>Att.</p>
                <strong>EQUIPE DE ADMINSTRAÇÃO DE FEEDBACKS</strong>
                """.formatted(dataFormatada, urgencia, feedback.getNota(), feedback.getDescricao());

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

    public void enviarEmailRelatorio(List<Feedback> feedbacks) {
        Resend resend = new Resend(apiKey);

        List<String> urgencias = new ArrayList<>();
        StringBuilder feedbacksHtmlBuilder = new StringBuilder();
        
        final String[] dataAnterior = {""};

        feedbacks.forEach(feedback -> {
            String urgencia = definirUrgencia(feedback.getNota());
            urgencias.add(urgencia);

            String dataFormatada = DateFormatter.formatarData(feedback.getData().toString());

            if (!dataFormatada.equals(dataAnterior[0])) {
                feedbacksHtmlBuilder.append("<h2>%s</h2>".formatted(dataFormatada));
                dataAnterior[0] = dataFormatada;
            }

            String feedbackFormatado = """
                    <p>
                    <strong>Urgência:</strong> %s<br>
                    <strong>Nota:</strong> %d
                    <strong>Feedback:</strong><br>
                    %s
                    <br>
                    <p>----------</p>
                    """.formatted(urgencia, feedback.getNota(), feedback.getDescricao());
            
            feedbacksHtmlBuilder.append(feedbackFormatado);
        });

        String feedbacksHtml = feedbacksHtmlBuilder.toString();

        Map<String, Long> contagemUrgencias = urgencias.stream()
            .collect(Collectors.groupingBy(String::toUpperCase, Collectors.counting()));

        long qtdeFeedbacksSemUrgencia = contagemUrgencias.getOrDefault("SEM URGÊNCIA", 0L);
        long qtdeFeedbacksModerado = contagemUrgencias.getOrDefault("MODERADA", 0L);
        long qtdeFeedbacksUrgente = contagemUrgencias.getOrDefault("URGENTE", 0L);

        int totalFeedbacks = feedbacks.size();
        double mediaFeedbacksDiaria = totalFeedbacks / 7.0;

        String cabecalhoHtml = """
                <p><strong>Caros Administradores,</strong></p>
                <p>Este é o resumo semanal de feedbacks:</p>
                """;

        String resumoHtml = """
                <p>
                    <strong>Quantidade Total:</strong> %d<br>
                    <strong>Feedbacks Sem Urgência:</strong> %d<br>
                    <strong>Feedbacks Moderados:</strong> %d<br>
                    <strong>Feedbacks Urgentes:</strong> %d<br>
                    <strong>Média Diária:</strong> %.2f<br>
                </p>
                """.formatted(totalFeedbacks, qtdeFeedbacksSemUrgencia, qtdeFeedbacksModerado, qtdeFeedbacksUrgente, mediaFeedbacksDiaria);

        String assinaturaHtml = """
                <p>Att.</p>
                <strong>EQUIPE DE ADMINSTRAÇÃO DE FEEDBACKS</strong>
                """;

        String html = cabecalhoHtml + resumoHtml + feedbacksHtml + assinaturaHtml;

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("Plataforma de Feedbacks <onboarding@resend.dev>")
                .to("joao.passone@gmail.com")
                .subject("Relatório semanal de Feedbacks")
                .html(html)
                .build();
    
        try {
            CreateEmailResponse data = resend.emails().send(params);
            System.out.println(data.getId());
        } catch (ResendException e) {
            e.printStackTrace();
        }
    }

    public String definirUrgencia(int nota) {
        if (nota > 7) {
            return "SEM URGÊNCIA";
        } else if (nota > 5) {
            return "MODERADA";
        }

        return "URGENTE";
    }

}
