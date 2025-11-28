package org.viswa.database;


import org.viswa.model.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDB {

    public static final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, Movie> movies = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, Theatre> theatres = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, Screen> screens = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<Integer, Show> shows = new ConcurrentHashMap<>();

    static {

        // ---- USERS ----
        users.put(1, new User(1, "viswa", "123"));
        users.put(2, new User(2, "sudhir", "456"));
        users.put(3, new User(3, "peter", "789"));

        // ---- MOVIES ----
        Movie movie1 = new Movie(1, "Leo");
        Movie movie2 = new Movie(2, "Vikram");
        Movie movie3 = new Movie(3, "F1");
        Movie movie4 = new Movie(4, "Petta");
        Movie movie5 = new Movie(5, "Bison");

        movies.put(movie1.getMovieId(), movie1);
        movies.put(movie2.getMovieId(), movie2);
        movies.put(movie3.getMovieId(), movie3);
        movies.put(movie4.getMovieId(), movie4);
        movies.put(movie5.getMovieId(), movie5);

        // ---- THEATRES ----
        Theatre t1 = new Theatre(1, "KG");
        Theatre t2 = new Theatre(2, "Inox");
        Theatre t3 = new Theatre(3, "PVR");
        Theatre t4 = new Theatre(4, "Miraj");
        Theatre t5 = new Theatre(5, "Broadway");

        theatres.put(t1.getTheatreId(), t1);
        theatres.put(t2.getTheatreId(), t2);
        theatres.put(t3.getTheatreId(), t3);
        theatres.put(t4.getTheatreId(), t4);
        theatres.put(t5.getTheatreId(), t5);

        // ---- SCREENS ----
        screens.put(1, new Screen(1, 1, 30));
        screens.put(2, new Screen(2, 2, 30));
        screens.put(3, new Screen(3, 3, 30));
        screens.put(4, new Screen(4, 4, 30));
        screens.put(5, new Screen(5, 5, 30));
        screens.put(6, new Screen(6, 5, 30));
        screens.put(7, new Screen(7, 1, 30));
        screens.put(8, new Screen(8, 1, 30));

        // ---- SHOWS ----
        shows.put(1, new Show(1, 1, 1, 1, LocalDate.now(), LocalTime.of(10, 30)));
        shows.put(2, new Show(2, 1, 1, 7, LocalDate.now(), LocalTime.of(14, 45)));
        shows.put(3, new Show(3, 2, 2, 2, LocalDate.now(), LocalTime.of(18, 00)));
        shows.put(4, new Show(4, 3, 3, 3, LocalDate.now().plusDays(1), LocalTime.of(20, 15)));
        shows.put(5, new Show(5, 4, 4, 4, LocalDate.now().plusDays(1), LocalTime.of(9, 15)));
        shows.put(6, new Show(6, 4, 4, 4, LocalDate.now(), LocalTime.of(13, 15)));
        shows.put(7, new Show(7, 5, 5, 5, LocalDate.now().plusDays(1), LocalTime.of(11, 15)));
        shows.put(8, new Show(8, 5, 5, 5, LocalDate.now().plusDays(1), LocalTime.of(9, 15)));
        shows.put(9, new Show(9, 3, 5, 3, LocalDate.now().plusDays(1), LocalTime.of(9, 15)));
        shows.put(10, new Show(10, 1, 1, 8, LocalDate.now(), LocalTime.of(19, 30)));
    }
}


