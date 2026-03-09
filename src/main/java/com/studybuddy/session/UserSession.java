package com.studybuddy.session;

public class UserSession {

    private static int userId;
    private static String userName;

    public static void setUser(int id,String name){
        userId = id;
        userName = name;
    }

    public static int getUserId(){
        return userId;
    }

    public static String getUserName(){
        return userName;
    }

    public static void clear(){
        userId = 0;
        userName = null;
    }

}