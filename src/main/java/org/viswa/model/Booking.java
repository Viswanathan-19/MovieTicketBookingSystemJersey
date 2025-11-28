package org.viswa.model;


import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class Booking {
    private int userId;
    private int bookingId;
    private Show show;
    private int totalAmount;
    private List<String>seatNo;

    public static ConcurrentHashMap<Integer,Booking>bookings=new ConcurrentHashMap<>();

    public Booking(int bookingId, Show show,int userId,List<String> seatNo,int totalAmount) {
        this.bookingId = bookingId;
        this.show = show;
        this.userId=userId;
        this.seatNo=seatNo;
        this.totalAmount=totalAmount;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

   
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(List<String> seatNo) {
        this.seatNo = seatNo;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    
    
}
