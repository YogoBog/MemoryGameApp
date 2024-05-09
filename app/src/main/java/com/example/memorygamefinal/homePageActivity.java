package com.example.memorygamefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.memorygamefinal.UserData;
import com.google.android.material.snackbar.Snackbar;

import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public class homePageActivity extends AppCompatActivity {

    private SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        sharedPrefs = new SharedPrefs(this);
        Map<String, UserData> users = sharedPrefs.getUsers();

        ImageView start = findViewById(R.id.startImageView);
        start.setOnClickListener(view -> {
            String lastSignedInUser = sharedPrefs.getLastSignedInUser();
            if (lastSignedInUser != null) {
                UserData userData = users.get(lastSignedInUser);
                if (userData != null) {
                    if (sharedPrefs.loginUser(lastSignedInUser, userData.getPassword())) {
                        Toast.makeText(homePageActivity.this, "Successfully logged in, welcome " + userData.getUsername(), Toast.LENGTH_SHORT).show();
                        openGame(start);
                    }
                }
            } else {
                openlogin(start);
            }
        });
    }

    public void openlogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openGame(View view) {
        startActivity(new Intent(this, gameActivity.class));
    }

}