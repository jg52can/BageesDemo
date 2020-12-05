package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class confirmationOrderActivity extends AppCompatActivity {
    private EditText nameEditText, addressEditText, phoneEditText, cityEditText, zipEditText;
    private Button btnConfirmOrder;
    private TextView totalPrice;
    private String totalAmount = "";
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_order);

        btnConfirmOrder = (Button) findViewById(R.id.btn_activity_confirmation);
        nameEditText = (EditText) findViewById(R.id.edt_txt_name_confirm);
        addressEditText = (EditText) findViewById(R.id.edt_txt_address_confirm);
        phoneEditText = (EditText) findViewById(R.id.edt_txt_phone_confirm);
        cityEditText = (EditText) findViewById(R.id.edt_txt_city_confirm);
        zipEditText = (EditText) findViewById(R.id.edt_txt_zip_confirm);
        totalPrice = (TextView) findViewById(R.id.txt_total_amount_confirm);
        totalAmount = getIntent().getStringExtra("total amount");
        user = FirebaseAuth.getInstance().getCurrentUser();

        totalPrice.setText("Total: $"+totalAmount);

        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString())){
            nameEditText.setError("Fullname is required");
            nameEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(addressEditText.getText().toString())){
            addressEditText.setError("Address is required");
            addressEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(phoneEditText.getText().toString())){
            phoneEditText.setError("Phone number is required");
            phoneEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(cityEditText.getText().toString())){
            cityEditText.setError("City name is required");
            cityEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(zipEditText.getText().toString())){
            zipEditText.setError("Zip is required");
            zipEditText.requestFocus();
            return;
        }

        confirmOrder();
    }

    private void confirmOrder() {
        String scurrentDate, scurrentTime;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM, dd, yyyy");
        scurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        scurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(user.getUid());

        final HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", nameEditText.getText().toString());
        ordersMap.put("address", addressEditText.getText().toString());
        ordersMap.put("city", cityEditText.getText().toString());
        ordersMap.put("zip", zipEditText.getText().toString());
        ordersMap.put("phone", phoneEditText.getText().toString());
        ordersMap.put("date", scurrentDate);
        ordersMap.put("time", scurrentTime);
        ordersMap.put("status", "Not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View")
                            .child(user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(confirmationOrderActivity.this, "Your order has been placed successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(confirmationOrderActivity.this, MainActivity2.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}