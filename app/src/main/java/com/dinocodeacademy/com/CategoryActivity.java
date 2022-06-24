package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    Button btHistory, btProg, btTech;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    LinearLayout linearLayout;
    static final float END_SCALE = 0.7f;

    private FirebaseAuth firebaseAuth;
    private TextView yourEmail, yourUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        yourEmail = findViewById(R.id.header_email);
        yourUserName = findViewById(R.id.header_userName);

        btHistory = findViewById(R.id.bt_hist_start);
        btProg = findViewById(R.id.bt_prog_start);
        btTech = findViewById(R.id.bt_tech_start);

        btHistory.setOnClickListener(this);
        btProg.setOnClickListener(this);
        btTech.setOnClickListener(this);

        navigationView = findViewById(R.id.nav_view);
        linearLayout = findViewById(R.id.category_linear);

        //navToggler_btn = findViewById(R.id.action_menu_presenter);

        toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationDrawer();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bt_hist_start:
                Intent intentHistory = new Intent(CategoryActivity.this, LevelsActivity.class);
                intentHistory.putExtra("Category", Constants.HISTORY);
                startActivity(intentHistory);
                break;

            case R.id.bt_tech_start:
                Intent intentTech = new Intent(CategoryActivity.this, LevelsActivity.class);
                intentTech.putExtra("Category", Constants.TECHNOLOGY);
                startActivity(intentTech);

                break;

            case R.id.bt_prog_start:
                Intent intentProg = new Intent(CategoryActivity.this, LevelsActivity.class);
                intentProg.putExtra("Category", Constants.PROGRAMMING);
                startActivity(intentProg);
                break;
            case R.id.drawer_layout:


        }

    }
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            Intent intent = new Intent(CategoryActivity.this, PlayActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home) {
            Intent home = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(home);
            CategoryActivity.super.onBackPressed();

        } else if (item.getItemId() == R.id.nav_settings) {

            startActivity(new Intent(this, Settings.class));

        }  else if (item.getItemId() == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finishAffinity();
            finish();
            super.onBackPressed();
        }
        else if(item.getItemId() == R.id.nav_profile){
            startActivity(new Intent(CategoryActivity.this, UserProfileActivity.class));
        }
        return true;
    }
    private void navigationDrawer() {
        /*firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://quiz-project-6afd9-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        assert user != null;
        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String username = "" + ds.child("userName").getValue();
                    String email = "" + ds.child("email").getValue();

                    //set data
                    yourEmail.setText(email);
                    yourUserName.setText(username);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        toolbar.setOnClickListener(v -> {


            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {drawer.openDrawer(GravityCompat.START);}
        });


        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawer.setScrimColor(getResources().getColor(R.color.cat_heading));
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
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
}
