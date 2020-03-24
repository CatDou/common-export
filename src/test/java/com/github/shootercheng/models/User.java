package com.github.shootercheng.models;

/**
 * @author James
 */
public class User {
    private String userName;
    private String passWord;
    private Integer seq;

    public User() {

    }

    public User(String userName, String passWord, Integer seq) {
        this.userName = userName;
        this.passWord = passWord;
        this.seq = seq;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
