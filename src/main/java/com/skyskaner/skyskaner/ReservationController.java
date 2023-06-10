package com.skyskaner.skyskaner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
static SearchResult result;
boolean ischild;
int luggagetype;
int howmany;
@FXML
Label flightInfo;
@FXML
Spinner<Integer> howManyChooser;
@FXML
Spinner<Integer> luggageChooser;
@FXML
CheckBox isChild;


void setIschild(ActionEvent a){

}

void setLuggagetype(ActionEvent a){

}
void setHowmany(ActionEvent a){

}

void makeReservation() {
    for (Flight f : result.flights) {

        int id = f.id;
        String query = "DO $$ DECLARE i INTEGER;BEGIN FOR i IN 1.." +howmany+ " LOOP EXECUTE 'insert into user_tickets SELECT id_ticket, " + HelloApplication.userId + "," + ischild + "," + luggagetype + " from tickets t join flights f on t.id_flight = f.id_flight where f.id_flight=" + id + " order by t.price desc limit 1';END LOOP;END $$;";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {
            stmt.executeQuery(query);
        } catch (SQLException e) {
          //  throw new RuntimeException(e);
        }
        }
    }
    public void goBack() throws IOException {
        HelloApplication.showSearch();
    }
    public void reserve() throws IOException {
        ischild=isChild.isSelected();
        luggagetype=luggageChooser.getValue();
        howmany=howManyChooser.getValue();
        makeReservation();
        goBack();
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        valueFactory.setValue(1);
        howManyChooser.setValueFactory(valueFactory);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,10);
        valueFactory2.setValue(1);
        luggageChooser.setValueFactory(valueFactory2);
        VBox vbox = new VBox();
        HBox mainBox = new HBox();
        Label stopInfo = new Label("(empty)");
        Label price = new Label("(empty)");
        VBox conInfo = new VBox();
        Pane up = new Pane();
        Pane left = new Pane();
        Pane right = new Pane();
        Label airline = new Label();
        {
            mainBox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(stopInfo, price, mainBox, airline);
            vbox.setMinWidth(450);
            vbox.setMaxWidth(450);
        }
        for(Flight cur: result.flights){
            HBox box = new HBox();
            VBox info1 = new VBox();
            Label code1 = new Label(cur.origin.code);
            Label time1 = new Label(cur.departureTime.toString());
            info1.getChildren().addAll(code1,time1);
            VBox info2 = new VBox();
            Label code2 = new Label(cur.destination.code);
            Label time2 = new Label(cur.arrivalTime.toString());
            info2.getChildren().addAll(code2,time2);
            long dur = Duration.between(cur.departureTime.toLocalTime(), cur.arrivalTime.toLocalTime()).toHours() + 1;
            String durString;
            if(dur==1){
                durString=dur+" hour";
            }
            else{
                durString=dur+" hour";
            }
            Label duration = new Label(durString);
            code1.setStyle("-fx-font: 20 arial;");
            code2.setStyle("-fx-font: 20 arial;");
            VBox mid = new VBox();
            Pane bar = new Pane();
            mid.setAlignment(Pos.CENTER);

            mid.getChildren().addAll(duration,bar);
            box.getChildren().addAll(info1,mid,info2);
            bar.setMinWidth(50);
            bar.setMaxHeight(5);
            bar.setStyle("-fx-background-color: grey;");
            mainBox.getChildren().addAll(box);
            if(cur!=result.flights.getLast()){
                Pane space = new Pane();
                space.setMinWidth(50);
                mainBox.getChildren().addAll(space);
            }
        }
        //final destination
        if(result.flights.size()==1){
            stopInfo.setText("Direct");
        }
        else{
            stopInfo.setText(String.valueOf(result.flights.size()-1) + " stop");
        }
        price.setText(String.valueOf(result.price)+ "€");
        flightInfo.setGraphic(vbox);
    }
}
