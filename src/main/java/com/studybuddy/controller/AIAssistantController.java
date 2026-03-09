package com.studybuddy.controller;

import com.studybuddy.ml.MLService;
import com.studybuddy.model.StudyTask;
import com.studybuddy.service.TaskManager;
import com.studybuddy.service.Navigator;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AIAssistantController {

    @FXML
    private VBox recommendationBox;

    @FXML
    private Label productivityLabel;


    // ================= GENERATE AI RECOMMENDATIONS =================

    @FXML
    private void generateRecommendation(){

        recommendationBox.getChildren().clear();

        if(TaskManager.tasks.isEmpty()){

            addCard("No study tasks found.");
            addCard("Go to Study Planner and add some study topics first.");

            return;
        }

        int completed = 0;
        int pending = 0;

        int totalStudyHours = 0;
        int totalDifficulty = 0;

        Map<String,Integer> subjectCount = new HashMap<>();


        for(StudyTask task : TaskManager.tasks){

            if(task.isCompleted())
                completed++;
            else
                pending++;

            totalStudyHours += task.getStudyHours();
            totalDifficulty += task.getDifficulty();

            subjectCount.put(
                    task.getSubject(),
                    subjectCount.getOrDefault(task.getSubject(),0) + 1
            );

        }


        // ===== BASIC INSIGHTS =====

        addCard("Completed Tasks: " + completed);
        addCard("Pending Tasks: " + pending);


        // ===== PRODUCTIVITY SCORE =====

        int total = completed + pending;

        if(total > 0){

            double productivity = (completed * 100.0) / total;

            addCard("Productivity Score: " + String.format("%.1f", productivity) + "%");

        }


        // ===== RULE BASED RECOMMENDATIONS =====

        if(pending > completed){

            addCard("Focus on completing your pending tasks first.");

        }
        else if(completed == total){

            addCard("Excellent work! You completed all your tasks.");

        }
        else{

            addCard("Good progress! Keep maintaining your study routine.");

        }


        // ===== SUBJECT ANALYSIS =====

        String topSubject = "";
        int max = 0;

        for(String subject : subjectCount.keySet()){

            if(subjectCount.get(subject) > max){

                max = subjectCount.get(subject);
                topSubject = subject;

            }

        }

        if(!topSubject.isEmpty()){

            addCard("You are focusing a lot on " + topSubject + ".");
            addCard("Consider revising advanced topics in " + topSubject + ".");

        }


        // ===== MACHINE LEARNING PREDICTION =====

        try{

            int studytime = Math.max(1, totalStudyHours);
            int failures = pending;
            int absences = Math.max(0, pending - completed);

            int difficulty = totalDifficulty / TaskManager.tasks.size();

            String prediction = MLService.predict(
                    studytime,
                    failures,
                    absences,
                    difficulty,
                    totalStudyHours
            );

            addCard("ML Prediction: " + prediction);

        }catch(Exception e){

            addCard("ML prediction currently unavailable.");

        }

        detectWeakSubject();

    }



    // ================= CREATE RECOMMENDATION CARD =================

    private void addCard(String text){

        HBox card = new HBox();
        card.getStyleClass().add("ai-rec-card");

        Label label = new Label("💡 " + text);
        label.getStyleClass().add("ai-rec");

        card.getChildren().add(label);

        recommendationBox.getChildren().add(card);

    }

    private void detectWeakSubject(){

    if(TaskManager.tasks.isEmpty())
        return;

    Map<String,Integer> totalMap = new HashMap<>();
    Map<String,Integer> completedMap = new HashMap<>();


    for(StudyTask task : TaskManager.tasks){

        String subject = task.getSubject();

        totalMap.put(
                subject,
                totalMap.getOrDefault(subject,0) + 1
        );

        if(task.isCompleted()){

            completedMap.put(
                    subject,
                    completedMap.getOrDefault(subject,0) + 1
            );

        }

    }

    String weakSubject = "";
    double lowestRate = 1.0;


    for(String subject : totalMap.keySet()){

        int total = totalMap.get(subject);
        int completed = completedMap.getOrDefault(subject,0);

        double rate = (double) completed / total;

        if(rate < lowestRate){

            lowestRate = rate;
            weakSubject = subject;

        }

    }

    if(!weakSubject.isEmpty()){

        addCard("AI Insight: Your weakest subject is " + weakSubject + ".");
        addCard("Recommendation: Spend more time practicing " + weakSubject + ".");

    }

}



    // ================= BACK TO DASHBOARD =================

    @FXML
    private void goToDashboard(){

        Stage stage = (Stage) recommendationBox.getScene().getWindow();

        Navigator.switchScene(stage, "dashboard.fxml", "style.css");

    }

}