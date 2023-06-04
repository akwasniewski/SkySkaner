package com.skyskaner.skyskaner;

public class Flight {
    public int id;
    public Airport origin;
    public Airport destination;
    public int price;
    Flight(int id, Airport origin, Airport destination, int price){
        this.id=id;
        this.origin=origin;
        this.destination=destination;
        this.price=price;
    }

}
