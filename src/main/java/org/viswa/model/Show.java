package org.viswa.model;

import java.time.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class Show {

    private int showId;
    private int movieId;
    private int theatreId;
    private int screenId;
    private LocalDate showDate;
    private LocalTime showTime;

    private List<String> allSeats = new ArrayList<>();   //store by default 30 seats(A1-A10,B1-B10,C1-C10)
    private Set<String> bookedSeats = new HashSet<>();   //store the booked seats


    private ConcurrentHashMap<String,Long> lockedSeats=new ConcurrentHashMap<>(); 
    private final long LOCK_DURATION_MS=5000;          //session time is for 5 seconds i.e(payment time)

    public Show(int showId, int movieId, int theatreId, int screenId, LocalDate showDate, LocalTime showTime) {
        this.showId = showId;
        this.movieId = movieId;
        this.theatreId = theatreId;
        this.screenId = screenId;
        this.showDate = showDate;
        this.showTime = showTime;
         generateSeats();
    }

    private void generateSeats() {             //generate 30 seats for each show
        char row = 'A';
        for (int r = 0; r < 3; r++) {
            for (int c = 1; c <= 10; c++) {
                allSeats.add("" + row + c);
            }
            row++;
        }
    }

    public synchronized boolean lockSeats(String seat){
        long now=System.currentTimeMillis();    

        if(bookedSeats.contains(seat)){  //to check already booked seats
            return false;
        }

        if(lockedSeats.containsKey(seat)){   //to check already locked seats
            return false;
        }
        lockedSeats.put(seat, now);  //lock the seat with time
        
        return true;

    }

    public boolean isLockExpired(String seat){     //check if the lock is expired or not
         Long lockTime=lockedSeats.get(seat);
         if(lockTime == null) return true;

         long now=System.currentTimeMillis();
         return (now - lockTime)>LOCK_DURATION_MS;    
    }

    public synchronized void unlockSeat(String seat) {    
      lockedSeats.remove(seat);
    }




   

    public  void displayAllSeats() {
        int count = 1;
        System.out.println("\n       \u001B[90m █████  SCREEN \u001B[90m  █████ \n");
        for (String seat : allSeats) {
            String color;
            String icon = "■";
            if (bookedSeats.contains(seat) ){
                color = "\u001B[31m";
                System.out.print(color+icon+ seat+" \u001B[0m");
            }
            else{
                 color = "\u001B[32m";
                System.out.print(color+icon+ seat+" \u001B[0m");            }

            if (count % 10 == 0) System.out.println();
            count++;
           
        }
        
        System.out.println();
    }
   
    

    public synchronized boolean finalizeBooking(String seat) {
    if (!lockedSeats.containsKey(seat)) return false;  //check already locked
    if (isLockExpired(seat)) {       //check isexpired
        unlockSeat(seat);    //if expired release the lock
        return false;
    }

    bookedSeats.add(seat);
    lockedSeats.remove(seat);

    return true;
}



    public int getShowId() {   return showId;  }


    public void setShowId(int showId) { this.showId = showId; }


    public int getMovieId() { return movieId;}


    public void setMovieId(int movieId) {  this.movieId = movieId;  }


    public int getTheatreId() {    return theatreId;}


    public void setTheatreId(int theatreId) {this.theatreId = theatreId; }


    public int getScreenId() { return screenId;  }


    public void setScreenId(int screenId) {this.screenId = screenId;}


    public LocalDate getShowDate() {   return showDate; }


    public void setShowDate(LocalDate showDate) {this.showDate = showDate; }


    public LocalTime getShowTime() { return showTime;}


    public void setShowTime(LocalTime showTime) {  this.showTime = showTime;}


   

    
    

    
}
