package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {


    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        Button btPlay = findViewById(R.id.bt_playbutton);
        Button settings = findViewById(R.id.bt_settings_button);

        btPlay.setOnClickListener(v -> {

            Intent intent = new Intent(PlayActivity.this, CategoryActivity.class);
            startActivity(intent);

        });
        settings.setOnClickListener(view -> {
            Intent intent = new Intent(PlayActivity.this, Settings.class );
            startActivity(intent);

        });


    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            new AlertDialog.Builder(this)
                    .setTitle("Do you  want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {

                        setResult(RESULT_OK, new Intent().putExtra("Exit", false));
                        System.exit(0);
                    }).create().show();

        }else  {

            Toast.makeText(this, "Press Again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("BUGBUG","onStop() in PlayActivity");
        finish();


    }
}
