package com.example.memorygamefinal;

public class UserData {
    private String username;
    private String password;
    private int score;

    public int getScore() {
        return score;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
