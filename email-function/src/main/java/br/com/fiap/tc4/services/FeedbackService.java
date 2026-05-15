package br.com.fiap.tc4.services;

import java.util.List;

import br.com.fiap.tc4.models.Feedback;
import br.com.fiap.tc4.repositories.FeedbackRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FeedbackService {

    FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback findById(Long id) {
        return repository.findById(id);
    }

    public List<Feedback> buscarFeedbacksUltimaSemana() {
        return repository.buscarFeedbacksUltimaSemana();
    }
}
