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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView register, forgotPass, isAdmin;
    EditText userEmail,userPass;
    Button userLogin;
    private ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        forgotPass = (TextView) findViewById(R.id.userForgot);
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), forgotPassword.class));
            }
        });

        register = (TextView) findViewById(R.id.userRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), RegisterUser.class));
            }
        });
        isAdmin = (TextView) findViewById(R.id.txtIsAdmin);
        isAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,adminlogin.class));
            }
        });
        userEmail = (EditText)findViewById(R.id.userEmail);
        userPass = (EditText)findViewById(R.id.userPassword);
        progressBar = (ProgressBar)findViewById(R.id.userLogBar);
        userLogin = (Button)findViewById(R.id.userLogin);
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String password = userPass.getText().toString().trim();

                if(email.isEmpty()){
                    userEmail.setError("Email is required");
                    userEmail.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.setError("Provide valid email");
                    userEmail.requestFocus();
                    return;
                }

                if(password.isEmpty()){
                    userPass.setError("Password is required");
                    userPass.requestFocus();
                    return;
                }

                if(password.length() < 6 ){
                    userPass.setError("Minimum length should be 6 characters");
                    userPass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                                progressBar.setVisibility(View.GONE);
                                // redirects to profile activity
                                startActivity(new Intent(MainActivity.this,MainActivity2.class));

                        }
                        else{
                            Toast.makeText(MainActivity.this, "Failed to login! Try again.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }

}