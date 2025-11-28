package org.viswa.model;



public class Screen {

    private int screenId;
    private int theatreId;
    private volatile int totalSeats;     


    public Screen(int screenId, int theatreId, int totalSeats) {
        this.screenId = screenId;
        this.theatreId = theatreId;
        this.totalSeats = totalSeats;
        
    }
    


    
    
    public int getScreenId() { return screenId; }
    public int getTheatreId() { return theatreId; }
    public int getTotalSeats() {  return totalSeats; }
    public void setTotalSeats(int totalSeats) {  this.totalSeats = totalSeats; }
    
}
