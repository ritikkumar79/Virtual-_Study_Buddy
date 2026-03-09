package com.studybuddy.controller;

import com.studybuddy.dao.UserDAO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SignupController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;



    @FXML
    private void handleSignup(){

        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if(name.isEmpty() || email.isEmpty() || password.isEmpty()){

            statusLabel.setText("Please fill all fields");

            return;

        }

        boolean created = UserDAO.register(name,email,password);

        if(created){

            statusLabel.setText("Account created successfully!");

        }else{

            statusLabel.setText("Email already exists");

        }

    }



    @FXML
    private void goToLogin(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) nameField.getScene().getWindow();

            Scene scene = new Scene(root,1100,650);

            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);

        }catch(Exception e){

            e.printStackTrace();

        }

    }

}