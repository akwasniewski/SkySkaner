package com.skyskaner.skyskaner;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Random;

public class SearchController {
    @FXML
    DatePicker date;
    @FXML
    TextField origin;
    @FXML
    TextField destination;
    @FXML
    Label errorLabel;
    public void Search() throws SQLException {
        if(origin.getText().equals("") || destination.getText().equals("") || date==null){
            ErrorMessage("Provide all data");
            return;
        }
        String query = "SELECT * from getbest0((SELECT id_airport from airports JOIN cities ON airports.id_city=cities.id_city where cities.the_name=? LIMIT 1),(SELECT id_airport from airports JOIN cities ON airports.id_city=cities.id_city where cities.the_name=? LIMIT 1),?)";
        PreparedStatement preparedStatement = DatabaseHandler.connection.prepareStatement(query);
        preparedStatement.setString(1, origin.getText());
        preparedStatement.setString(2, destination.getText());
        preparedStatement.setDate(3, Date.valueOf(date.getValue()));
        ResultSet rs = preparedStatement.executeQuery();
        LinkedList<Flight> flights = new LinkedList<>();
        Airport cur = null;
        while(rs.next()){
            int id= rs.getInt(1);
            if(cur==null){
                String firstCityQuery = "SELECT id_airport, airports.the_name, cities.the_name, code FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_from JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=?";
                PreparedStatement flightStatement = DatabaseHandler.connection.prepareStatement(firstCityQuery);
                flightStatement.setInt(1, id);
                ResultSet cityInfo = flightStatement.executeQuery();
                cityInfo.next();
                cur = new Airport(cityInfo.getInt(1),cityInfo.getString(2), cityInfo.getString(3),cityInfo.getString(4));
            }
            int price= rs.getInt(2);
            String secondCityQuery = "SELECT id_airport, airports.the_name, cities.the_name, code FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_to JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=?";
            PreparedStatement flightStatement = DatabaseHandler.connection.prepareStatement(secondCityQuery);
            flightStatement.setInt(1, id);
            ResultSet cityInfo = flightStatement.executeQuery();
            cityInfo.next();
            Airport second = new Airport(cityInfo.getInt(1),cityInfo.getString(2), cityInfo.getString(3),cityInfo.getString(4));
            flights.add(new Flight(id, cur, second,price));
            cur=second;
        }
        if(flights.isEmpty()){
            ErrorMessage("Sorry, no suitable flights found");
            return;
        }
        ErrorMessage("");
        System.out.println(flights.get(0).origin.code + " " + flights.get(0).destination.code + " " +flights.get(0).price);
    }
    public void ErrorMessage(String err){
        errorLabel.setText(err);
    }
}
