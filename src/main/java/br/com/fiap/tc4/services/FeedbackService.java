package br.com.fiap.tc4.services;

import java.sql.Date;
import java.time.LocalDate;

import br.com.fiap.tc4.dtos.FeedbackRequestDTO;
import br.com.fiap.tc4.models.Feedback;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FeedbackService {

    EntityManager em;

    public FeedbackService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void enviarFeedback(FeedbackRequestDTO feedback) {
        Date dataAtual = Date.valueOf(LocalDate.now());
        Feedback novoFeedback = new Feedback(feedback.descricao(), feedback.nota(), dataAtual);
        
        em.persist(novoFeedback);
    }
}
