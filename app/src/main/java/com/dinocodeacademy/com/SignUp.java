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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    protected EditText et_Email,et_Password;
    private EditText et_FullName, et_UserName;
    protected ProgressBar progressBar;
    protected FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://quiz-project-6afd9-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress);
        if(mAuth.getCurrentUser()!= null){
            startActivity(new Intent(SignUp.this, SplashScreen.class));
        }

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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    assert currentUser != null;
                    String Email = currentUser.getEmail();
                    String uid = currentUser.getUid();

                    User user = new User(fullName,userName,Email);

                    databaseReference.child(uid).setValue(user);
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(SignUp.this, "verification email sent to " + email, Toast.LENGTH_LONG).show();
                                Intent emailVerify = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(emailVerify);

                            } else {
                                Toast.makeText(SignUp.this, "Check Internet Connection" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, "Check Internet Connection" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
                }
            });
    }
}