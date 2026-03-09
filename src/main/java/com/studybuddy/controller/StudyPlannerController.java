package com.studybuddy.controller;

import com.studybuddy.service.Navigator;
import com.studybuddy.model.StudyTask;
import com.studybuddy.service.TaskManager;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.Node;

public class StudyPlannerController {

    @FXML
    private TextField subjectField;

    @FXML
    private TextField topicField;

    @FXML
    private DatePicker datePicker;

    // NEW ML INPUTS
    @FXML
    private ComboBox<Integer> difficultyBox;

    @FXML
    private TextField studyHoursField;

    @FXML
    private VBox taskContainer;

    @FXML
    private LineChart<String, Number> studyChart;

    @FXML
    private Label totalTasksLabel;

    @FXML
    private Label completedTasksLabel;

    @FXML
    private Label studyHoursLabel;

    @FXML
    private Label productivityLabel;


    // ================= INITIALIZE =================

    @FXML
    public void initialize() {

        loadChart();
        updateStats();

        // difficulty values for ML
        difficultyBox.getItems().addAll(1,2,3,4,5);

    }


    // ================= NAVIGATE BACK TO DASHBOARD =================

    @FXML
    private void goToDashboard() {

        Stage stage = (Stage) taskContainer.getScene().getWindow();

        Navigator.switchScene(stage, "dashboard.fxml", "style.css");

    }


    // ================= ADD TASK =================

    @FXML
    private void addTask() {

        String subject = subjectField.getText();
        String topic = topicField.getText();

        if (subject == null || subject.isEmpty()) return;
        if (topic == null || topic.isEmpty()) return;
        if (datePicker.getValue() == null) return;
        if (difficultyBox.getValue() == null) return;
        if (studyHoursField.getText().isEmpty()) return;

        String date = datePicker.getValue().toString();

        int difficulty = difficultyBox.getValue();
        int hours = Integer.parseInt(studyHoursField.getText());


        // create task
        StudyTask task = new StudyTask(subject, topic, false, difficulty, hours);
        TaskManager.tasks.add(task);


        HBox card = new HBox();
        card.getStyleClass().add("task-card");
        card.setSpacing(15);


        CheckBox complete = new CheckBox();

        complete.setOnAction(e -> {

            task.setCompleted(complete.isSelected());
            updateStats();

        });


        VBox textArea = new VBox();
        textArea.setSpacing(3);

        Label subjectLabel = new Label("📚 " + subject);
        Label topicLabel = new Label("Topic: " + topic);
        Label difficultyLabel = new Label("Difficulty: " + difficulty);
        Label hoursLabel = new Label("Study Hours: " + hours);
        Label dateLabel = new Label("Date: " + date);

        textArea.getChildren().addAll(
                subjectLabel,
                topicLabel,
                difficultyLabel,
                hoursLabel,
                dateLabel
        );

        HBox.setHgrow(textArea, Priority.ALWAYS);


        Button deleteBtn = new Button("Delete");
        deleteBtn.getStyleClass().add("delete-btn");

        deleteBtn.setOnAction(e -> {

            taskContainer.getChildren().remove(card);
            TaskManager.tasks.remove(task);

            updateStats();

        });


        card.getChildren().addAll(complete, textArea, deleteBtn);

        addHoverAnimation(card);

        taskContainer.getChildren().add(card);


        subjectField.clear();
        topicField.clear();
        studyHoursField.clear();
        difficultyBox.setValue(null);
        datePicker.setValue(null);

        updateStats();

    }



    // ================= UPDATE STATS =================

    private void updateStats() {

        int total = 0;
        int completed = 0;
        int totalHours = 0;

        for (StudyTask task : TaskManager.tasks) {

            total++;
            totalHours += task.getStudyHours();

            if(task.isCompleted())
                completed++;

        }

        totalTasksLabel.setText(String.valueOf(total));
        completedTasksLabel.setText(String.valueOf(completed));

        studyHoursLabel.setText(totalHours + "h");

    }



    // ================= LOAD CHART =================

    private void loadChart() {

        studyChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        series.getData().add(new XYChart.Data<>("Mon", 2));
        series.getData().add(new XYChart.Data<>("Tue", 3));
        series.getData().add(new XYChart.Data<>("Wed", 4));
        series.getData().add(new XYChart.Data<>("Thu", 1));
        series.getData().add(new XYChart.Data<>("Fri", 5));

        studyChart.getData().add(series);

    }



    // ================= CARD HOVER ANIMATION =================

    private void addHoverAnimation(Node card) {

        card.setOnMouseEntered(e -> {

            ScaleTransition scale = new ScaleTransition(Duration.millis(150), card);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();

        });

        card.setOnMouseExited(e -> {

            ScaleTransition scale = new ScaleTransition(Duration.millis(150), card);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();

        });

    }

}