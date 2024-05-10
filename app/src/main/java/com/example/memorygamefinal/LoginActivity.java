package com.example.memorygamefinal;

// Import statements
import static android.app.ProgressDialog.show;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.widget.AppCompatCheckBox;
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
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton;
import com.google.android.material.snackbar.Snackbar;

// Class definition
public class LoginActivity extends AppCompatActivity {

    // Member variables
    private ConstraintLayout cons;
    private SharedPrefs myshare;
    private EditText usernameEDT;
    private EditText passwordEDT;
    private AppCompatCheckBox checkbox;

    // onCreate method called when activity is created
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Hide the title bar and set full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // Hide action bar
        setContentView(R.layout.activity_login); // Set layout

        // Initialize shared preferences
        myshare = new SharedPrefs(this);

        // Find views by their IDs
        Button loginButton = findViewById(R.id.loginButton);
        cons = findViewById(R.id.login_layout);
        usernameEDT = findViewById(R.id.usernameRegEditText);
        passwordEDT = findViewById(R.id.passwordRegEditText);
        checkbox = findViewById(R.id.checkBox2);

        // Set a listener for the checkbox to toggle password visibility
        checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            passwordEDT.setTransformationMethod(isChecked ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        });

        // Set a click listener for the login button
        loginButton.setOnClickListener(view -> loginUser());
    }

    // Method to handle user login
    private void loginUser() {
        // Get username and password from EditText fields
        String username = usernameEDT.getText().toString().trim();
        String password = passwordEDT.getText().toString().trim();
        // Check if username or password is empty
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            // Show a snackbar if fields are empty
            Snackbar.make(cons, "Field is empty", Snackbar.LENGTH_LONG).show();
        } else {
            // Check if login is successful
            if (myshare.loginUser(username, password)) {
                // Show toast message for successful login
                Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                // Open game activity and finish current activity
                openGame();
                finish();
            } else {
                // Show snackbar for login error
                Snackbar.make(cons, "Error", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    // Method to open RegisterActivity
    public void openRegister(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    // Method to open game activity
    public void openGame() {
        startActivity(new Intent(this, gameActivity.class));
    }
}
