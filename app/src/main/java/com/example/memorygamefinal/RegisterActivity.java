package com.example.memorygamefinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    // Declare variables
    private ConstraintLayout cons;
    private EditText usernameEDT;
    private EditText passwordEDT;
    private Button registerButton;
    private AppCompatCheckBox checkBox;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide the title bar and set the activity to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        
        // Set the layout for this activity
        setContentView(R.layout.activity_register);
        
        // Initialize shared preferences
        SharedPrefs myshare = new SharedPrefs(this);

        // Initialize UI components
        cons = findViewById(R.id.register_layout);
        usernameEDT = findViewById(R.id.usernameRegEditText);
        passwordEDT = findViewById(R.id.passwordRegEditText);
        registerButton = findViewById(R.id.registerButton);
        checkBox = findViewById(R.id.checkBox2);

        // Set listener for checkbox to show/hide password
        checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked) {
                // Show password
                passwordEDT.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                // Hide password
                passwordEDT.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        // Set listener for register button click
        registerButton.setOnClickListener(view -> registerUser());
    }

    // Method to handle registering a new user
    private void registerUser() {
        // Get username and password from input fields
        String username = usernameEDT.getText().toString();
        String password = passwordEDT.getText().toString();

        // Check if username or password is empty
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            // Show a Snackbar message if either field is empty
            Snackbar.make(cons, "You need to type in both username and password", Snackbar.LENGTH_LONG).show();
        } else {
            // If both fields are filled, attempt to register the user
            SharedPrefs myshare = new SharedPrefs(this);
            if (myshare.registerUser(RegisterActivity.this, username, password)) {
                // If registration is successful, show a toast message, open login activity, and finish this activity
                Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                openLogin();
                finish();
            }
        }
    }

    // Method to open the login activity
    public void openLogin() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
