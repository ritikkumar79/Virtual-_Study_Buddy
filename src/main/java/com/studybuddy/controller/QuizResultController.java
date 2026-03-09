package com.studybuddy.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.studybuddy.service.Navigator;

public class QuizResultController {

    @FXML
    private Label scoreLabel;

    @FXML
    private Label feedbackLabel;

    @FXML
    private PieChart resultChart;

    private static int score;
    private static int total;

    public static void setResult(int s,int t){

        score = s;
        total = t;
    }

    @FXML
    public void initialize(){

        scoreLabel.setText("Score: "+score+"/"+total);

        double percent = (score*100.0)/total;

        if(percent>=80)
            feedbackLabel.setText("Excellent performance!");
        else if(percent>=50)
            feedbackLabel.setText("Good job, revise weak areas.");
        else
            feedbackLabel.setText("You should practice more.");

        resultChart.getData().add(new PieChart.Data("Correct",score));
        resultChart.getData().add(new PieChart.Data("Wrong",total-score));
    }

    @FXML
    private void goToDashboard(){

        Stage stage = (Stage) scoreLabel.getScene().getWindow();

        Navigator.switchScene(stage,"dashboard.fxml","style.css");
    }
}