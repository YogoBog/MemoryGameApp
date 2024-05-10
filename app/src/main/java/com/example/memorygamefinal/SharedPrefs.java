package com.example.memorygamefinal;

import static android.accounts.AccountManager.KEY_PASSWORD;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;


public class SharedPrefs {

    // SharedPreferences instance to handle data persistence
    private static SharedPreferences sharedPreferences;

    // Map to store user data
    private static Map<String, UserData> users;

    // Key for storing the username of the last signed-in user
    private static final String LAST_SIGNED_IN_USER = "last_signed_in_user";

    // Constructor for SharedPrefs class
    public SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        users = new HashMap<>();
        loadData(); // Load user data from SharedPreferences when the SharedPrefs object is created
    }

    // Method to retrieve the map of users
    public Map<String, UserData> getUsers() {
        return users;
    }

    // Method to retrieve UserData object for a specific username
    public UserData getUserData(String username) {
        return users.get(username);
    }

    // Method to retrieve password for a specific username
    public String getPassword(String username) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            return userData.getPassword();
        }
        return null;
    }

    // Method to retrieve the username of the last signed-in user
    public static String getLastSignedInUser() {
        return sharedPreferences.getString(LAST_SIGNED_IN_USER, null);
    }

    // Method to set score for a specific user
    public void setScore(String username, int score) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            userData.setScore(score);
        }
    }

    // Method to retrieve score for a specific user
    public static int getScore(String username) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            return userData.getScore();
        }
        return 0;
    }

    // Method to load user data from SharedPreferences
    private void loadData() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            if (!key.endsWith("_score")) {
                UserData userData = new UserData();
                userData.setUsername(key);
                userData.setPassword((String) entry.getValue());
                userData.setScore(sharedPreferences.getInt(key + "_score", 0));
                users.put(userData.getUsername(), userData);
            }
        }
    }

    // Method to save user data to SharedPreferences
    public void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (UserData userData : users.values()) {
            editor.putString(userData.getUsername(), userData.getPassword());
            editor.putInt(userData.getUsername() + "_score", userData.getScore());
        }
        editor.apply();
    }

    // Method to register a new user
    public boolean registerUser(Context context, String username, String password) {
        if (!users.containsKey(username)) {
            UserData userData = new UserData();
            userData.setUsername(username);
            userData.setPassword(password);
            userData.setScore(0);
            users.put(username, userData);
            saveData(); // Save user data after registration
            return true;
        }
        Toast.makeText(context, "An account with this username already exists", Toast.LENGTH_SHORT).show();
        return false;
    }

    // Method to login a user
    public boolean loginUser(String username, String password) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            if (userData.getPassword().equals(password)) {
                storeLastSignedInUser(username); // Store the username of the signed-in user
      
