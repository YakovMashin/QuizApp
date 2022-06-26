package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class UserProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final float END_SCALE = 0.7f;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button navToggler_btn, back_btn, bt_changePassword;
    LinearLayout linearLayout;
    EditText et_Email, et_UserName, et_FullName;
    FirebaseAuth firebaseAuth;
    private TextView yourEmail, yourUserName;



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
        navToggler_btn = findViewById(R.id.action_menu_presenter);
        linearLayout = findViewById(R.id.main_content);
        et_Email = findViewById(R.id.EMail);
        et_UserName = findViewById(R.id.USername);
        et_FullName = findViewById(R.id.fullName);
        back_btn = findViewById(R.id.prof_back_btn);
        bt_changePassword = findViewById(R.id.bt_change_password);

        yourEmail = findViewById(R.id.header_email);
        yourUserName = findViewById(R.id.header_userName);

        //Init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://quiz-project-6afd9-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        assert user != null;
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //if() {
                        String fullname = "" + ds.child("fullName").getValue().toString().trim();
                        String username = "" + ds.child("userName").getValue().toString().trim();
                        String email = "" + ds.child("email").getValue().toString().trim();

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

        bt_changePassword.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(this,ForgotPassword.class));
            finishAffinity();
            finish();

        });

    }
    private void navigationDrawer() {

        //Navigation Drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_profile);

        navToggler_btn.setOnClickListener(v -> {

            if (drawerLayout.isDrawerVisible(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else drawerLayout.openDrawer(GravityCompat.START);
        });

        animateNavigationDrawer();

    }

    ////////////////////////////////////////////////////////////ANIMATE NAV DRAWER////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void animateNavigationDrawer() {
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
