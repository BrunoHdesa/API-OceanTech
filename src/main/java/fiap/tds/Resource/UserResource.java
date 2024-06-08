package fiap.tds.Resource;

import fiap.tds.Entities.User;
import fiap.tds.Repository.UserRepository;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("cadastro")
public class UserResource {
    UserRepository userRepo = new UserRepository();

    @GET
    public List<User> getFeedbacks() {
        return userRepo.getAllUsers();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") int id) {
        User user = userRepo.getUser(id);
        if (user == null) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(user).build();
    }

    @POST
    public Response createUser(User user) {
        if (user == null) {
            return Response.status(400).entity("O cadastro não pode ser nulo").build();
        }
        userRepo.createUser(user);
        return Response.status(201).entity(user).build();
    }

    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        user.setId(id);
        userRepo.updateUser(user);
        return Response.status(200).entity(user).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int id) {
        userRepo.deleteUser(id);
        return Response.status(204).build();
    }

    @POST
    @Path("cadastro/login")
    public Response login(User user) {
        if (user == null || user.getEmail() == null || user.getSenha() == null) {
            return Response.status(400).entity("Usuario e senha são obrigatórios").build();
        }

        User authenticatedUser = userRepo.authenticateUser(user.getEmail(), user.getSenha());

        if (authenticatedUser == null) {
            return Response.status(401).entity("Credenciais inválidas").build();
        }

        // Aqui você pode gerar um token de autenticação e retorná-lo
        // Por simplicidade, vamos retornar apenas o ID do usuário
        return Response.status(200).entity(authenticatedUser.getId()).build();
    }
}
