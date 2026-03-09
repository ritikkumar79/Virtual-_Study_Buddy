package com.studybuddy.controller;

import com.studybuddy.dao.UserDAO;
import com.studybuddy.session.UserSession;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;


    // ================= LOGIN =================

    @FXML
private void handleLogin(){

    String email = emailField.getText();
    String password = passwordField.getText();

    if(email == null || email.isEmpty()){
        errorLabel.setText("Email required");
        errorLabel.setVisible(true);
        return;
    }

    if(password == null || password.isEmpty()){
        errorLabel.setText("Password required");
        errorLabel.setVisible(true);
        return;
    }

    boolean validUser = UserDAO.login(email,password);

    if(validUser){

        openDashboard();

    }else{

        errorLabel.setText("Invalid email or password");
        errorLabel.setVisible(true);

    }

}


    // ================= OPEN SIGNUP =================

    @FXML
    private void openSignupPage(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();

            Scene scene = new Scene(root,1100,650);

            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);

        }catch(Exception e){

            e.printStackTrace();

        }

    }


    // ================= OPEN DASHBOARD =================

    private void openDashboard(){

        try{

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) emailField.getScene().getWindow();

            Scene scene = new Scene(root,1100,650);

            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.setMaximized(true);

        }catch(Exception e){

            System.out.println("Error opening dashboard");

            e.printStackTrace();

        }

    }

}