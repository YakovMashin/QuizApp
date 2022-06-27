package com.dinocodeacademy.com;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class OnFireDialog extends AppCompatActivity {

    private final Context mContext;
    private Dialog fireDialog;

    public OnFireDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void onFireDialog(final Dialog correctDialog, final QuizActivity quizActivity){

        // init new dialog
        fireDialog = new Dialog(mContext);
        fireDialog.setContentView(R.layout.fire_dialog);
        Objects.requireNonNull(fireDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;

        Button btCorrectDialog = fireDialog.findViewById(R.id.bt_fire_dialog);

        btCorrectDialog.setOnClickListener(v -> {
            fireDialog.dismiss();
            quizActivity.showQuestions();
        });
        fireDialog.show();
        fireDialog.setCancelable(false);
        fireDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(fireDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}

