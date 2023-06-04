package com.skyskaner.skyskaner;

import java.util.LinkedList;

public class Flight {
    public int id;
    public Airport origin;
    public Airport destination;
    LinkedList<Airline> airlines;
    public int price;
    Flight(int id, Airport origin, Airport destination, int price, LinkedList<Airline> airlines){
        this.id=id;
        this.origin=origin;
        this.destination=destination;
        this.price=price;
        this.airlines=airlines;
    }

}
