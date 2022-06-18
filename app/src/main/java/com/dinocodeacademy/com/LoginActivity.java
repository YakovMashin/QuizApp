package com.dinocodeacademy.com;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_Email,et_Password;
    private Button Login;
    private TextView forgot;

    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_Email = findViewById(R.id.log_email);
        et_Password = findViewById(R.id.log_password);
        forgot = findViewById(R.id.forgot_tv);

        Login = findViewById(R.id.go_btn);

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progress);

        forgot.setOnClickListener(this);
        Login.setOnClickListener(this);
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
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        Intent i = new Intent(LoginActivity.this,SplashScreen.class);
                        startActivity(i);
                    }else{
                        progressBar.setVisibility(View.GONE);
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,
                                "Check your email to verify your account",
                                Toast.LENGTH_LONG).show();

                    }


                }
                else{
                    Toast.makeText(LoginActivity.this,
                            "Failed to log in, please check your data",
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LoginActivity.this,SignUp.class));
    }
}