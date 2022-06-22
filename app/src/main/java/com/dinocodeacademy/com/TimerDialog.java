package com.dinocodeacademy.com;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;

import java.util.Objects;

public class TimerDialog {
    
    private final Context mContext;
    private Dialog TimerDialog;


    public TimerDialog(Context mContext) {
        this.mContext = mContext;
    }
    
    public void timerDialog(){
        
        
        TimerDialog = new Dialog(mContext);
        TimerDialog.setContentView(R.layout.timer_dialog);
        Objects.requireNonNull(TimerDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        
        final Button btTimer =  (Button) TimerDialog.findViewById(R.id.bt_timer);
        
        
       
        btTimer.setOnClickListener(v -> {

            TimerDialog.dismiss();
            Intent intent = new Intent(mContext, PlayActivity.class);
            mContext.startActivity(intent);


        });

        TimerDialog.show();
        TimerDialog.setCancelable(false);
        TimerDialog.setCanceledOnTouchOutside(false);

        Objects.requireNonNull(TimerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
    }

   
}
