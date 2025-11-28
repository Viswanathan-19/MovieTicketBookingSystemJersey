package org.viswa.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.viswa.controller.ShowController;
import org.viswa.controller.TheatreController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Path("/seats")
public class ShowResource {
    ShowController showController=new ShowController();
    TheatreController theatreController=new TheatreController();
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSeats(Map<String, Object> json) {

        String timeInput = (String) json.get("time");
        // seatNumbers comes as ArrayList, NOT String[]
        String movieName=(String)json.get("movieName");
        String theatreName=(String)json.get("theatreName");
         int movieId= theatreController.getMovieId(movieName);
         int theatreId= theatreController.getTheatreId(theatreName);
         String dateString=(String)json.get("date");
         int userId=(int)json.get("userId");
        List<String> seatList = (List<String>) json.get("seatNumbers");
        String[] seatNumbers = seatList.toArray(new String[0]);
        return showController.displaySeatsByShowTime(
                LocalTime.parse(timeInput),
                movieId,
                theatreId,
                LocalDate.parse(dateString),
                userId,
                seatNumbers
        );
    }

}
