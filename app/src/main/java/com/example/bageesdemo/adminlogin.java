
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bageesdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adminlogin extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView admregister, admforgotPass;
    EditText admEmail,admPass;
    Button adminLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        mAuth = FirebaseAuth.getInstance();
        admforgotPass = (TextView) findViewById(R.id.admForgot);
        admforgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), forgotPassword.class));
            }
        });
        admregister = (TextView) findViewById(R.id.admRegister);
        admregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), adminregister.class));
            }
        });

        admEmail = (EditText)findViewById(R.id.txtAdminEmail);
        admPass = (EditText)findViewById(R.id.txtAdminPassword);
        progressBar = (ProgressBar)findViewById(R.id.admLogBar);
        adminLogin = (Button)findViewById(R.id.btnAdminLogin);

        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = admEmail.getText().toString().trim();
                String password = admPass.getText().toString().trim();

                if(email.isEmpty()){
                    admEmail.setError("Email is required");
                    admEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    admEmail.setError("Provide valid email");
                    admEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    admPass.setError("Password is required");
                    admPass.requestFocus();
                    return;
                }

                if(password.length() < 6 ){
                    admPass.setError("Minimum length should be 6 characters");
                    admPass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            progressBar.setVisibility(View.GONE);
                            // redirects to profile activity
                            startActivity(new Intent(adminlogin.this,adminhome.class));

                        }
                        else{
                            Toast.makeText(adminlogin.this, "Failed to login! Try again.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

    }

}