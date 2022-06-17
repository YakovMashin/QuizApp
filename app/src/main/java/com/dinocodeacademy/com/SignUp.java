package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button bt_signup;
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bt_signup = findViewById(R.id.bt_SignUp);
        tv_login = findViewById(R.id.login_tv);
        bt_signup.setOnClickListener(this);
        tv_login.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.bt_SignUp:

                break;

            case R.id.login_tv:
                Intent i = new Intent(SignUp.this, LoginActivity.class);
                startActivity(i);

                break;

        }
    }
}