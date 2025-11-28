package org.viswa.model;

public class User {
    private  Integer userId;
    private String userName;
    private String passWord;

    public User(Integer userId, String userName, String passWord) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
    }

    public Integer getUserId() {return userId;}

    public void setUserId(int userId) { this.userId = userId;}

    public String getUserName() {   return userName; }

    public void setUserName(String userName) {   this.userName = userName; }

    public String getPassWord() { return passWord;}

    public void setPassWord(String passWord) {  this.passWord = passWord; }
   

}
