package com.skyskaner.skyskaner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    DatePicker date;
    @FXML
    TextField origin;
    @FXML
    TextField destination;
    @FXML
    Label errorLabel;
    @FXML
    ListView<SearchResult> searchResultList;

    public void Search() throws SQLException {
        if(origin.getText().equals("") || destination.getText().equals("") || date==null){
            ErrorMessage("Provide all data");
            return;
        }
        LinkedList<SearchResult> flights = SearchUtils.Search(origin.getText().toUpperCase(), destination.getText().toUpperCase(), Date.valueOf(date.getValue()));
        if(flights.isEmpty()){
            ErrorMessage("No flights matching your query found");
            searchResultList.getItems().clear();
            return;
        }
        ErrorMessage("");
        searchResultList.getItems().clear();
        searchResultList.getItems().addAll(flights);
    }

    public void ErrorMessage(String err){
        errorLabel.setText(err);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchResultList.setCellFactory(param -> new ListCell<SearchResult>() {
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
            @Override
            protected void updateItem(SearchResult item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    mainBox.getChildren().clear();
                    for(Flight cur: item.flights){
                        HBox box = new HBox();
                        VBox info1 = new VBox();
                        Label code1 = new Label(cur.origin.code);
                        Label time1 = new Label(cur.departureTime.toString());
                        info1.getChildren().addAll(code1,time1);
                        VBox info2 = new VBox();
                        Label code2 = new Label(cur.destination.code);
                        Label time2 = new Label(cur.arrivalTime.toString());
                        info2.getChildren().addAll(code2,time2);
                        code1.setStyle("-fx-font: 20 arial;");
                        code2.setStyle("-fx-font: 20 arial;");
                        Pane mid = new Pane();
                        box.getChildren().addAll(info1,mid,info2);
                        mid.setMinWidth(50);
                        mid.setMaxHeight(5);
                        mid.setStyle("-fx-background-color: grey;");
                        mainBox.getChildren().addAll(box);
                        if(cur!=item.flights.getLast()){
                            Pane space = new Pane();
                            space.setMinWidth(50);
                            mainBox.getChildren().addAll(space);
                        }
                    }
                    //final destination
                    if(item.flights.size()==1){
                        stopInfo.setText("Direct");
                    }
                    else{
                        stopInfo.setText(String.valueOf(item.flights.size()-1) + " stop");
                    }
                    price.setText(String.valueOf(item.price)+ "€");
                    setGraphic(vbox);
                }
            }
        });

        /*searchResultList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (searchResultList.getSelectionModel().getSelectedItem() == null) return;
                if(!SongQueue.IsEmpty()) {
                    confirm.showAndWait();
                    if (confirm.getResult() == ButtonType.NO) {
                        return;
                    }
                }
                SongQueue.Clear();
                SongQueue.AddFront((Song) searchResultList.getSelectionModel().getSelectedItem());
                EventHandlers.Next.handle(new ActionEvent());
            }
        });*/
    }

    public void goTickets(ActionEvent actionEvent) throws IOException {
        HelloApplication.ShowTickets();
    }
}
