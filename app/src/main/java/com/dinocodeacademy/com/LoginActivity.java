package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends SignUp implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_Email = findViewById(R.id.log_email);
        et_Password = findViewById(R.id.log_password);
        TextView forgot = findViewById(R.id.forgot_tv);

        Button login = findViewById(R.id.go_btn);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress);

        forgot.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_btn:
                userLogin();
                break;

            case R.id.forgot_tv:
                Intent i = new Intent(LoginActivity.this,ForgotPassword.class);
                startActivity(i);
                break;


        }
    }

    private void userLogin() {
        // validation
        String email = et_Email.getText().toString().trim();
        String password = et_Password.getText().toString().trim();

        if(password.isEmpty()){
            et_Password.setError("Please enter a password");
            et_Password.requestFocus();
            return;
        }
        if(password.length() < 6){
            et_Password.setError("Password should be longer than 6 characters");
            et_Password.requestFocus();
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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                if(user.isEmailVerified()) { // if user already verified log him
                    progressBar.setVisibility(View.GONE);
                    finishAffinity(); // remove from the stack
                    Intent i = new Intent(LoginActivity.this, SplashScreen.class);
                    startActivity(i);
                }
                else{ // else send a verification
                    Toast.makeText(LoginActivity.this,
                            "check you inbox for mail verification",
                            Toast.LENGTH_LONG).show();
                    mAuth.getCurrentUser().sendEmailVerification();
                }
            }
            else{  Toast.makeText(LoginActivity.this,
                    "something went wrong, please check your credentials",
                    Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this,SignUp.class));
    }
}