package com.skyskaner.skyskaner;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
public class SearchUtils {
    public static LinkedList<SearchResult> Search(String origin, String destination, Date date) throws SQLException {
        LinkedList<SearchResult> allFlights = new LinkedList<>();
        allFlights.addAll(SearchDirect(origin, destination, date));
        allFlights.addAll(SearchOneStop(origin,destination,date));
        return allFlights;
    }
    public static LinkedList<SearchResult> SearchDirect(String origin, String destination, Date date) throws SQLException {
        String query = "SELECT * from getbest0(?,?,?)";
        PreparedStatement preparedStatement = DatabaseHandler.connection.prepareStatement(query);
        preparedStatement.setString(1, origin);
        preparedStatement.setString(2, destination);
        preparedStatement.setDate(3, Date.valueOf(date.toLocalDate()));
        ResultSet rs = preparedStatement.executeQuery();
        LinkedList<SearchResult> flights = new LinkedList<>();
        while(rs.next()){
            int id= rs.getInt(1);
            int price = rs.getInt(2);
            Flight flight = GetFlight(id);
            flights.add(new SearchResult(flight, price));
        }
        return flights;
    }
    public static LinkedList<SearchResult> SearchOneStop(String origin, String destination, Date date) throws SQLException {
        String query = "SELECT * from getbest1((SELECT id_airport from airports JOIN cities ON airports.id_city=cities.id_city where cities.the_name=? LIMIT 1),(SELECT id_airport from airports JOIN cities ON airports.id_city=cities.id_city where cities.the_name=? LIMIT 1),?)";
        PreparedStatement preparedStatement = DatabaseHandler.connection.prepareStatement(query);
        preparedStatement.setString(1, origin);
        preparedStatement.setString(2, destination);
        preparedStatement.setDate(3, Date.valueOf(date.toLocalDate()));
        ResultSet rs = preparedStatement.executeQuery();
        LinkedList<SearchResult> flights = new LinkedList<>();
        while(rs.next()){
            int id1= rs.getInt(1);
            int id2= rs.getInt(1);
            int price = rs.getInt(3);
            Flight flight1 = GetFlight(id1);
            LinkedList<Flight> curFlights = new LinkedList<>();
            curFlights.add(flight1);
            Flight flight2 = GetFlight(id2);
            curFlights.add(flight2);
            flights.add(new SearchResult(curFlights, price));
        }
        return flights;
    }
    public static Flight GetFlight(int flightId) throws SQLException {
        //first airport;
        String firstCityQuery = "SELECT id_airport, airports.the_name, cities.the_name, code FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_from JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=?";
        PreparedStatement firstCityStatement = DatabaseHandler.connection.prepareStatement(firstCityQuery);
        firstCityStatement.setInt(1, flightId);
        ResultSet firstCityInfo = firstCityStatement.executeQuery();
        firstCityInfo.next();
        Airport firstAirport = new Airport(firstCityInfo.getInt(1),firstCityInfo.getString(2), firstCityInfo.getString(3),firstCityInfo.getString(4));

        //second airport
        String secondCityQuery = "SELECT id_airport, airports.the_name, cities.the_name, code FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_to JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=?";
        PreparedStatement secondCityStatement = DatabaseHandler.connection.prepareStatement(secondCityQuery);
        secondCityStatement.setInt(1, flightId);
        ResultSet secondCityInfo = secondCityStatement.executeQuery();
        secondCityInfo.next();
        Airport secondAirport = new Airport(secondCityInfo.getInt(1),secondCityInfo.getString(2), secondCityInfo.getString(3),secondCityInfo.getString(4));

        //airlines
        String airlinesQuery = "SELECT bb.the_name FROM airplanes NATURAL JOIN airlines_airplanes aa JOIN airlines bb ON aa.id_airline=bb.id_airline JOIN flights on airplanes.id_airplane=flights.id_airplane WHERE id_flight=? ";
        PreparedStatement airlinesStatement = DatabaseHandler.connection.prepareStatement(airlinesQuery);
        airlinesStatement.setInt(1, flightId);
        ResultSet airlinesInfo = airlinesStatement.executeQuery();
        LinkedList<Airline> airlines=new LinkedList<>();
        while(airlinesInfo.next()){
            airlines.add(new Airline(airlinesInfo.getString(1)));
        }
        return new Flight(flightId, firstAirport, secondAirport, airlines);
    }
}
    //SELECT id_airport, airports.the_name, cities.the_name, code, the_date FROM flights NATURAL JOIN connections JOIN airports ON id_airport=id_airport_from JOIN airplanes ON flights.id_airplane=airplanes.id_airplane JOIN cities ON airports.id_city=cities.id_city WHERE id_flight=10;
