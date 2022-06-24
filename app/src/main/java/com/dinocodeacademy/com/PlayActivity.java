package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlayActivity extends AppCompatActivity {


    private long backPressedTime;
    private FirebaseUser user;

    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        TextView helloUser = findViewById(R.id.user_tv);


        Button btPlay = findViewById(R.id.bt_playbutton);

        btPlay.setOnClickListener(v -> {

            Intent intent = new Intent(PlayActivity.this, CategoryActivity.class);
            startActivity(intent);

        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://quiz-project-6afd9-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        userID = user.getUid();

        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userName = snapshot.getValue(User.class);

                if(userName !=null){
                    String fullname = userName.fullName;

                    helloUser.setText("Hey " + fullname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlayActivity.this, "Something went wrong :( ", Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {

            new AlertDialog.Builder(this)
                    .setTitle("Do you  want to exit?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {

                        setResult(RESULT_OK, new Intent().putExtra("Exit", true));
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
