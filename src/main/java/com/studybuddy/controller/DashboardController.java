package com.studybuddy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;

import java.util.Optional;

import com.studybuddy.service.Navigator;
import com.studybuddy.session.UserSession;

import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class DashboardController {

    @FXML
    private VBox sidebar;
    @FXML
private Label welcomeLabel;

    @FXML
    private BorderPane root;

    @FXML
    private VBox card1, card2, card3;

    @FXML
    private LineChart<String, Number> studyChart;

    @FXML
    private Label timerLabel;

    private boolean collapsed = false;
    private boolean dark = false;

    private int seconds = 1500;
    private Timeline timeline;


    // ================= INITIALIZE =================

    @FXML
    public void initialize(){

        addHover(card1);
        addHover(card2);
        addHover(card3);

        loadChart();

        welcomeLabel.setText(
        "Welcome " + UserSession.getUserName() + " 👋"
);

    }


    // ================= CARD HOVER =================

    private void addHover(VBox card){

        card.setOnMouseEntered(e -> {

            ScaleTransition scale = new ScaleTransition(Duration.millis(200), card);
            scale.setToX(1.07);
            scale.setToY(1.07);
            scale.play();

        });

        card.setOnMouseExited(e -> {

            ScaleTransition scale = new ScaleTransition(Duration.millis(200), card);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();

        });

    }


    // ================= OPEN AI PAGE =================

    @FXML
    private void openAIPage(){

        try{

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/aiassistant.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) sidebar.getScene().getWindow();

            Scene scene = new Scene(root,1100,650);

            scene.getStylesheets().add(
                    getClass().getResource("/css/ai.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    // ================= OPEN STUDY PLANNER =================

    @FXML
    private void openStudyPlanner(){

        try{

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/fxml/studyplanner.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) sidebar.getScene().getWindow();

            Scene scene = new Scene(root,1100,650);

            scene.getStylesheets().add(
                    getClass().getResource("/css/planner.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            e.printStackTrace();
        }

    }


    // ================= LOAD CHART =================

    private void loadChart(){

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Mon",2));
        series.getData().add(new XYChart.Data<>("Tue",3));
        series.getData().add(new XYChart.Data<>("Wed",4));
        series.getData().add(new XYChart.Data<>("Thu",2));
        series.getData().add(new XYChart.Data<>("Fri",5));

        studyChart.getData().add(series);

    }


    // ================= SIDEBAR TOGGLE =================

    @FXML
    private void toggleSidebar(){

        if(collapsed){

            sidebar.setPrefWidth(230);

        }else{

            sidebar.setPrefWidth(60);

        }

        collapsed = !collapsed;

    }


    // ================= TIMER =================

    @FXML
    private void startTimer(){

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            seconds--;

            int min = seconds / 60;
            int sec = seconds % 60;

            timerLabel.setText(String.format("%02d:%02d",min,sec));

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }
    @FXML
private void openAnalytics(){

    try{

        Parent root = FXMLLoader.load(
            getClass().getResource("/fxml/analytics.fxml"));

        Stage stage = (Stage) sidebar.getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.show();

    }catch(Exception e){
        e.printStackTrace();
    }

}
 // ================= qizz  =================
@FXML
private void openQuizPage(){

    System.out.println("Quiz button clicked");

    Stage stage = (Stage) sidebar.getScene().getWindow();

    Navigator.switchScene(stage,"quiz.fxml","quiz.css");

}

@FXML
private void openQuizSetup(){

    Stage stage = (Stage) sidebar.getScene().getWindow();

    Navigator.switchScene(stage,"quizsetup.fxml","style.css");
}

@FXML
private void handleLogout(){

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    alert.setTitle("Logout");
    alert.setHeaderText("Confirm Logout");
    alert.setContentText("Are you sure you want to logout?");

    Optional<ButtonType> result = alert.showAndWait();

    if(result.isPresent() && result.get() == ButtonType.OK){

        try{

            // Clear logged user session
            UserSession.clear();

            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/fxml/login.fxml"));

            Parent root = loader.load();

            Stage stage = (Stage) sidebar.getScene().getWindow();

            Scene scene = new Scene(root,1100,650);

            scene.getStylesheets().add(
                    getClass().getResource("/css/style.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.setMaximized(true);

            System.out.println("User logged out successfully");

        }catch(Exception e){

            System.out.println("Logout Error");
            e.printStackTrace();

        }

    }

}


  // ================= OPEN notes =================
@FXML
private void openNotesPage() {

    System.out.println("Notes button clicked");

    try {

        // Debug path check
        System.out.println("Trying to load: /fxml/notes.fxml");

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/notes.fxml"));

        if(loader.getLocation() == null){
            System.out.println("❌ notes.fxml not found!");
            return;
        }

        Parent root = loader.load();

        Stage stage = (Stage) sidebar.getScene().getWindow();

        Scene scene = new Scene(root,1100,650);

        // Load CSS safely
        try{

            String cssPath = "/css/notes.css";

            if(getClass().getResource(cssPath) != null){

                scene.getStylesheets().add(
                        getClass().getResource(cssPath).toExternalForm());

                System.out.println("✅ notes.css loaded");

            }else{

                System.out.println("⚠ notes.css not found");

            }

        }catch(Exception cssError){

            System.out.println("CSS loading error");
            cssError.printStackTrace();

        }

        stage.setScene(scene);
        stage.show();

        System.out.println("✅ Notes page loaded successfully");

    }
    catch(Exception e){

        System.out.println("❌ Error loading Notes page");

        e.printStackTrace();

    }
}


    @FXML
    private void resetTimer(){

        seconds = 1500;
        timerLabel.setText("25:00");

        if(timeline != null)
            timeline.stop();

    }


    // ================= THEME TOGGLE =================

    @FXML
    private void toggleTheme(){

        if(dark){

            root.getStylesheets().clear();
            root.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
            );

        }else{

            root.getStylesheets().clear();
            root.getStylesheets().add(
                getClass().getResource("/css/dark.css").toExternalForm()
            );

        }

        dark = !dark;

    }

}