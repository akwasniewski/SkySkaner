package com.skyskaner.skyskaner;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Showtickets {

    public Label errorLabel;
    @FXML
    ListView<TicketResult> resultlist=new ListView<>();

    public void goBack(ActionEvent actionEvent) throws IOException {
        HelloApplication.showSearch();
    }

    public void showTickets(ActionEvent actionEvent) {
        searchTickets();

        resultlist.setCellFactory(param -> new ListCell<TicketResult>() {
            VBox vbox = new VBox();
            {
                vbox.setAlignment(Pos.CENTER);
                vbox.setMinWidth(524);
                vbox.setStyle("-fx-font: 20 arial;");
            }

            protected void updateItem(TicketResult item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    vbox.getChildren().clear(); // Clear previous content

                    for (int i = 0; i < item.myticket.size(); i++) {
                        VBox labelBox = new VBox();
                        Label idTicketLabel = new Label("Ticket ID: " + item.myticket.get(i).id_ticket);
                        Label isChildLabel = new Label("Child: " + item.myticket.get(i).ischild);
                        Label luggageTypeLabel = new Label("Luggage Type: " + item.myticket.get(i).luggagetype);
                        Label dateLabel = new Label("Date: " + item.myticket.get(i).date + " Departure: " + item.myticket.get(i).departure + " Arrival: " + item.myticket.get(0).arrival);
                        Label routeLabel = new Label(item.myticket.get(i).from + " -----> " + item.myticket.get(i).to);
                        Label separator = new Label("------------------------------------------");
                        labelBox.getChildren().addAll(idTicketLabel, isChildLabel, luggageTypeLabel, dateLabel, routeLabel,separator);
                        vbox.getChildren().add(labelBox);
                    }

                    setGraphic(vbox);
                }
            }
        });
    }

    public void searchTickets(){
        TicketResult result = new TicketResult();
        String query = "select u.id_ticket, is_child, luggage_type, the_date, departure_time, arrival_time, id_airport_from, id_airport_to  from tickets t join user_tickets u on u.id_ticket = t.id_ticket \n" +
                "join flights f on f.id_flight = t.id_flight \n" +
                "join connections c on c.id_connection = f.id_connection where id_user='"+HelloApplication.userId+"'";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int ticketId = rs.getInt("id_ticket");
                boolean isChild = rs.getBoolean("is_Child");
                int luggageType = rs.getInt("luggage_type");
                String date = rs.getString("the_date");
                String departure = rs.getString("departure_time");
                String arrival = rs.getString("arrival_time");
                String id_airport_from = rs.getString("id_airport_from");
                String id_airport_to = rs.getString("id_airport_to");

                result.addTicket(new Ticket(ticketId,isChild,luggageType,date,departure,arrival,id_airport_from, id_airport_to));
            }
            if(result.isEmpty()){
                ErrorMessage("You don't have any tickets");
                resultlist.getItems().clear();
                return;
            }
            for(Ticket t : result.myticket){
                query = "select cities.the_name from cities join airports on cities.id_city = airports.id_city where id_airport = "+t.from+";";
                rs = stmt.executeQuery(query);
                while(rs.next()){
                String city_from = rs.getString("the_name");

                    t.from=city_from;
                }
                query = "select cities.the_name from cities join airports on cities.id_city = airports.id_city where id_airport = "+t.to+";";
                rs = stmt.executeQuery(query);
                while(rs.next()) {
                    String city_to = rs.getString("the_name");

                    t.to = city_to;
                }
            };
            resultlist.getItems().clear();
            resultlist.getItems().addAll(result);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void ErrorMessage(String err){

        errorLabel.setText(err);
    }
}
