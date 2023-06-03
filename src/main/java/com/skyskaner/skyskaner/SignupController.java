package com.skyskaner.skyskaner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class SignupController {
    @FXML
    TextField usernameField;
    @FXML
    TextField passwordField;
    @FXML
    Label errorLabel;
    public void Signup() throws NoSuchAlgorithmException {
        String hashedPassword=HashPassword(passwordField.getText());
        String query ="select * from users where username='"+usernameField.getText()+"'";
        try (Statement stmt = DatabaseHandler.connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()){
                ErrorMessage("Username taken :(");
                return;
            }
            Random rn = new Random();
            int random=rn.nextInt();
            String insert = "INSERT into users values (?, ?, ?)";
            PreparedStatement preparedStatement = DatabaseHandler.connection.prepareStatement(insert);
            preparedStatement.setInt(1, random);
            preparedStatement.setString(2, usernameField.getText());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.executeUpdate();
            HelloApplication.SetUser(random);
        }
        catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
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
    public void ErrorMessage(String err){
        passwordField.setText("");
        errorLabel.setText(err);
    }
    public void ChangeLogin() throws IOException {
        HelloApplication.ChangeLogin(false);
    }
}
