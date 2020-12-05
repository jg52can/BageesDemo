package com.example.bageesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class afterSplashScreen extends AppCompatActivity {
    private Button user,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_splash_screen);

        user = (Button) findViewById(R.id.btn_user_after_splash_screen);
        admin = (Button) findViewById(R.id.btn_admin_after_splash_screen);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(afterSplashScreen.this,MainActivity.class));
                finish();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(afterSplashScreen.this,adminlogin.class));
                finish();
            }
        });
    }
}