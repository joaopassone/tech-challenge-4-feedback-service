package br.com.fiap.tc4.repositories;

import br.com.fiap.tc4.models.Feedback;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FeedbackRepository implements PanacheRepository<Feedback> {
    
}
