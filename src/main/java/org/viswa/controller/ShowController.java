package org.viswa.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.ws.rs.core.Response;
import org.viswa.model.*;
import  org.viswa.database.*;
import org.viswa.util.*;

public class ShowController {
    Show show;
    Screen screen;
    private static final AtomicInteger bookingIdGenerator = new AtomicInteger(0);    // to maintain atomicity for booking id,when multiple threads book



    public LocalTime getShowTime(){
        Scanner sc=ScannerProvider.getScanner();
        LocalTime showTime;
         while(true){
               try{
              System.out.print("Enter show time (HH:mm): ");
                    String timeInput = sc.nextLine();

                    showTime=LocalTime.parse(timeInput);
                    break;
                }
                catch(Exception e){
                    System.out.println("Invalid time format! Please enter again in HH:mm format.");
                }
    }
    return showTime;
}

    public int getShowId(LocalTime time,int movieId,int theatreId,LocalDate showDate){
        for(Show s:InMemoryDB.shows.values()){
            if(s.getMovieId() == movieId
            && s.getTheatreId() == theatreId
            && s.getShowDate().equals(showDate)
            &&s.getShowTime().equals(time)){
             return s.getShowId();
            }
        }
        return 0;


    }

        private boolean isValidSeatFormat(String seat) {    //seat validation only uppercase and correct numerals are allowed
        if (seat == null || seat.length() < 2) return false;

        char row = seat.charAt(0);

        // allowed rows
        if (row != 'A' && row != 'B' && row != 'C') return false;

        // extract seat number after the row letter
        String numStr = seat.substring(1);

        // ensure numeric
        if (!numStr.matches("\\d+")) return false;

        int num = Integer.parseInt(numStr);

        return num >= 1 && num <= 10;
    }

    public Response displaySeatsByShowTime(LocalTime showTime, int movieId, int theatreId, LocalDate showDate, int userId, String[] seatNumbers){


        //   System.out.println(Thread.currentThread().getName()+" enters");

                synchronized(this){            //One synchronized block to allow only one thread to enter and leave only after booking is completed or cancelled.

                    Scanner sc=ScannerProvider.getScanner();
                     int showId=getShowId(showTime, movieId, theatreId, showDate);  //get showId
                    if (showId == 0) {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(Map.of("error", "Show not found for given details"))
                                .build();
                    }
                     show=InMemoryDB.shows.get(showId);       //get the correct show object using showId
                    if (show == null) {
                        return Response.status(Response.Status.NOT_FOUND)
                                .entity(Map.of("error", "Show not available"))
                                .build();
                    }

                      Show currentShow = show;
                      Screen screen=InMemoryDB.screens.get(show.getScreenId());   //get the screen object for that show

                     if (screen == null) {
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(Map.of("error", "Screen not found for show"))
                                .build();
                    }

                    if(screen.getTotalSeats()<=0){    //check if the current screen with selected showtime has no shows
                        if(screen.getTotalSeats()<=0) return Response.status(Response.Status.GONE).
                                entity("No Seats available for the selected show").build();
                        System.out.println(Thread.currentThread().getName() + " Available Seats for "+InMemoryDB.movies.get(movieId).getmovieName()+" :");
                        show.displayAllSeats();                              //display that no seats available for the show

                        System.out.println(Thread.currentThread().getName()+" No Seats Available for Selected Show ,Please Select a Different Show\n");

                        TheatreController theatreController=new TheatreController();
                         theatreController.displayTheatresAlternateShows(show);  //display the alternate show timing for the movie
//                         showTime=getShowTime();                                 //get show time to confirm
                          showTime=LocalTime.parse("19:30");
                        showId=getShowId(showTime, movieId, theatreId, showDate);   //get the selected show id
                        currentShow=InMemoryDB.shows.get(showId);                         //get the appropriate show object

                         System.out.println(Thread.currentThread().getName() + " Available Seats for "+InMemoryDB.movies.get(movieId).getmovieName()+" :");
                         currentShow.displayAllSeats();                         //display the available seats on the selected show

                        }
                        else{

                    System.out.println(Thread.currentThread().getName() + " Available Seats for "+InMemoryDB.movies.get(movieId).getmovieName()+" :");
                    show.displayAllSeats();                       //if the seats are available,display the available seats
                        }


                     boolean flag=true;

//                                    do {
//                    System.out.println("Enter seats (space or comma separated): ");
//                    String seatInput = sc.nextLine();
//
//                    seatNumbers = seatInput.split("[, ]+");
//
//                    flag = true;
//
//                    for (String seat : seatNumbers) {
//                        if (!isValidSeatFormat(seat)) {  // validate the entered seats
//                            System.out.println("\nInvalid Seat: " + seat);
//                            System.out.println("Valid format: A1-A10, B1-B10, C1-C10\n");
//                            flag = false;
//                            break;
//                        }
//                    }
//
//                } while (!flag);

                int status=bookSeats(seatNumbers,currentShow,userId);  //check if the seats are already booked ,if(status->0) then retry again
                //if status->0 ,retry again ,else status -> 1,dont retry ,bcoz(user may cancel booking,or payment failed bcoz of expired time)

                if (status == 0) {
                    return Response.status(Response.Status.CONFLICT)
                            .entity(Map.of("error", "One or more seats are already booked"))
                            .build();

                }
                    return Response.ok().entity("Seats are booked").build();
                        }
        }






