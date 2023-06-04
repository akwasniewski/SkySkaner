package com.skyskaner.skyskaner;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
public class SearchUtils {
    public static LinkedList<SearchResult> Search(String origin, String destination, Date date) throws SQLException {
        String query = "SELECT * from getbest0((SELECT id_airport from airports JOIN cities ON airports.id_city=cities.id_city where cities.the_name=? LIMIT 1),(SELECT id_airport from airports JOIN cities ON airports.id_city=cities.id_city where cities.the_name=? LIMIT 1),?)";
        PreparedStatement preparedStatement = DatabaseHandler.connection.prepareStatement(query);
        preparedStatement.setString(1, origin);
        preparedStatement.setString(2, destination);
        preparedStatement.setDate(3, Date.valueOf(date.toLocalDate()));
        ResultSet rs = preparedStatement.executeQuery();
        LinkedList<SearchResult> flights = new LinkedList<>();
        Airport cur = null;
        while(rs.next()){
            int id= rs.getInt(1);
            if(cur==null){
                String firstCityQuery = "SELECT id_airport, airports.the_name, cities.the_name, code, airplanes.id_airplane FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_from JOIN airplanes ON flights.id_airplane=airplanes.id_airplane JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=?";
                PreparedStatement flightStatement = DatabaseHandler.connection.prepareStatement(firstCityQuery);
                flightStatement.setInt(1, id);
                ResultSet cityInfo = flightStatement.executeQuery();
                cityInfo.next();
                cur = new Airport(cityInfo.getInt(1),cityInfo.getString(2), cityInfo.getString(3),cityInfo.getString(4));
            }
            int price= rs.getInt(2);
            String secondCityQuery = "SELECT id_airport, airports.the_name, cities.the_name, code, airplanes.id_airplane FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_to JOIN airplanes ON flights.id_airplane=airplanes.id_airplane JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=?";
            PreparedStatement flightStatement = DatabaseHandler.connection.prepareStatement(secondCityQuery);
            flightStatement.setInt(1, id);
            ResultSet cityInfo = flightStatement.executeQuery();
            String airlinesQuery = "SELECT bb.the_name FROM airplanes NATURAL JOIN airlines_airplanes aa JOIN airlines bb ON aa.id_airline=bb.id_airline WHERE id_airplane=? ";
            PreparedStatement airlinesStatement = DatabaseHandler.connection.prepareStatement(airlinesQuery);
            cityInfo.next();
            int airportId=cityInfo.getInt(1);
            String airportName=cityInfo.getString(2);
            String cityName=cityInfo.getString(3);
            String airportCode=cityInfo.getString(4);
            int airplaneId=cityInfo.getInt(5);
            airlinesStatement.setInt(1, airplaneId);
            ResultSet airlinesInfo = airlinesStatement.executeQuery();
            LinkedList<Airline> airlines=new LinkedList<>();
            while(airlinesInfo.next()){
                airlines.add(new Airline(airlinesInfo.getString(1)));
            }
            Airport second = new Airport(airportId,airportName, cityName,airportCode);
            LinkedList<Flight> curFlights=new LinkedList<>();
            curFlights.add(new Flight(id, cur, second,price,airlines));
            flights.add(new SearchResult(curFlights,price));
            cur=second;
        }
        return flights;
    }
}
