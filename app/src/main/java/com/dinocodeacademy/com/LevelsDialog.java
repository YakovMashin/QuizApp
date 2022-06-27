package com.dinocodeacademy.com;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

import java.util.Objects;

public class LevelsDialog {

    private final Context mContext;

    private Dialog levelsDialog;

    public LevelsDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void LevelsDialog(String category){

        // init new dialog
        levelsDialog = new Dialog(mContext);
        levelsDialog.setContentView(R.layout.activity_levels);
        levelsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button level1 = levelsDialog.findViewById(R.id.beginner);
        Button level2 = levelsDialog.findViewById(R.id.professional);
        Button level3 = levelsDialog.findViewById(R.id.world_class);

        // pass the category and level to QuizActivity
        level1.setOnClickListener(v -> {
            Intent intentBeginner = new Intent(mContext, QuizActivity.class);
            intentBeginner.putExtra("Level",1);
            intentBeginner.putExtra("Category", category);
            mContext.startActivity(intentBeginner);
        });
        level2.setOnClickListener(v -> {
            Intent intentProf = new Intent(mContext, QuizActivity.class);
            intentProf.putExtra("Level",2);
            intentProf.putExtra("Category", category);
            mContext.startActivity(intentProf);
        });
        level3.setOnClickListener(v -> {
            Intent intentWC = new Intent(mContext, QuizActivity.class);
            intentWC.putExtra("Level",3);
            intentWC.putExtra("Category", category);
            mContext.startActivity(intentWC);
        });

        levelsDialog.show();
        levelsDialog.setCancelable(true);
        levelsDialog.setCanceledOnTouchOutside(true);

        Objects.requireNonNull(levelsDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
