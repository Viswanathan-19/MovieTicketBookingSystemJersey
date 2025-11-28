package org.viswa.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.viswa.database.InMemoryDB;
import org.viswa.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/bookings")
public class BookingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookings(){
         Map<String,Object> bookingInfo=new HashMap<>();
        for(Booking b: Booking.bookings.values()){

            int bookingId=b.getBookingId();
            User u=InMemoryDB.users.get(b.getUserId());
            Show s=b.getShow();
            Movie m=InMemoryDB.movies.get(s.getMovieId());
            Theatre t=InMemoryDB.theatres.get(s.getTheatreId());
           bookingInfo.put("UserName: ",u.getUserName());
           bookingInfo.put(" BookingId: ",bookingId);
           bookingInfo.put(" MovieName: ",m.getmovieName());
            bookingInfo.put(" TheatreName: ",t.getTheatreName());
            bookingInfo.put(" ShowTime: ",s.getShowTime().toString());
            bookingInfo.put(" ShowDate: ",s.getShowDate().toString());
            bookingInfo.put(" BookedSeats: ",b.getSeatNo());
            bookingInfo.put(" TotalAmount: ",b.getTotalAmount());

        }
        return Response.ok().entity(bookingInfo).build();
    }
}
