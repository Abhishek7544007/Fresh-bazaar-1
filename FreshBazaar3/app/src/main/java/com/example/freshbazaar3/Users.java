package com.example.freshbazaar3;

public class Users
{
String userId;
String userName;
String userEmail;
String userPn;

String userType;

public Users()
{}

    public Users(String userId, String userName, String userPn, String userEmail, String userType) {
        this.userId = userId;
        this.userName=userName;
this.userEmail=userEmail;
this.userPn=userPn;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public String getUserPn() {
        return userPn;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public String getUserType() {
        return userType;
    }
}
