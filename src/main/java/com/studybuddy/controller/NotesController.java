package com.studybuddy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import com.studybuddy.dao.NotesDAO;
import com.studybuddy.model.Note;
import com.studybuddy.service.Navigator;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class NotesController {

    @FXML private TextField titleField;
    @FXML private TextArea contentArea;
    @FXML private TextField subjectField;
    @FXML private TextField searchField;

    @FXML private VBox notesContainer;

    @FXML private Label totalNotesLabel;
    @FXML private Label pinnedNotesLabel;

    private int pinnedCount = 0;


    // ================= INITIALIZE =================

    @FXML
    public void initialize(){
        loadNotes();
    }


    // ================= LOAD NOTES FROM DATABASE =================

    private void loadNotes(){

        notesContainer.getChildren().clear();

        List<Note> notes = NotesDAO.getNotes();

        for(Note note : notes){
            displayNote(note);
        }

        totalNotesLabel.setText("Total Notes: " + notes.size());
    }


    // ================= SAVE NOTE =================

    @FXML
    private void saveNote(){

        String title = titleField.getText();
        String content = contentArea.getText();
        String subject = subjectField.getText();

        if(title.isEmpty() || content.isEmpty()){
            return;
        }

        int id = NotesDAO.saveNote(title,content,subject);

        Note note = new Note(id,title,content,subject,"Now");

        displayNote(note);

        titleField.clear();
        contentArea.clear();
        subjectField.clear();
    }


    // ================= DISPLAY NOTE CARD =================

    private void displayNote(Note note){

        VBox card = new VBox(8);
        card.getStyleClass().add("note-card");

        Label title = new Label(note.getTitle());
        title.getStyleClass().add("note-title");

        Label subject = new Label("🏷 " + note.getSubject());

        Label content = new Label(note.getContent());
        content.setWrapText(true);

        Label time = new Label(note.getTime());

        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_RIGHT);

        Button pin = new Button(note.isPinned() ? "Unpin" : "Pin");
        Button edit = new Button("Edit");
        Button delete = new Button("Delete");
        Button download = new Button("Download");


        // PIN NOTE (UI LEVEL)

        pin.setOnAction(e -> {

            note.setPinned(!note.isPinned());

            if(note.isPinned())
                pinnedCount++;
            else
                pinnedCount--;

            pinnedNotesLabel.setText("Pinned: " + pinnedCount);
        });


        // EDIT NOTE

        edit.setOnAction(e -> {

            titleField.setText(note.getTitle());
            contentArea.setText(note.getContent());
            subjectField.setText(note.getSubject());

            NotesDAO.deleteNote(note.getId());

        });


        // DELETE NOTE

        delete.setOnAction(e -> {

            NotesDAO.deleteNote(note.getId());

            notesContainer.getChildren().remove(card);

        });


        // DOWNLOAD NOTE

        download.setOnAction(e -> downloadNote(note));


        actions.getChildren().addAll(pin,edit,delete,download);

        card.getChildren().addAll(title,subject,content,time,actions);

        notesContainer.getChildren().add(card);
    }


    // ================= SEARCH =================

    @FXML
    private void searchNotes(){

        String keyword = searchField.getText().toLowerCase();

        notesContainer.getChildren().clear();

        List<Note> notes = NotesDAO.getNotes();

        for(Note note : notes){

            if(note.getTitle().toLowerCase().contains(keyword)
            || note.getContent().toLowerCase().contains(keyword)
            || note.getSubject().toLowerCase().contains(keyword)){

                displayNote(note);
            }

        }
    }


    // ================= AI SUMMARY =================

    @FXML
    private void generateSummary(){

        String text = contentArea.getText();

        if(text.length() < 50)
            return;

        String summary = text.substring(0,150) + "...";

        contentArea.setText(summary);
    }


    // ================= DOWNLOAD NOTE =================

    private void downloadNote(Note note){

        try{

            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialFileName(note.getTitle()+".txt");

            Stage stage = (Stage) notesContainer.getScene().getWindow();

            File file = fileChooser.showSaveDialog(stage);

            if(file == null) return;

            FileWriter writer = new FileWriter(file);

            writer.write("Title: " + note.getTitle()+"\n\n");
            writer.write("Subject: " + note.getSubject()+"\n\n");
            writer.write(note.getContent());

            writer.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // ================= NAVIGATION =================

    @FXML
    private void goToDashboard(){

        Stage stage = (Stage) notesContainer.getScene().getWindow();

        Navigator.switchScene(stage,"dashboard.fxml","style.css");
    }
}