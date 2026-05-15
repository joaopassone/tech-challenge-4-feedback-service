package br.com.fiap.tc4.repositories;

import java.util.List;

import br.com.fiap.tc4.models.Feedback;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class FeedbackRepository implements PanacheRepository<Feedback> {

    EntityManager em;

    public FeedbackRepository(EntityManager em) {
        this.em = em;
    }

    @SuppressWarnings("unchecked")
    public List<Feedback> buscarFeedbacksUltimaSemana() {

        return em.createNativeQuery("""
                SELECT *
                FROM tc4_db.feedback f
                WHERE f.data > CURDATE() - INTERVAL 1 WEEK
                ORDER BY data
                """, Feedback.class)
                .getResultList();
    }
}
