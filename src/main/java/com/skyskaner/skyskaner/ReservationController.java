package com.skyskaner.skyskaner;

import javafx.event.ActionEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationController {
SearchResult result;
boolean ischild;
int luggagetype;
int howmany;

void setIschild(ActionEvent a){

}

void setLuggagetype(ActionEvent a){

}
void setHowmany(ActionEvent a){

}

void makeReservation(ActionEvent a) {
    for (Flight f : result.flights) {
        int id = f.id;
        String query = "DO $$ DECLARE i INTEGER;BEGIN FOR i IN 1.." + howmany + " LOOP EXECUTE 'insert into user_tickets SELECT id_ticket, " + HelloApplication.userId + "," + ischild + "," + luggagetype + " from tickets t join flights f on t.id_flight = f.id_flight where f.id_flight=" + id + " order by t.price desc limit 1';END LOOP;END $$;";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {
            stmt.executeQuery(query);
        } catch (SQLException e) {

        }
        }
    }

}
