package org.viswa.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.viswa.controller.MovieController;

import java.util.*;

@Path("/movies")
public class MovieResource {

    MovieController movieController=new MovieController();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovies(Map<String, String> json) {
        String dateString = json.get("date");
        return movieController.displayMoviesByDate(dateString);

    }

}
