package com.dinocodeacademy.com;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class Settings extends AppCompatActivity {

    SwitchCompat switchButton;
    ImageView imageViewOn;
    private Context mContext;

    public Settings(){}

    public Settings(Context mContext) {
        this.mContext = mContext;
    }


    private String soundState;

    public static final String PREFERRENCE = "shared_prefrence";
    //public static final boolean TOGGLE_STATE = false;
    public static final String SOUND_STATE = "sound state";
    public static final String PREFERRENCE_SOUND_ON = "sound_on";
    public static final String PREFERENCE_SOUND_OFF = "sound_of";

    public String getSoundState() {
        return soundState;
    }

    public void setSoundState(String soundState) {
        this.soundState = soundState;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchButton = findViewById(R.id.switchButton);
        imageViewOn = findViewById(R.id.sound_on);


        loadSound();


        switchButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked()){
                soundState = PREFERRENCE_SOUND_ON;
                updateSoundState(soundState);

            }
            else{
                updateSoundState(soundState);
                soundState = PREFERENCE_SOUND_OFF;
                imageViewOn.setImageResource(R.drawable.sound_on);
            }
        });
    }

    private void updateSoundState(String soundState) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERRENCE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFERRENCE_SOUND_ON,soundState);
        editor.apply();
    }

    private void loadSound() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERRENCE,MODE_PRIVATE);
        sharedPreferences.getString(SOUND_STATE, PREFERRENCE_SOUND_ON);
    }

    public void onBackPressed() {

            Intent intent = new Intent(Settings.this,PlayActivity.class);
            startActivity(intent);


    }
}
