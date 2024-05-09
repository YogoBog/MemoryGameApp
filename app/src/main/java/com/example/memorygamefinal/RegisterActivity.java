package com.example.memorygamefinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class RegisterActivity extends AppCompatActivity {

    ConstraintLayout cons;
    private AppCompatCheckBox checkbox;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        SharedPrefs myshare = new SharedPrefs(this);
        EditText usernameEDT = findViewById(R.id.usernameRegEditText);
        EditText passwordEDT = findViewById(R.id.passwordRegEditText);
        Button register = findViewById(R.id.registerButton);
        cons = (ConstraintLayout)findViewById(R.id.register_layout);
        checkbox = findViewById(R.id.checkBox2);

        checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (!isChecked) {
                // show password
                passwordEDT.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                // hide password
                passwordEDT.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });


        register.setOnClickListener(view -> {
            EditText usernameEDT1 = findViewById(R.id.usernameRegEditText);
            EditText passwordEDT1 = findViewById(R.id.passwordRegEditText);
            String username = usernameEDT1.getText().toString();
            String password = passwordEDT1.getText().toString();
            if (TextUtils.isEmpty(usernameEDT1.getText().toString()) || TextUtils.isEmpty(passwordEDT1.getText().toString()))
                Snackbar.make(cons, "You need to type in both username and password", Snackbar.LENGTH_LONG).show();
            else {
                if(myshare.registerUser(RegisterActivity.this, username,password)) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                    openLogin(register);
                    finish();
                }
            }
        });
    }


    public void openLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}