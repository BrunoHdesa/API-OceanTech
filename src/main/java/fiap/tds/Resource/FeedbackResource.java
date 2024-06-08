package fiap.tds.Resource;

import fiap.tds.Entities.Feedback;
import fiap.tds.Repository.FeedbackRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;

import java.io.IOException;
import java.util.List;

@Path("feedback")
public class FeedbackResource implements ContainerResponseFilter {
    FeedbackRepository feedbackRepo = new FeedbackRepository();

    @GET
    public List<Feedback> getFeedbacks() {
        return feedbackRepo.getAllFeedbacks();
    }

    @GET
    @Path("{id}")
    public Response getFeedback(@PathParam("id") int id) {
        Feedback feedback = feedbackRepo.getFeedback(id);
        if (feedback == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(feedback).build();
    }

    @POST
    public Response createFeedback(Feedback feedback) {
        if (feedback == null) {
            return Response.status(400).entity("O feedback não pode ser nulo").build();
        }
        feedbackRepo.createFeedback(feedback);
        return Response.status(201).entity(feedback).build();
    }

    @PUT
    @Path("{id}")
    public Response updateFeedback(@PathParam("id") int id, Feedback feedback) {
        feedback.setId(id);
        feedbackRepo.updateFeedback(feedback);
        return Response.status(200).entity(feedback).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteFeedback(@PathParam("id") int id) {
        feedbackRepo.deleteFeedback(id);
        return Response.status(204).build();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        headers.add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");

        if (requestContext.getMethod().equals("OPTIONS")) {
            responseContext.setStatus(200);
        }
    }
}
