package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class Settings extends AppCompatActivity {

    SwitchCompat switchButton;
    ImageView imageViewOn;

    public static boolean soundOn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchButton = findViewById(R.id.switchButton);
        imageViewOn = findViewById(R.id.sound_on);



        switchButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked()){
                imageViewOn.setImageResource((R.drawable.sound_off));
                soundOn = false;
            }
            else{
                imageViewOn.setImageResource(R.drawable.sound_on);
                soundOn = true;
            }
        });
    }
    public void onBackPressed() {

            Intent intent = new Intent(Settings.this,PlayActivity.class);
            startActivity(intent);


    }
}
