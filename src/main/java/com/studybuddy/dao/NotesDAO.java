package com.studybuddy.dao;

import java.sql.*;
import java.util.*;

import com.studybuddy.model.Note;
import com.studybuddy.session.UserSession;

public class NotesDAO {

    // SAVE NOTE
    public static int saveNote(String title,String content,String subject){

        int noteId = -1;

        try{

            Connection con = DBConnection.getConnection();

            String sql =
            "INSERT INTO notes(user_id,title,content,subject) VALUES(?,?,?,?)";

            PreparedStatement ps =
            con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1,UserSession.getUserId());
            ps.setString(2,title);
            ps.setString(3,content);
            ps.setString(4,subject);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if(rs.next()){
                noteId = rs.getInt(1);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return noteId;
    }


    // GET NOTES
    public static List<Note> getNotes(){

        List<Note> list = new ArrayList<>();

        try{

            Connection con = DBConnection.getConnection();

            String sql =
            "SELECT * FROM notes WHERE user_id=? ORDER BY created_at DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,UserSession.getUserId());

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Note n = new Note(

                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("subject"),
                    rs.getString("created_at").toString()

                );

                list.add(n);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }


    // DELETE
    public static void deleteNote(int id){

        try{

            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM notes WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,id);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    // UPDATE
    public static void updateNote(int id,String title,String content){

        try{

            Connection con = DBConnection.getConnection();

            String sql =
            "UPDATE notes SET title=?,content=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1,title);
            ps.setString(2,content);
            ps.setInt(3,id);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}