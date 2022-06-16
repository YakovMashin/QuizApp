package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LevelsActivity extends AppCompatActivity implements View.OnClickListener {

    Button level1, level2, level3;

    String categoryValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        level1 = findViewById(R.id.beginner);
        level2 = findViewById(R.id.professional);
        level3 = findViewById(R.id.world_class);

        level1.setOnClickListener(this);
        level2.setOnClickListener(this);
        level3.setOnClickListener(this);

        Intent intent = getIntent();
        categoryValue = intent.getStringExtra("Category");
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.beginner:
                Intent intentBeginner = new Intent(LevelsActivity.this, QuizActivity.class);
                intentBeginner.putExtra("Level",1);
                intentBeginner.putExtra("Category", categoryValue);
                startActivity(intentBeginner);
                break;

            case R.id.professional:
                Intent intentProf = new Intent(LevelsActivity.this, QuizActivity.class);
                intentProf.putExtra("Level",2);
                intentProf.putExtra("Category", categoryValue);
                startActivity(intentProf);
                break;

            case R.id.world_class:
                Intent intentWC = new Intent(LevelsActivity.this, QuizActivity.class);
                intentWC.putExtra("Level",3);
                intentWC.putExtra("Category", categoryValue);
                startActivity(intentWC);
                break;
        }

    }

    public void onBackPressed() {
        Intent intent = new Intent(LevelsActivity.this, CategoryActivity.class);
        startActivity(intent);
    }


}