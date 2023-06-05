package com.skyskaner.skyskaner;

import java.sql.Time;
import java.util.LinkedList;

public class Flight {
    public int id;
    public Airport origin;
    public Airport destination;
    LinkedList<Airline> airlines;
    public int price;
    public Time departureTime;
    public Time arrivalTime;
    Flight(int id, Airport origin, Airport destination, LinkedList<Airline> airlines, Time departureTime, Time arrivalTime){
        this.id=id;
        this.origin=origin;
        this.destination=destination;
        this.airlines=airlines;
        this.price=0;
        this.departureTime=departureTime;
        this.arrivalTime=arrivalTime;
    }
}