   public int bookSeats(String[] seatNumbers, Show show,int userId) {

    List<String> locked = new ArrayList<>();   //store the locked seats for booking

    // lock each seat
    for (String seat : seatNumbers) {
        if (show.lockSeats(seat)) {
            locked.add(seat);
            System.out.println(Thread.currentThread().getName()+" "+seat + " locked!");
        } else {
               for(String seat1:seatNumbers){   //if one of the seats is not locked ,unlock all the seats for rebooking
                   show.unlockSeat(seat1);
               }
            System.out.println(Thread.currentThread().getName()+" "+seat + " cannot be locked! or already booked! Try again\n");
            return 0 ;       //retry booking again with different seats
        }
    }

    if (locked.isEmpty()) return 0;

//     boolean flag= confirm(locked, show);   //confirm with the user within 5 seconds
//     if(!flag)return 1;       //if booking cancelled i.e return 1 -> dont direct to rebooking,bcoz user cancelled the booking process

     int bookingCount=0;
     int totalprice=0;
    // Finalize booking
    for (String seat : locked) {


        if(show.isLockExpired(seat)){     //check if lock time expired for seats
            System.out.println(Thread.currentThread().getName()+" Time expired try again");
            for (String seat1: locked) {   //if expired ,then unlock all the seats
            show.unlockSeat(seat1);
        }
             return 1;      //time expired so return 1-> user need to start from again
            }
        if (show.finalizeBooking(seat) ) {
            // show.isAvailableSeats(seat);
            if(seat.startsWith("A")){
                totalprice+=SeatCategory.BRONZE.getPrice();
            }
            else if(seat.startsWith("B")){
                totalprice+=SeatCategory.SILVER.getPrice();
            }
            else{
                totalprice+=SeatCategory.GOLD.getPrice();
            }
             bookingCount++;
            System.out.println(seat + " BOOKED!");
        } else {
            System.out.println(seat + " lock expired!");
        }

    }

          Screen screen=InMemoryDB.screens.get(show.getScreenId());
           screen.setTotalSeats(screen.getTotalSeats()-bookingCount);   //update total seats in the screen
           System.out.println("Seats after booking : "+screen.getTotalSeats());
           show.displayAllSeats();
            Booking b=new Booking(bookingIdGenerator.incrementAndGet(), show,userId,locked,totalprice); //store booking information only once
            Booking.bookings.putIfAbsent(b.getBookingId(),b);


            return 1;
}


   // confirm with the user
  public boolean confirm(List<String> locked,Show show){
    Scanner sc = ScannerProvider.getScanner();
    System.out.print("\nConfirm booking? yes/no: ");
    String answer = sc.nextLine();

    if (!answer.equalsIgnoreCase("yes")) {
        System.out.println("Booking cancelled.");    //if booking cancelled,unlock all the locked seats.
        for (String seat : locked) {
            show.unlockSeat(seat);
        }

        return false;
    }
    return true;

}


}

