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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    protected EditText et_Email,et_Password;
    private EditText et_FullName, et_UserName;
    protected ProgressBar progressBar;
    protected FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress);

        Button bt_signup = findViewById(R.id.bt_SignUp);
        TextView tv_login = findViewById(R.id.login_tv);
        bt_signup.setOnClickListener(this);
        tv_login.setOnClickListener(this);

        et_FullName = findViewById(R.id.reg_FullName);
        et_UserName = findViewById(R.id.reg_UserName);
        et_Email = findViewById(R.id.Email);
        et_Password = findViewById(R.id.Password);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.bt_SignUp:
                registerUser();
                break;

            case R.id.login_tv:
                Intent i = new Intent(SignUp.this, LoginActivity.class);
                startActivity(i);
                break;

        }
    }
    private void registerUser(){
        String email =et_Email.getText().toString().trim();
        String password =et_Password.getText().toString().trim();
        String userName =et_UserName.getText().toString().trim();
        String fullName =et_FullName.getText().toString().trim();

        if(fullName.isEmpty()){
            et_FullName.setError("Please enter your full name");
            et_FullName.requestFocus();
            return;
        }
        if(userName.isEmpty()){
            et_UserName.setError("Please enter a username");
            et_UserName.requestFocus();
            return;
        }
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
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        User user = new User(fullName,userName,email);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){

                                        Toast.makeText(SignUp.this,
                                                "User has been registered succesfully",
                                                Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.VISIBLE);

                                    }else{
                                        Toast.makeText(SignUp.this,
                                                "Failed Registration, try again please",
                                                Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                });
                    }else{
                        Toast.makeText(SignUp.this,
                                "failed registration, wassup",
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.VISIBLE);
                        //startActivity(new Intent(SignUp.this,LoginActivity.class));


                    }
                });

    }
}