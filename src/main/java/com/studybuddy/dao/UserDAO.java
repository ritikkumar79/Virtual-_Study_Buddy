package com.studybuddy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.studybuddy.session.UserSession;

public class UserDAO {

    // ================= LOGIN =================

   public static boolean login(String email,String password){

    try{

        Connection con = DBConnection.getConnection();

        String sql =
        "SELECT * FROM users WHERE email=? AND password=?";

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setString(1,email);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){

            int id = rs.getInt("id");
            String name = rs.getString("name");

            UserSession.setUser(id,name);

            return true;
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    return false;
}


    // ================= REGISTER =================

    public static boolean register(String name, String email, String password) {

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "INSERT INTO users(name,email,password) VALUES(?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();

            System.out.println("User registered successfully");

            return true;

        } catch (Exception e) {

            System.out.println("Signup Error");

            e.printStackTrace();

        }

        return false;
    }

}