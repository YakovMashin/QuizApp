package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {

    Button btHistory, btProg, btTech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        btHistory = findViewById(R.id.bt_hist_start);
        btProg = findViewById(R.id.bt_prog_start);
        btTech = findViewById(R.id.bt_tech_start);

        btHistory.setOnClickListener(this);
        btProg.setOnClickListener(this);
        btTech.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_hist_start:
                Intent intentHistory = new Intent(CategoryActivity.this, LevelsActivity.class);
                intentHistory.putExtra("Category", Constants.HISTORY);
                startActivity(intentHistory);

                break;

            case R.id.bt_tech_start:
                Intent intentTech = new Intent(CategoryActivity.this, LevelsActivity.class);
                intentTech.putExtra("Category", Constants.TECHNOLOGY);
                startActivity(intentTech);

                break;

            case R.id.bt_prog_start:
                Intent intentProg = new Intent(CategoryActivity.this, LevelsActivity.class);
                intentProg.putExtra("Category", Constants.PROGRAMMING);
                startActivity(intentProg);
                break;

        }

    }
    public void onBackPressed() {
            Intent intent = new Intent(CategoryActivity.this, PlayActivity.class);
            startActivity(intent);

        }

    }
