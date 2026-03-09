package com.studybuddy.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/studybuddy";
    private static final String USER = "root";
    private static final String PASSWORD = "Matkor7979@"; 

    public static Connection getConnection() {

        try {

            // Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println(" Database Connected Successfully");

            return con;

        } catch (Exception e) {

            System.out.println(" Database Connection Failed");
            e.printStackTrace();

        }

        return null;
    }
}