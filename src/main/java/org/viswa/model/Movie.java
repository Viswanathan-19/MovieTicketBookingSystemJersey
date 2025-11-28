package org.viswa.model;

public class Movie {
    private int movieId;
    private String movieName;

    public Movie(int movieId, String movieName) {
        this.movieId = movieId;
        this.movieName = movieName;
    }

    public int getMovieId() { return movieId; }
    public String getmovieName() { return movieName; }
}
