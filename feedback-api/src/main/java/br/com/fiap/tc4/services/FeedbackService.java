package br.com.fiap.tc4.services;

import java.sql.Date;
import java.time.LocalDate;

import br.com.fiap.tc4.dtos.FeedbackRequestDTO;
import br.com.fiap.tc4.models.Feedback;
import br.com.fiap.tc4.repositories.FeedbackRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FeedbackService {

    FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public int enviarFeedback(FeedbackRequestDTO feedback) {
        Date dataAtual = Date.valueOf(LocalDate.now());
        Feedback novoFeedback = new Feedback(feedback.descricao(), feedback.nota(), dataAtual);
        
        repository.persistAndFlush(novoFeedback);

        return novoFeedback.getId();
    }
}
