package com.dinocodeacademy.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView txtHighScore;
    TextView txtTotalQuizQues;
    TextView txtCorrectQues;
    TextView txtWrongQues;

    Button btnextLevel;
    Button btMainMenu;

    // init shared prefs for high score
    private int highScore;
    public static final String SHARED_PREFERRENCE = "shread_prefrence";
    public static final String SHARED_PREFERRENCE_HIGH_SCORE = "shread_prefrence_high_score";

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        btMainMenu = findViewById(R.id.result_bt_mainmenu);
        btnextLevel = findViewById(R.id.result_bt_playAgain);
        txtHighScore = findViewById(R.id.result_text_High_Score);
        txtTotalQuizQues = findViewById(R.id.result_total_Ques);
        txtCorrectQues = findViewById(R.id.result_Correct_Ques);
        txtWrongQues = findViewById(R.id.result_Wrong_Ques);

// get the intnet from result dialogs
        Intent intent = getIntent();

        int score = intent.getIntExtra("UserScore",0);
        int totalQuestion = intent.getIntExtra("TotalQuestion",0);
        int correctQues = intent.getIntExtra("CorrectQues",0);
        int wrongQues = intent.getIntExtra("WrongQues",0);
        final String Category = intent.getStringExtra("Category");
        final int Level = intent.getIntExtra("Level", 1);


        btMainMenu.setOnClickListener(v -> {

            Intent intent1 = new Intent(ResultActivity.this, PlayActivity.class);
            startActivity(intent1);

        });

        // go to next level quiz
        btnextLevel.setOnClickListener(v -> {
            if(Level == 3){ // reached max level
                Toast.makeText(ResultActivity.this, "You've reached last level", Toast.LENGTH_LONG).show();
            }
            else {
                Intent intent12 = new Intent(ResultActivity.this, QuizActivity.class);
                intent12.putExtra("Level", Level + 1);
                intent12.putExtra("Category", Category);
                startActivity(intent12);
            }
        });

        loadHighScore(); // load the ahsred prefs

        if (score > highScore){ // highscore broken

            updatHighScore(score);
        }
        txtHighScore.setText("High Score: "+ highScore);
        txtTotalQuizQues.setText("Total Ques: " + totalQuestion);
        txtCorrectQues.setText("Correct: " + correctQues);
        txtWrongQues.setText("Wrong: " + wrongQues);
    }

    private void updatHighScore(int newHighScore) {

        highScore = newHighScore; // update the highscore to user's recent score
        txtHighScore.setText("High Score: " + highScore);

        // edit shared prefs accordingly
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERRENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHARED_PREFERRENCE_HIGH_SCORE,highScore);
        editor.apply();
    }

    // get current high score from shared prefs
    private void loadHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERRENCE,MODE_PRIVATE);
        highScore = sharedPreferences.getInt(SHARED_PREFERRENCE_HIGH_SCORE, 0);
        txtHighScore.setText("High Score: " + highScore);
        txtHighScore.setTextColor(Color.WHITE);
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){

            Intent intent = new Intent(ResultActivity.this,PlayActivity.class);
            startActivity(intent);

        }else {
            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();

        }
        backPressedTime = System.currentTimeMillis();
    }
}
