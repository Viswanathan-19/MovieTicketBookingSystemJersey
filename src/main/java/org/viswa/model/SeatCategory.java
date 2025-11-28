package org.viswa.model;


public enum SeatCategory {
    BRONZE(60),
    SILVER(150),
    GOLD(220);
   

    private final int price;    //used to store the price for the seat types

    SeatCategory(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
