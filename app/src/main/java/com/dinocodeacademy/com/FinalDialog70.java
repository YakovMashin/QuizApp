package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;

public class FinalDialog70 extends FinalDialog100 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_dialog70);

        bt_Menu = findViewById(R.id.back_to_menu_btn);
        bt_details = findViewById(R.id.details_btn);

        bt_Menu.setOnClickListener(view -> {
            Intent intent = new Intent(FinalDialog70.this, PlayActivity.class);
            startActivity(intent);
        });
        Intent intent = getIntent();

        final int score = intent.getIntExtra("UserScore", 0);
        final int totalQuestion = intent.getIntExtra("TotalQuestion", 0);
        final int correctQues = intent.getIntExtra("CorrectQues", 0);
        final int wrongQues = intent.getIntExtra("WrongQues", 0);
        final String category = intent.getStringExtra("Category");
        final int Level = intent.getIntExtra("Level",0);

        bt_details.setOnClickListener(view -> {
            intentPutExtra(score,totalQuestion,correctQues,wrongQues,category,Level);
        });

    }
}