package br.com.fiap.tc4.controllers;

import br.com.fiap.tc4.dtos.FeedbackRequestDTO;
import br.com.fiap.tc4.services.FeedbackService;
import br.com.fiap.tc4.services.PubSubService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/avaliacao")
public class FeedbackController {

    FeedbackService service;
    PubSubService pubSubService;

    public FeedbackController(FeedbackService service, PubSubService pubSubService) {
        this.service = service;
        this.pubSubService = pubSubService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarPedido(FeedbackRequestDTO feedback) {
        int id = service.enviarFeedback(feedback);

        if (feedback.nota() <= 7) {
            pubSubService.publish("topico-feedback", String.valueOf(id));
        }

        return Response.status(Status.CREATED).build();
    }
}
