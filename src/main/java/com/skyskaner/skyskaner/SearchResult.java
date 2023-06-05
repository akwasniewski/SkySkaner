package com.skyskaner.skyskaner;

import java.util.LinkedList;

public class SearchResult {
    int price;
    LinkedList<Flight> flights;
    SearchResult(LinkedList<Flight> flights, int price){
        this.price=price;
        this.flights=flights;
    }
    SearchResult(Flight flight, int price){
        this.price=price;
        this.flights = new LinkedList<Flight>();
        this.flights.add(flight);
    }
}
