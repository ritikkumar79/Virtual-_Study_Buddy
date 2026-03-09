package com.studybuddy.controller;

import com.studybuddy.model.Question;
import com.studybuddy.service.Navigator;
import com.studybuddy.service.QuizAPIService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;

import java.util.List;

public class QuizController {

    @FXML
    private Label questionLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private RadioButton option1;

    @FXML
    private RadioButton option2;

    @FXML
    private RadioButton option3;

    @FXML
    private RadioButton option4;

    private ToggleGroup optionsGroup = new ToggleGroup();

    private List<Question> questions;

    private int currentIndex = 0;

    private int score = 0;

    private int timeLeft = 30;

    private Timeline timer;

    @FXML
    public void initialize(){

        option1.setToggleGroup(optionsGroup);
        option2.setToggleGroup(optionsGroup);
        option3.setToggleGroup(optionsGroup);
        option4.setToggleGroup(optionsGroup);

        QuizAPIService service = new QuizAPIService();

        questions = service.fetchQuestions();

        loadQuestion();
    }

    private void loadQuestion(){

        if(currentIndex >= questions.size()){
            

            QuizResultController.setResult(score,questions.size());


            Stage stage = (Stage) questionLabel.getScene().getWindow();

            Navigator.switchScene(stage,"quizresult.fxml","style.css");


            return;
        }

        Question q = questions.get(currentIndex);

        progressBar.setProgress((double)currentIndex/questions.size());

        questionLabel.setText(q.getQuestion());

        option1.setText(q.getOptions().get(0));
        option2.setText(q.getOptions().get(1));
        option3.setText(q.getOptions().get(2));
        option4.setText(q.getOptions().get(3));

        optionsGroup.selectToggle(null);

        startTimer();
    }

    private void startTimer(){

        timeLeft = 30;

        timerLabel.setText(timeLeft + "s");

        if(timer != null)
            timer.stop();

        timer = new Timeline(new KeyFrame(Duration.seconds(1),e->{

            timeLeft--;

            timerLabel.setText(timeLeft+"s");

            if(timeLeft <= 0){

                timer.stop();

                nextQuestion();
            }

        }));

        timer.setCycleCount(Timeline.INDEFINITE);

        timer.play();
    }

    @FXML
    private void nextQuestion(){

        RadioButton selected = (RadioButton) optionsGroup.getSelectedToggle();

        if(selected != null){

            String answer = selected.getText();

            if(answer.equals(questions.get(currentIndex).getCorrectAnswer()))
                score++;
        }

        currentIndex++;

        loadQuestion();
    }
}