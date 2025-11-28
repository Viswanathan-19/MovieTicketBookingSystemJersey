package org.viswa.controller;

import java.time.LocalDate;
import java.util.*;

import jakarta.ws.rs.core.Response;
import org.viswa.model.*;
import  org.viswa.database.*;

public class MovieController {
    
    public Response displayMoviesByDate(String dateString){
        try{
            LocalDate date=LocalDate.parse(dateString);

            Set<Integer> movieIds=new HashSet<>();
            for(Show s: InMemoryDB.shows.values()){
                if(s.getShowDate().equals(date)){
                    movieIds.add(s.getMovieId());
                }
            }

            if (movieIds.isEmpty()) {
                Map<String,Object> error=new HashMap<>();
                error.put("status","fail");
                error.put("message","No movies available on "+date);
                List<String> validDates = Arrays.asList(
                        LocalDate.now().toString(),
                        LocalDate.now().plusDays(1).toString()
                );

                error.put("validDates", validDates);

                return  Response.status(Response.Status.NOT_FOUND)
                        .entity(error)
                        .build();

            }

            List<String> movieNames = new ArrayList<>();
            for (int movieId : movieIds) {
                Movie movie = InMemoryDB.movies.get(movieId);
                movieNames.add(movie.getmovieName());
            }


            Map<String, Object> success = new HashMap<>();
            success.put("status", "success");
            success.put("date", date.toString());
            success.put("movies", movieNames);

            return Response.ok(success).build();

        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Invalid date format. Use yyyy-MM-dd");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(err)
                    .build();
        }

    }
}
