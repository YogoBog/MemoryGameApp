package com.example.memorygamefinal;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.atomic.AtomicInteger;

public class gameActivity extends AppCompatActivity implements View.OnClickListener {
    int Score;
    LinearLayout mainlayout;
    Pics po = new Pics();
    boolean firstTurn = true;
    int id;
    int idOfpicThatAlreadyBeenChosed;
    int picNum;
    int picThatWasClicked;
    boolean[] foundPics = new boolean[4];



    public boolean isWin(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i])
                return false;
        }
        return true;
    }

    //////////////////////////////////
    @Override
    @SuppressLint("UseCompatLoadingForDrawables")
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Button logout = findViewById(R.id.logOutButton);
        TextView UserScore = findViewById(R.id.UserScoreTextview);
        TextView currentUserScore = findViewById(R.id.UserScoreTxt);
        Button again = findViewById(R.id.button);
        Button reset = findViewById(R.id.ResetButton);
        TextView score = findViewById(R.id.txt);
        mainlayout = findViewById(R.id.mainlayout);
        Score = 0;
        score.setText(Score + "");

        SharedPrefs myshare = new SharedPrefs(this);
        String username = myshare.getLastSignedInUser();
        UserScore.setText(username + "'s user score:");
        currentUserScore.setText(myshare.getScore(username) + " ");

        again.setVisibility(View.INVISIBLE);
        for (int i = 1; i <= 8; i++) {
            View v = findViewById(getResources().getIdentifier("imageButton" + i, "id", getPackageName()));
            v.setOnClickListener(this);
            ((ImageButton) v).setImageDrawable(getDrawable(getResources().getIdentifier("cardback", "drawable", getPackageName())));
            ((ImageButton) v).setAdjustViewBounds(true);
        }
        reset.setOnClickListener(view -> {
            for (int i = 1; i <= 8; i++) {
                View v = findViewById(getResources().getIdentifier("imageButton" + i, "id", getPackageName()));
                ((ImageButton) v).setImageDrawable(getDrawable(getResources().getIdentifier("cardback", "drawable", getPackageName())));
                ((ImageButton) v).setAdjustViewBounds(true);
                ((ImageButton) v).setEnabled(true);
            }
            again.setVisibility(View.INVISIBLE);
            Log.d("banana", myshare.getScore(username) + " ");
            Score = 0;
            score.setText(0 + "");
            firstTurn = true;
            foundPics = new boolean[4];
            po.newOrder();


        });


        logout.setOnClickListener(view -> {
            openHome(logout);
            myshare.logoutUser();
            finish();
        });
    }

    @Override
    public void onClick(View view) {
        TextView currentUserScore = findViewById(R.id.UserScoreTxt);
        SharedPrefs myshare = new SharedPrefs(this);
        String username = myshare.getLastSignedInUser();
        ImageButton b = (ImageButton) view;
        Button again = findViewById(R.id.button);
        TextView score = findViewById(R.id.txt);
        again.setVisibility(View.INVISIBLE);
        id = Integer.parseInt(String.valueOf(getResources().getResourceEntryName(b.getId()).charAt(11)));

        picNum = po.getPics()[id - 1];
        if (isWin(foundPics))
            Toast.makeText(this, "You won!", Toast.LENGTH_SHORT).show();

        else if (firstTurn && !foundPics[picNum - 1]) {
            b.setImageDrawable(getDrawable(getResources().getIdentifier("pic" + picNum, "drawable", getPackageName())));
            b.setAdjustViewBounds(true);
            picThatWasClicked = picNum;
            firstTurn = false;
            idOfpicThatAlreadyBeenChosed = b.getId();
        } else if (!foundPics[picNum - 1] && idOfpicThatAlreadyBeenChosed != b.getId()) {
            b.setImageDrawable(getDrawable(getResources().getIdentifier("pic" + picNum, "drawable", getPackageName())));
            b.setAdjustViewBounds(true);
            for (int i = 1; i <= 8; i++) {
                View v = findViewById(getResources().getIdentifier("imageButton" + i, "id", getPackageName()));
                v.setEnabled(false);
            }

            again.setVisibility(View.VISIBLE);
            again.setOnClickListener(view1 -> {
                for (int i = 1; i <= 8; i++) {
                    View v = findViewById(getResources().getIdentifier("imageButton" + i, "id", getPackageName()));
                    v.setEnabled(true);
                }
                if (picThatWasClicked != picNum) {
                    ImageButton b1 = findViewById(idOfpicThatAlreadyBeenChosed);
                    b1.setImageDrawable(getDrawable(getResources().getIdentifier("cardback", "drawable", getPackageName())));
                    b1.setAdjustViewBounds(true);
                    b.setImageDrawable(getDrawable(getResources().getIdentifier("cardback", "drawable", getPackageName())));
                    b.setAdjustViewBounds(true);
                    Score = Score - 1;
                    score.setText(Score + "");

                } else {
                    foundPics[picNum - 1] = true;
                    Score = Score + 2;
                    score.setText(Score + "");

                }
                again.setVisibility(View.INVISIBLE);

                if (isWin(foundPics)) {
                    myshare.setScore(username, myshare.getScore(username) + Score);
                    currentUserScore.setText(myshare.getScore(username) + " ");
                    myshare.saveData();
                    Snackbar.make(mainlayout, "Congratulations, You won!!", Snackbar.LENGTH_LONG).show();

                } else {
                    firstTurn = true;
                }
            });

        }

    }

    public void openHome(View view) {
        startActivity(new Intent(this, homePageActivity.class));
    }
}