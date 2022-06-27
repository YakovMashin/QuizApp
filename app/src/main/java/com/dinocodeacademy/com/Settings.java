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
    private  boolean toggleState;


    // save toggle button state
    public static final String TOGGLE_STATE = "toggle state";// name
    public static final boolean PREFERENCE_TOGGLE_CHECKED = true;
    public static final boolean PREFRENCE_TOGGLE_UNCHECKED = false;

    // save sound state
    public static final String SOUND_STATE = "sound state"; // name
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

        loadSound(); // load current sound

        switchButton.setOnCheckedChangeListener((compoundButton, b) -> {
            if(compoundButton.isChecked()){
                soundState = PREFERENCE_SOUND_OFF; // turn of the sound
                toggleState = PREFERENCE_TOGGLE_CHECKED; //
                updateSoundState(soundState,toggleState);// update states to new
                imageViewOn.setImageResource(R.drawable.sound_off);


            }
            else{
                soundState = PREFERRENCE_SOUND_ON;
                toggleState = PREFRENCE_TOGGLE_UNCHECKED;
                updateSoundState(soundState, toggleState);
                imageViewOn.setImageResource(R.drawable.sound_on);

            }
        });
    }



    private void updateSoundState(String soundState,
     boolean toggleState) {
SharedPreferences sharedPreferences = getSharedPreferences(SOUND_STATE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SOUND_STATE,soundState);
        editor.apply();
        SharedPreferences toggle = getSharedPreferences(TOGGLE_STATE,MODE_PRIVATE);
        editor = toggle.edit();
        editor.putBoolean(TOGGLE_STATE, toggleState);
        editor.apply();
        // edit shared preferences with the new states and save
    }

    private void loadSound() {
        // get shared prefs and set states accordingly
        SharedPreferences sharedPreferences = getSharedPreferences(SOUND_STATE,MODE_PRIVATE);
        sharedPreferences.getString(SOUND_STATE, PREFERRENCE_SOUND_ON);
        SharedPreferences toggle = getSharedPreferences(TOGGLE_STATE,MODE_PRIVATE);
        switchButton.setChecked(toggle.getBoolean(TOGGLE_STATE, false));
        if(switchButton.isChecked()){
            imageViewOn.setImageResource(R.drawable.sound_off);
        }
        else
            imageViewOn.setImageResource(R.drawable.sound_on);
    }

    public void onBackPressed() {
            Intent intent = new Intent(Settings.this,CategoryActivity.class);
            startActivity(intent);
    }
}
