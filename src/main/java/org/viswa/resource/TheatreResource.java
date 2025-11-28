package org.viswa.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.viswa.controller.*;

import java.time.LocalDate;
import java.util.*;

@Path("/theatre")
public class TheatreResource {

    TheatreController theatreController=new TheatreController();

     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.APPLICATION_JSON)
     public Response getTheatres(Map<String,String> json){
         String movieName=json.get("movieName");
         String dateString=json.get("date");
         if (dateString == null) {
             return Response.status(400)
                     .entity("Date not selected yet")
                     .build();
         }
         System.out.println(dateString+" date "+movieName);
         return theatreController.displayTheatres(movieName, LocalDate.parse(dateString));
     }

    @POST
    @Path("/shows")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getShows(Map<String,String> json){
         String theatreName=json.get("theatreName");
         String dateString=json.get("date");
         String movieName=json.get("movieName");
         int movieId= theatreController.getMovieId(movieName);
        return theatreController.displayTheatresShows(theatreName,LocalDate.parse(dateString),movieId);
    }
}
