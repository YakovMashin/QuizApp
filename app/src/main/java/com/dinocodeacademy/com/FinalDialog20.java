package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinalDialog20 extends FinalDialog100 {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_dialog20);

        bt_Menu = findViewById(R.id.back_to_menu_btn);
        bt_details = findViewById(R.id.details_btn);

        bt_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalDialog20.this, PlayActivity.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();

        final int score = intent.getIntExtra("UserScore", 0);
        final int totalQuestion = intent.getIntExtra("TotalQuestion", 0);
        final int correctQues = intent.getIntExtra("CorrectQues", 0);
        final int wrongQues = intent.getIntExtra("WrongQues", 0);
        final String category = intent.getStringExtra("Category");
        final int Level = intent.getIntExtra("Level",0);

        bt_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultData = new Intent(FinalDialog20.this, ResultActivity.class);

                resultData.putExtra("UserScore", score);
                resultData.putExtra("TotalQuestion", totalQuestion);
                resultData.putExtra("CorrectQues", correctQues);
                resultData.putExtra("WrongQues", wrongQues);
                resultData.putExtra("category",category );
                resultData.putExtra("Level", Level);

                startActivity(resultData);
            }
        });

    }
}