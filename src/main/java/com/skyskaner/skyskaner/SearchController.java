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
        LinkedList<SearchResult> flights = SearchUtils.Search(origin.getText(), destination.getText(), Date.valueOf(date.getValue()));
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
            Label code1 = new Label("(empty)");
            Label code2 = new Label("(empty)");
            VBox conInfo = new VBox();
            Pane up = new Pane();
            Pane left = new Pane();
            Pane right = new Pane();
            Label airline = new Label();
            Pane mid = new Pane();
            {
                mainBox.getChildren().addAll(code1,mid,code2);
                mainBox.setAlignment(Pos.CENTER);
                vbox.getChildren().addAll(stopInfo, price, mainBox, airline);
                vbox.setMinWidth(524);
                mid.setMinWidth(50);
                mid.setMaxHeight(5);
                code1.setStyle("-fx-font: 40 arial;");
                code2.setStyle("-fx-font: 40 arial;");
                mid.setStyle("-fx-background-color: grey;");
            }
            @Override
            protected void updateItem(SearchResult item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    code1.setText(item.flights.get(0).origin.code);
                    code2.setText(item.flights.get(0).destination.code);
                    airline.setText(item.flights.get(0).airlines.get(0).name);
                    if(item.flights.size()==1){
                        stopInfo.setText("Direct");
                    }
                    else{
                        stopInfo.setText(String.valueOf(item.flights.size()) + " stops");
                    }
                    price.setText(String.valueOf(item.flights.get(0).price)+ "€");
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
