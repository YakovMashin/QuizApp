package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FinalDialog100 extends AppCompatActivity {

    protected  Button bt_Menu;
    protected Button bt_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_final100);

        bt_Menu = findViewById(R.id.back_to_menu_btn);
        bt_details = findViewById(R.id.details_btn);

        bt_Menu.setOnClickListener(view -> {
            Intent intent = new Intent(FinalDialog100.this, PlayActivity.class);
            startActivity(intent);
        });


        Intent intent = getIntent();

        final  int score = intent.getIntExtra("UserScore", 0);
        final int totalQuestion = intent.getIntExtra("TotalQuestion", 0);
        final int correctQues = intent.getIntExtra("CorrectQues", 0);
        final int wrongQues = intent.getIntExtra("WrongQues", 0);
        final String category = intent.getStringExtra("Category");
        final int Level = intent.getIntExtra("Level",0);

        bt_details.setOnClickListener(view -> {
            intentPutExtra(score,totalQuestion,correctQues,wrongQues,category,Level);
        });
    }

    protected void intentPutExtra(int score, int total, int correct, int wrong,String category, int level){
        Intent resultData = new Intent(this, ResultActivity.class);

        resultData.putExtra("UserScore", score);
        resultData.putExtra("TotalQuestion", total);
        resultData.putExtra("CorrectQues", correct);
        resultData.putExtra("WrongQues", wrong);
        resultData.putExtra("Category",category );
        resultData.putExtra("Level", level);
        startActivity(resultData);
    }

}