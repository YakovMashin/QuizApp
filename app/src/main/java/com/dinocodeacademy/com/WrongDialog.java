package com.dinocodeacademy.com;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class WrongDialog {


    private final Context mContext;

    private Dialog wrongDialog;

    private QuizActivity mquizActivity;

    public WrongDialog(Context mContext) {
        this.mContext = mContext;
    }


    @SuppressLint("SetTextI18n")
    public void wrongDialog(String correctAnswer, final QuizActivity quizActivity){
        mquizActivity = quizActivity;

        // init new dialog
        wrongDialog = new Dialog(mContext);
        wrongDialog.setContentView(R.layout.wrong_dialog);
        Objects.requireNonNull(wrongDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        Button btWrongDialog =  wrongDialog.findViewById(R.id.bt_wrong_dialog);
        TextView textViewCorrectAnswer =  wrongDialog.findViewById(R.id.text_correct_ans);

        textViewCorrectAnswer.setText("Correct Ans: " + correctAnswer); // get correct answer from the quiz and show it

        btWrongDialog.setOnClickListener(v -> {
            wrongDialog.dismiss();
            mquizActivity.showQuestions();
        });

        wrongDialog.show();
        wrongDialog.setCancelable(false);
        wrongDialog.setCanceledOnTouchOutside(false);

        Objects.requireNonNull(wrongDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }



}
