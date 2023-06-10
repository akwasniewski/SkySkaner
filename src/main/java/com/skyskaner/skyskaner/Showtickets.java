package com.skyskaner.skyskaner;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Showtickets implements Initializable {
    @FXML
    TextField deleteField;
    public Label errorLabel;
    public Label errorLabel2;
    @FXML
    ListView<Ticket> resultlist=new ListView<>();

    public void goBack(ActionEvent actionEvent) throws IOException {
        HelloApplication.showSearch();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTickets();
        resultlist.setCellFactory(param -> new ListCell<Ticket>() {
            VBox vbox = new VBox();
            {
                vbox.setAlignment(Pos.CENTER);
                vbox.setMinWidth(450);
                vbox.setStyle("-fx-font: 20 arial;");
            }

            protected void updateItem(Ticket item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                        vbox.getChildren().clear(); // Clear previous content
                        VBox labelBox = new VBox();
                        Label idTicketLabel = new Label("Ticket ID: " + item.id);
                        Label isChildLabel = new Label("Child: " + item.ischild);
                        Label luggageTypeLabel = new Label("Luggage Type: " + item.luggagetype);
                        Label dateLabel = new Label("Date: " + item.date + " Departure: " + item.departure + " Arrival: " + item.arrival);
                        Label routeLabel = new Label(item.from + " -----> " + item.to);
                        Button cancelButton = new Button();
                        cancelButton.setText("Cancel Reservation");
                        cancelButton.setStyle("-fx-background-color: blue;-fx-text-fill: white");
                        cancelButton.setMaxHeight(15);
                        labelBox.getChildren().addAll(idTicketLabel, isChildLabel, luggageTypeLabel, dateLabel, routeLabel,cancelButton);
                        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Cancel(item.id);
                            }
                        });
                        vbox.getChildren().add(labelBox);
                    }
                    setGraphic(vbox);
                }
        });
    }

    public void searchTickets(){
        LinkedList<Ticket> result = new LinkedList<>();
        String query = "select u.id, is_child, luggage_type, the_date, departure_time, arrival_time, id_airport_from, id_airport_to  from tickets t join user_tickets u on u.id_ticket = t.id_ticket \n" +
                "join flights f on f.id_flight = t.id_flight \n" +
                "join connections c on c.id_connection = f.id_connection where id_user='"+HelloApplication.userId+"'";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int ticketId = rs.getInt("id");
                boolean isChild = rs.getBoolean("is_Child");
                int luggageType = rs.getInt("luggage_type");
                String date = rs.getString("the_date");
                String departure = rs.getString("departure_time");
                String arrival = rs.getString("arrival_time");
                String id_airport_from = rs.getString("id_airport_from");
                String id_airport_to = rs.getString("id_airport_to");

                result.add(new Ticket(ticketId,isChild,luggageType,date,departure,arrival,id_airport_from, id_airport_to));
            }
            if(result.isEmpty()){
                ErrorMessage("You don't have any tickets");
                resultlist.getItems().clear();
                return;
            }
            for(Ticket t : result){
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
    public void ErrorMessage2(String err){
        deleteField.setText("");
        errorLabel.setText(err);
    }
    public void Cancel(int id) {
        String query="delete from user_tickets where user_tickets.id="+id+";";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {
            stmt.executeQuery(query);
        } catch (SQLException e) {

        }
        searchTickets();
    }

}
