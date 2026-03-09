package com.studybuddy.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import com.studybuddy.model.StudyTask;
import com.studybuddy.service.TaskManager;
import com.studybuddy.service.Navigator;

import java.util.HashMap;
import java.util.Map;

public class AnalyticsController {

    @FXML
    private PieChart subjectChart;

    @FXML
    private LineChart<String, Number> weeklyChart;

    @FXML
    private Label productivityLabel;

    @FXML
    private Label totalTasksLabel;

    @FXML
    private Label completedTasksLabel;



    // ================= INITIALIZE =================

    @FXML
    public void initialize(){

        loadSubjectChart();
        loadWeeklyChart();
        calculateStats();

    }



    // ================= CALCULATE STATS =================

    private void calculateStats(){

        int completed = 0;
        int total = TaskManager.tasks.size();

        for(StudyTask task : TaskManager.tasks){

            if(task.isCompleted())
                completed++;

        }

        totalTasksLabel.setText(String.valueOf(total));
        completedTasksLabel.setText(String.valueOf(completed));

        if(total == 0){

            productivityLabel.setText("0%");
            return;

        }

        double productivity = (completed * 100.0) / total;

        productivityLabel.setText(String.format("%.1f%%", productivity));

    }



    // ================= LOAD SUBJECT PIE CHART =================

    private void loadSubjectChart(){

        Map<String,Integer> subjectCount = new HashMap<>();

        for(StudyTask task : TaskManager.tasks){

            subjectCount.put(
                    task.getSubject(),
                    subjectCount.getOrDefault(task.getSubject(),0)+1
            );

        }

        subjectChart.getData().clear();

        for(String subject : subjectCount.keySet()){

            subjectChart.getData().add(
                    new PieChart.Data(subject, subjectCount.get(subject))
            );

        }

    }



    // ================= LOAD WEEKLY LINE CHART =================

    private void loadWeeklyChart(){

        weeklyChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Mon",2));
        series.getData().add(new XYChart.Data<>("Tue",3));
        series.getData().add(new XYChart.Data<>("Wed",4));
        series.getData().add(new XYChart.Data<>("Thu",1));
        series.getData().add(new XYChart.Data<>("Fri",5));

        weeklyChart.getData().add(series);

    }



    // ================= NAVIGATE BACK TO DASHBOARD =================

    @FXML
    private void goToDashboard(){

        Stage stage = (Stage) productivityLabel.getScene().getWindow();

        Navigator.switchScene(stage, "dashboard.fxml", "style.css");

    }

}