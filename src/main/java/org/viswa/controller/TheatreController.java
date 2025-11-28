package org.viswa.controller;

import java.time.*;
import java.util.*;

import jakarta.ws.rs.core.Response;
import org.viswa.database.InMemoryDB;
import org.viswa.model.*;

public class TheatreController {
    int movieId;
    int theatreId;
    LocalDate showDate;
 
    public Integer getMovieId(String movieName){
        Integer num=0;
        for(Movie movie: InMemoryDB.movies.values()){
            if(movie.getmovieName().equalsIgnoreCase(movieName)){
                num=movie.getMovieId();
                break;
        }
    }
    return num;
}

   public int getTheatreId(String theatreName){
    for(Theatre t:InMemoryDB.theatres.values()){
        if(t.getTheatreName().equalsIgnoreCase(theatreName)){
            return t.getTheatreId();
        }
    }
    return 0;
   }

   //To display the theatreNames
    public Response displayTheatres(String movieName, LocalDate date){
          showDate =date;
           Set<Integer> theatreIds=new HashSet<>();
             movieId=getMovieId(movieName);
           
        for(Show s:InMemoryDB.shows.values()){
            if(s.getMovieId() == movieId && s.getShowDate().equals(date)){
               theatreIds.add(s.getTheatreId());
        }
    }

        Map<String,Object> t=new HashMap<>();
        List<String> theatres=new ArrayList<>();
        for(Integer id:theatreIds){
            Theatre th=InMemoryDB.theatres.get(id);
            theatres.add(th.getTheatreName());
            System.out.println(th.getTheatreName());
        }

        t.put("Movie is Available in the theatres ",theatres);

        return Response.ok(t).build();


}
    //to display shows in the theatres
    public Response displayTheatresShows(String theatreName,LocalDate showDate,int movieId){
        theatreId=getTheatreId(theatreName);
        List<String>logs=new ArrayList<>();
       for(Show s:InMemoryDB.shows.values()){
        if(theatreId == s.getTheatreId() && s.getShowDate().equals(showDate) && s.getMovieId() == movieId){
            Theatre t=InMemoryDB.theatres.get(s.getTheatreId());
            logs.add("TheatreName: "+t.getTheatreName()+" ScreenNo: "+s.getScreenId()+" ShowTime: "+s.getShowTime()+" ShowDate: "+s.getShowDate() );
            System.out.println("TheatreName: "+t.getTheatreName()+" ScreenNo: "+s.getScreenId()+" ShowTime: "+s.getShowTime()+" ShowDate: "+s.getShowDate() );
        }
       }
        Map<String, Object> map = new HashMap<>();
        map.put("Show Timings", logs);

        return Response.ok(map).build();

    }
       public int returnMovieId(){
        return movieId;
       }
      
       public void displayTheatresAlternateShows(Show show){    //display the alternate shows ,if one of the shows is full
        
        for(Show s:InMemoryDB.shows.values()){
            if(s.getMovieId() == show.getMovieId() 
            && s.getTheatreId() == show.getTheatreId()
            && s.getShowTime()!=show.getShowTime()
            &&s.getShowDate() == s.getShowDate()){
                Theatre t=InMemoryDB.theatres.get(s.getTheatreId());
                 System.out.println("TheatreName: "+t.getTheatreName()+" ScreenNo: "+s.getScreenId()+" ShowTime: "+s.getShowTime()+" ShowDate: "+s.getShowDate() );
                 
                 
            }
            
        }

       }
    
       
       
        
}

