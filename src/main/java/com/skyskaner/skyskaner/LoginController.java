package com.skyskaner.skyskaner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Label errorLabel;
    public void Login() throws NoSuchAlgorithmException {
        String hashedPassword=HashPassword(passwordField.getText());
        String query ="select * from users where username='"+usernameField.getText()+"' and password='"+hashedPassword+"'";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                HelloApplication.SetUser(rs.getInt("id_user"));
                return;
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        WrongCredentials();
    }
    public String HashPassword(String prev) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(prev.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }
    public void WrongCredentials(){
        System.out.println("wrong credentials");
        passwordField.setText("");
        errorLabel.setText("Wrong Credentials!!!");
    }
}
