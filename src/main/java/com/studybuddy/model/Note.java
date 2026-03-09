package com.studybuddy.model;

public class Note {

    private int id;
    private String title;
    private String content;
    private String subject;
    private String time;
    private boolean pinned;

    public Note(int id,String title,String content,String subject,String time){

        this.id = id;
        this.title = title;
        this.content = content;
        this.subject = subject;
        this.time = time;
        this.pinned = false;
    }

    public int getId(){ return id; }

    public String getTitle(){ return title; }

    public String getContent(){ return content; }

    public String getSubject(){ return subject; }

    public String getTime(){ return time; }

    public boolean isPinned(){ return pinned; }

    public void setPinned(boolean pinned){ this.pinned = pinned; }
}