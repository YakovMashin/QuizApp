package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private Toolbar toolbar;
    Button back_btn,bt_apply_changes;
    TextView  tv_changePassword;
    LinearLayout linearLayout;
    EditText et_Email, et_UserName, et_FullName;

    FirebaseAuth firebaseAuth;
    private String fullname, username, email;
    private long pressedTime;
    FirebaseUser user;



    ///////////////////////////////////////////////////////////////////////////////////// SET UP BG MUSIC  ///////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); ///Eneter into fullscreen mode
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        linearLayout = findViewById(R.id.main_content);
        et_Email = findViewById(R.id.EMail);
        et_UserName = findViewById(R.id.USername);
        et_FullName = findViewById(R.id.fullName);
        back_btn = findViewById(R.id.prof_back_btn);
        tv_changePassword = findViewById(R.id.tv_change_password);
        bt_apply_changes = findViewById(R.id.bt_apply_changes);

        toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        //Init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://quiz-project-6afd9-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

      // assert user != null;
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //if() {
                         fullname = "" + Objects.requireNonNull(ds.child("fullName").getValue()).toString().trim();
                         username = "" + Objects.requireNonNull(ds.child("userName").getValue()).toString().trim();
                         email = "" + Objects.requireNonNull(ds.child("email").getValue()).toString().trim();



                        //set data
                        et_FullName.setText(fullname);
                        et_UserName.setText(username);
                        et_Email.setText(email);
                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        navigationDrawer();


        back_btn.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), CategoryActivity.class);
            startActivity(intent);
        });

        tv_changePassword.setOnClickListener(v -> {
            if (pressedTime + 2000 > System.currentTimeMillis()) {

                new AlertDialog.Builder(this)
                        .setTitle("You will be signed out and redirected")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", (dialog, which) -> {

                            setResult(RESULT_OK, new Intent().putExtra("Exit", true));
                            //Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
                            firebaseAuth.signOut();
                            startActivity(new Intent(this, ForgotPassword.class));

                        }).create().show();
            }else  {
                Toast.makeText(this, "Press Again to change password", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();

        });
        bt_apply_changes.setOnClickListener(v -> {
            String Email =et_Email.getText().toString().trim();// eliminate extra spaces
            String userName =et_UserName.getText().toString().trim();
            String fullName =et_FullName.getText().toString().trim();

            if(fullName.isEmpty()){
                et_FullName.setError("Please enter your name");
                et_FullName.requestFocus();
                return;
            }

            if(userName.isEmpty()){
                et_UserName.setError("Please enter a username");
                et_UserName.requestFocus();
                return;
            }
            if(email.isEmpty()){
                et_Email.setError("Please enter your email");
                et_Email.requestFocus();
                return;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                et_Email.setError("Please provide a valid email");
                et_Email.requestFocus();
            }
            if(!emailChanged() && !userNameChanged() && !nameChanged())
                Toast.makeText(UserProfileActivity.this, "The data is the same", Toast.LENGTH_LONG).show();

            else {
                if (userNameChanged()) {
                    databaseReference.child(user.getUid()).child("userName").setValue(userName);
                    Toast.makeText(UserProfileActivity.this, "your username has been updated", Toast.LENGTH_LONG).show();
                }
                if (nameChanged()) {
                    databaseReference.child(user.getUid()).child("fullName").setValue(fullName);
                    Toast.makeText(UserProfileActivity.this, "your name has been updated", Toast.LENGTH_LONG).show();
                }
                if (emailChanged()) {
                    databaseReference.child(user.getUid()).child("email").setValue(Email);
                    Toast.makeText(UserProfileActivity.this, "please confirm new email and Login", Toast.LENGTH_LONG).show();
                    firebaseAuth.signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                }
            }
        });


    }

    private boolean userNameChanged() {
        return !username.equals(et_UserName.getText().toString().trim());

    }

    private boolean emailChanged() {
        return !email.equals(et_Email.getText().toString().trim());

    }

    private boolean nameChanged(){
        return !fullname.equals(et_FullName.getText().toString().trim());
    }

    private void navigationDrawer() {

        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        toolbar.setOnClickListener(v -> {

            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        });

        animateNavigationDrawer();

    }

    ////////////////////////////////////////////////////////////ANIMATE NAV DRAWER////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.cat_heading));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                linearLayout.setScaleX(offsetScale);
                linearLayout.setScaleY(offsetScale);


                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = linearLayout.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                linearLayout.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.nav_home) {
            Intent home = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(home);
            UserProfileActivity.super.onBackPressed();

        } else if (menuItem.getItemId() == R.id.nav_settings) {

            startActivity(new Intent(this, Settings.class));

        }  else if (menuItem.getItemId() == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finishAffinity();
            finish();

            super.onBackPressed();
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
