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

    private static SharedPreferences sharedPreferences;
    private static Map<String, UserData> users;
    private static final String LAST_SIGNED_IN_USER = "last_signed_in_user";


    public Map<String, UserData> getUsers() {
        return users;
    }

    public UserData getUserData(String username) {
        return users.get(username);
    }

    public String getPassword(String username) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            return userData.getPassword();
        }
        return null;
    }

    public static String getLastSignedInUser() {
        return sharedPreferences.getString(LAST_SIGNED_IN_USER, null);
    }

    public void setScore(String username, int score) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            userData.setScore(score);
        }
    }

    public static int getScore(String username) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            return userData.getScore();
        }
        return 0;
    }

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


    public void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (UserData userData : users.values()) {
            editor.putString(userData.getUsername(), userData.getPassword());
            editor.putInt(userData.getUsername() + "_score", userData.getScore());
        }
        editor.apply();
    }

    public SharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences("userData", Context.MODE_PRIVATE);
        users = new HashMap<>();
        loadData();
    }

    public boolean registerUser(Context context, String username, String password) {
        if (!users.containsKey(username)) {
            UserData userData = new UserData();
            userData.setUsername(username);
            userData.setPassword(password);
            userData.setScore(0);
            users.put(username, userData);
            saveData();
            return true;
        }
        Toast.makeText(context, "An account with this usarename already exist", Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean loginUser(String username, String password) {
        if (users.containsKey(username)) {
            UserData userData = users.get(username);
            if (userData.getPassword().equals(password)) {
                storeLastSignedInUser(username);
                return true;
            }
        }
        return false;
    }

    public void storeLastSignedInUser(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAST_SIGNED_IN_USER, username);
        editor.apply();
    }

    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LAST_SIGNED_IN_USER);
        editor.apply();
    }

}



