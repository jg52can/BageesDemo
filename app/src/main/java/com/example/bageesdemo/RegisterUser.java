package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText regtxtName,regtxtAge,regtxtEmail,regtxtPass;
    Button btnRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        regtxtName = (EditText) findViewById(R.id.regUserName);
        regtxtAge = (EditText) findViewById(R.id.regUserAge);
        regtxtEmail = (EditText) findViewById(R.id.regUserEmail);
        regtxtPass = (EditText) findViewById(R.id.regUserPassword);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        progressBar = (ProgressBar)findViewById(R.id.proBarRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email = regtxtEmail.getText().toString().trim();
        String password = regtxtPass.getText().toString().trim();
        final String name = regtxtName.getText().toString().trim();
        final String age = regtxtAge.getText().toString().trim();

        if(name.isEmpty()){
            regtxtName.setError("Fullname is required");
            regtxtName.requestFocus();
            return;
        }
        if(age.isEmpty()){

            regtxtAge.setError("Age is required");
            regtxtAge.requestFocus();
            return;
        }
        if(email.isEmpty()){

            regtxtEmail.setError("Email is required");
            regtxtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regtxtEmail.setError("Provide valid email");
            regtxtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){

            regtxtPass.setError("Password is required");
            regtxtPass.requestFocus();
            return;
        }
        if(password.length() < 6 ){
            regtxtPass.setError("Minimum length should be 6 characters");
            regtxtPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    User user = new User(name,age,email);
                    FirebaseDatabase.getInstance().getReference("Users").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                            setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterUser.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                                // redirects to login activity
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else{
                                Toast.makeText(RegisterUser.this, "Failed to register! Try again.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisterUser.this, "Failed to register! Try again.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}