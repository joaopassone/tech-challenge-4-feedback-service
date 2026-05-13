package br.com.fiap.tc4.services;

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
}
