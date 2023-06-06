package com.skyskaner.skyskaner;

public class Ticket{
    int id_ticket;
   boolean ischild;
    int luggagetype;
    String date;
    String departure;
    String arrival;
    String from;
    String to;
    Ticket(int a,boolean b,int c,String d, String e, String f, String g, String h ){
        id_ticket=a;
        ischild = b;
        luggagetype = c;
        date = d;
        departure = e;
        arrival =f;
        from = g;
        to = h;

    }

}
