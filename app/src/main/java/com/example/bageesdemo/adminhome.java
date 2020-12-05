package com.example.bageesdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class adminhome extends AppCompatActivity {
    TextView txtComputerSys, txtComponents, txtGaming, txtHeadphones;
    Button checkOrders, logout,editProducts,viewProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        txtComputerSys = (TextView)findViewById(R.id.txtComputer);
        txtComponents = (TextView)findViewById(R.id.txtComponents);
        txtGaming = (TextView)findViewById(R.id.txtGaming);
        txtHeadphones = (TextView)findViewById(R.id.txtHeadphones);
        checkOrders = (Button) findViewById(R.id.btn_admin_home_check_order) ;
        logout = (Button) findViewById(R.id.btn_admin_home_logout);
        editProducts = (Button) findViewById(R.id.btn_admin_home_edit_prodcuts);
        viewProducts = (Button) findViewById(R.id.btn_admin_view_products);

        viewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adminhome.this,AdminEditProducts.class));
                finish();
            }
        });

        editProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adminhome.this,AdminEditProducts.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(adminhome.this,afterSplashScreen.class));
                finish();
            }
        });

        checkOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adminhome.this, AdminOrderCheck.class));
                finish();
            }
        });

        txtComputerSys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminhome.this, adminhome2.class);
                intent.putExtra("category", "ComputerSystems");
                startActivity(intent);

            }
        });

        txtComponents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminhome.this, adminhome2.class);
                intent.putExtra("category", "Components");
                startActivity(intent);
            }
        });

        txtGaming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminhome.this, adminhome2.class);
                intent.putExtra("category", "Gaming");
                startActivity(intent);
            }
        });

        txtHeadphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminhome.this, adminhome2.class);
                intent.putExtra("category", "Headphones");
                startActivity(intent);
            }
        });

    }
}