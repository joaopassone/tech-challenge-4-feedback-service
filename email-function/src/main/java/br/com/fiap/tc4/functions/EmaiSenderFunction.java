package br.com.fiap.tc4.functions;

import br.com.fiap.tc4.models.Feedback;
import br.com.fiap.tc4.services.EmailService;
import br.com.fiap.tc4.services.FeedbackService;
import io.quarkus.funqy.Funq;
import io.quarkus.funqy.gcp.functions.event.PubsubMessage;

public class EmaiSenderFunction {

    FeedbackService feedbackService;
    EmailService emailService;


    public EmaiSenderFunction(FeedbackService service, EmailService emailService) {
        this.feedbackService = service;
        this.emailService = emailService;
    }

    @Funq
    public void enviarEmail(PubsubMessage message) {
        String feedbackId = new String(java.util.Base64.getDecoder().decode(message.data));

        try {
            Feedback feedback = feedbackService.findById(Long.parseLong(feedbackId));
            emailService.enviarEmail(feedback);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }
    }
}
