package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
    public static final float END_SCALE = 0.7f;
    private LevelsDialog levelsDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        btHistory = findViewById(R.id.bt_hist_start);
        btProg = findViewById(R.id.bt_prog_start);
        btTech = findViewById(R.id.bt_tech_start);

        btHistory.setOnClickListener(this);
        btProg.setOnClickListener(this);
        btTech.setOnClickListener(this);

        navigationView = findViewById(R.id.nav_view);
        linearLayout = findViewById(R.id.category_linear);

        levelsDialog = new LevelsDialog(this);

        toolbar  = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);// set tool bar as an action bar in the activity

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState(); // sync the drawer state while toolbar pressed

        navigationDrawer(); // init the drawer

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //show levels dialog ans pass the theme
            case R.id.bt_hist_start:
                levelsDialog.LevelsDialog(Constants.HISTORY);
                break;

            case R.id.bt_tech_start:
                levelsDialog.LevelsDialog(Constants.TECHNOLOGY);
                break;

            case R.id.bt_prog_start:
                levelsDialog.LevelsDialog(Constants.PROGRAMMING);
                break;

        }

    } // close the drawer while closing the activity
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            Intent intent = new Intent(CategoryActivity.this, PlayActivity.class);
            startActivity(intent);
        }
    }

    // drawer items actions
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
            super.onBackPressed();
        }
        else if(item.getItemId() == R.id.nav_profile){
            startActivity(new Intent(CategoryActivity.this, UserProfileActivity.class));
        }
        else if(item.getItemId() == R.id.nav_about){
            startActivity(new Intent(this,AboutActivity.class));
        }
        return true;
    }
    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        toolbar.setOnClickListener(v -> { // open the drawer while tool bar is pressed or close it if it's already open
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
