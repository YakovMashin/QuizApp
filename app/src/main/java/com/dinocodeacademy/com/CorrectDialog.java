package com.dinocodeacademy.com;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class CorrectDialog   {

    private final Context mContext;

    private Dialog correctDialog;

    public CorrectDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void correctDialog(int score, final QuizActivity quizActivity, int streakCounter, final OnFireDialog onFire, int questionCounter, int size){

        // init new dialog
        correctDialog = new Dialog(mContext);
        correctDialog.setContentView(R.layout.correct_dialog);
        correctDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        Button btCorrectDialog = correctDialog.findViewById(R.id.bt_correct_dialog);
        score(score);
        btCorrectDialog.setOnClickListener(v -> {
            correctDialog.dismiss();
            quizActivity.showQuestions(); // go back to quiz and show questions
        });

        correctDialog.show();
        correctDialog.setCancelable(false);
        correctDialog.setCanceledOnTouchOutside(false);

        Objects.requireNonNull(correctDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if(streakCounter %5 ==0 && streakCounter !=0 && questionCounter != size){ // show streak dialog if there is a streak

            correctDialog.dismiss();
            onFire.onFireDialog(correctDialog, quizActivity);// show the dialog in quiz activity instead of correct dialog
            quizActivity.multiplier = 2; // introduce double points
        }
    }

    @SuppressLint("SetTextI18n")
    private void score(int score) { // calculate the score

        TextView textViewScore = correctDialog.findViewById(R.id.text_score);
        textViewScore.setText("Score: " + score);
    }


}
