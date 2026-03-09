package com.studybuddy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import com.studybuddy.service.Navigator;
import com.studybuddy.service.QuizAPIService;

public class QuizSetupController {

    @FXML
    private ComboBox<String> subjectBox;

    @FXML
    private ComboBox<String> difficultyBox;

    @FXML
    private Spinner<Integer> questionCount;

    @FXML
    public void initialize(){

        subjectBox.getItems().addAll(
                "Computer Science",
                "Mathematics",
                "General Knowledge",
                "Science",
                "History"
        );

        difficultyBox.getItems().addAll(
                "easy","medium","hard"
        );

        questionCount.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(5,20,5)
        );
    }

    @FXML
    private void startQuiz(){

        String subject = subjectBox.getValue();
        String difficulty = difficultyBox.getValue();
        int count = questionCount.getValue();

        if(subject == null || difficulty == null){
            System.out.println("Select subject and difficulty first");
            return;
        }

        QuizAPIService.setQuizConfig(count,subject,difficulty);

        Stage stage = (Stage) subjectBox.getScene().getWindow();

        Navigator.switchScene(stage,"quiz.fxml","quiz.css");
    }

    @FXML
    private void goToDashboard(){

        Stage stage = (Stage) subjectBox.getScene().getWindow();

        Navigator.switchScene(stage,"dashboard.fxml","style.css");
    }
}