package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends LoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        Button bt_resetPassword = findViewById(R.id.reset_password_btn);
        et_Email = findViewById(R.id.reset_email);
        TextView backToLogin = findViewById(R.id.to_login_tv);
        progressBar = findViewById(R.id.progress);

        mAuth = FirebaseAuth.getInstance();

        bt_resetPassword.setOnClickListener(v -> resetPassword());

        backToLogin.setOnClickListener(v -> startActivity(new Intent(ForgotPassword.this, LoginActivity.class)));
    }

    private void resetPassword() {
        String email = et_Email.getText().toString().trim();
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
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ForgotPassword.this,
                        "Check your email to reset your password",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(ForgotPassword.this,
                        "Something went wrong, try again",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
    }
}