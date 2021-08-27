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

public class RegisterPage extends AppCompatActivity {

    EditText fullname, email, password, phone;
    Button registerBtn;
    TextView loginTextBtn;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        fullname = findViewById(R.id.editTextUserNameR);
        email = findViewById(R.id.editTextEmailAddressR);
        password = findViewById(R.id.RegisterPasswordR);
        phone = findViewById(R.id.editTextPhoneR);
        registerBtn = findViewById(R.id.RegisterButtonR);
        loginTextBtn = findViewById(R.id.LoginText);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.RprogressBar);

        if(firebaseAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String jEmail =  email.getText().toString();
                String jPassword = password.getText().toString();

                if(TextUtils.isEmpty(jEmail)){
                    email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(jPassword)){
                    password.setError("Password is Required.");
                    return;
                }

                if(jPassword.length()<6){
                    password.setError("Password length must be > 6.");
                    return;

                }

                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(jEmail,jPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterPage.this, "Account Created Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else {
                            Toast.makeText(RegisterPage.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            }
        });


        loginTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),loginPage.class));
            }
        });
    }
}