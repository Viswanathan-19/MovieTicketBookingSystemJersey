package org.viswa.resource;

import org.viswa.controller.UserController;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.*;

@Path("/users")
public class UserResource {

    UserController controller = new UserController();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> json) {
        System.out.println("JSON received: " + json);

        String username = json.get("username");
        String password = json.get("password");

        int userId = controller.validateUser(username, password);

        if (userId != 0) {

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("userId", userId);
            return Response.ok(response).build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", "fail");
        response.put("message", "Invalid credentials");

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(response)
                .build();
    }



}
