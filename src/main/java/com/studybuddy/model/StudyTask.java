package com.studybuddy.model;

public class StudyTask {

    private String subject;
    private String topic;
    private boolean completed;

    private int difficulty;
    private int studyHours;

    public StudyTask(String subject,String topic,boolean completed,int difficulty,int studyHours){

        this.subject = subject;
        this.topic = topic;
        this.completed = completed;
        this.difficulty = difficulty;
        this.studyHours = studyHours;

    }

    public String getSubject(){
        return subject;
    }

    public String getTopic(){
        return topic;
    }

    public boolean isCompleted(){
        return completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public int getStudyHours(){
        return studyHours;
    }

}