package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    private EditText resetEmail;
    private Button btnReset;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        resetEmail = (EditText) findViewById(R.id.editTextResetEmail);
        btnReset = (Button) findViewById(R.id.resetButton);
        progressBar= (ProgressBar)findViewById(R.id.resetLogBar);
        mAuth = FirebaseAuth.getInstance();
        
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = resetEmail.getText().toString().trim();

        if(email.isEmpty()){
            resetEmail.setError("Email is required");
            resetEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetEmail.setError("Provide valid email");
            resetEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(forgotPassword.this, "Check your email to reset password.", Toast.LENGTH_SHORT).show();

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(forgotPassword.this, "Email entered does not exist!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}