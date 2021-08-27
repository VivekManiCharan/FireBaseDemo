package com.charan.firebasedemo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginPage extends AppCompatActivity {

    EditText email, Password;
    TextView CreateBtn;
    Button loginBtn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.loginPassword);
        progressBar = findViewById(R.id.loginprogressBar);
        firebaseAuth = FirebaseAuth.getInstance();
        loginBtn = findViewById(R.id.LoginButton);
        CreateBtn = findViewById(R.id.RegisterText);

        if(firebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jEmail =  email.getText().toString();
                String jPassword = Password.getText().toString();

                if(TextUtils.isEmpty(jEmail)){
                    email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(jPassword)){
                    Password.setError("Password is Required.");
                    return;
                }

                if(jPassword.length()<6){
                    Password.setError("Password length must be > 6.");
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(jEmail,jPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(loginPage.this, "Log In Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Toast.makeText(loginPage.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterPage.class));
            }
        });

    }
}