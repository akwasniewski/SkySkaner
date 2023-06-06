package com.skyskaner.skyskaner;

import java.util.LinkedList;

public class TicketResult{

    LinkedList<Ticket> myticket=new LinkedList<>();
    public void addTicket(Ticket t){
        myticket.add(t);
    }

    public boolean isEmpty() {
        if(myticket.isEmpty())return true;
        return false;
    }
}
