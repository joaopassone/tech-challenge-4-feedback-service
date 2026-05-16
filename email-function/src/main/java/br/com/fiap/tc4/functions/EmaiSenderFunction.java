package br.com.fiap.tc4.functions;

import java.util.List;

import br.com.fiap.tc4.dtos.PubsubEnvelope;
import br.com.fiap.tc4.models.Feedback;
import br.com.fiap.tc4.services.EmailService;
import br.com.fiap.tc4.services.FeedbackService;
import io.quarkus.funqy.Funq;

public class EmaiSenderFunction {

    FeedbackService feedbackService;
    EmailService emailService;


    public EmaiSenderFunction(FeedbackService service, EmailService emailService) {
        this.feedbackService = service;
        this.emailService = emailService;
    }

    @Funq
    public void enviarEmailFeedback(PubsubEnvelope envelope) {
        if (envelope == null || envelope.message == null || envelope.message.data == null) {
            System.err.println("AVISO: Recebido payload nulo ou inválido do Pub/Sub.");
            return;
        }

        String feedbackId = new String(java.util.Base64.getDecoder().decode(envelope.message.data));

        try {
            Feedback feedback = feedbackService.findById(Long.parseLong(feedbackId));
            emailService.enviarEmailFeedback(feedback);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email de feedback: " + e.getMessage());
        }
    }

    @Funq
    public void enviarEmailRelatorio() {
        try {
            List<Feedback> feedbacks = feedbackService.buscarFeedbacksUltimaSemana();
            emailService.enviarEmailRelatorio(feedbacks);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email do relatório: " + e.getMessage());
        }
    }
}
