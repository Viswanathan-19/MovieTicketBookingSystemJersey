package org.viswa.model;

public class Theatre {
    private final int theatreId;
    private final String theatreName;

    public Theatre(int theatreId, String theatreName) {
        this.theatreId = theatreId;
        this.theatreName = theatreName;
    }

    public int getTheatreId() { return theatreId; }
    public String getTheatreName() { return theatreName; }
}
