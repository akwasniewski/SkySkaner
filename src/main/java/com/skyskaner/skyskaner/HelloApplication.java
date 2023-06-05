package com.skyskaner.skyskaner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {
    public static Integer userId=null;
    static FXMLLoader fxmlLoader;
    static Stage stage;
    static Scene scene;
    @Override
    public void start(Stage newStage) throws IOException, SQLException {
        stage=newStage;
        fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        DatabaseHandler.ConnectToPostgres();
        scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Skyskaner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static void SetUser(Integer user) throws IOException {
        userId=user;
        System.out.println("logged in as user:"+ userId );
        fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }

    public static void ChangeLogin(boolean val) throws IOException {
        if(val) fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        else fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
    }
    public static void showSearch() throws IOException {

        System.out.println("logged in as user:"+ userId );
        fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("search.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);

    }

    public static void ShowTickets() throws IOException {

        fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("showtickets.fxml"));
        scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
    }
}