package com.selabg11.freshbazaar;
public class Users {
    String userId;
    String userName;
    String userEmail;
    String userPn;
    String userAdd;

    String userType;
    String wallet_balance;


    public Users()
    {}

    public String getWallet_balance() {
        return wallet_balance;
    }

    public void setWallet_balance(String wallet_balance) {
        this.wallet_balance = wallet_balance;
    }

    public Users(String userId, String userName, String userPn, String userEmail, String userType, String userAdd, String wallet_balance) {
        this.userId = userId;
        this.userName=userName;
        this.userAdd=userAdd;
        this.userEmail=userEmail;
        this.wallet_balance=wallet_balance;

        this.userPn=userPn;
        this.userType = userType;
    }
    //  Users user=new Users(Userid,name,pn,email,type,add);


    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAdd() {
        return userAdd;
    }

    public void setUserAdd(String userAdd) {
        this.userAdd = userAdd;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPn(String userPn) {
        this.userPn = userPn;
    }

    public void setUserType(String userType) {
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
