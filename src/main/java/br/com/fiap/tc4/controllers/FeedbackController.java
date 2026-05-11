package br.com.fiap.tc4.controllers;

import br.com.fiap.tc4.dtos.FeedbackRequestDTO;
import br.com.fiap.tc4.services.FeedbackService;
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

    public FeedbackController(FeedbackService service) {
        this.service = service;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criarPedido(FeedbackRequestDTO feedback) {
        service.enviarFeedback(feedback);

        return Response.status(Status.CREATED).build();
    }
}
