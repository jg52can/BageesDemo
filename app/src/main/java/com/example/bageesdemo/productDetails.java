package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class productDetails extends AppCompatActivity {

    Button btnAddToCart;
    TextView txtProductName,txtProductPrice,txtProductDescription;
    ElegantNumberButton elegantNumberButton;
    String productId="";
    ImageView imgProductDetails;
    FirebaseUser user;
    DatabaseReference reference;
    String fullName;
    String userID;
    private String ImageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User profile = snapshot.getValue(User.class);
                if(profile != null){
                    fullName = profile.name;
                    String email = profile.email;
                    String age = profile.age;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(productDetails.this, "Something went wrong. Restart the app.", Toast.LENGTH_SHORT).show();

            }
        });



        btnAddToCart = (Button) findViewById(R.id.btnAddToCart);



        productId = getIntent().getStringExtra("pid");
        imgProductDetails = (ImageView) findViewById(R.id.imgProductDetails);
        txtProductName = (TextView) findViewById(R.id.txtProductName);
        txtProductPrice = (TextView) findViewById(R.id.txtProductPrice);
        txtProductDescription = (TextView) findViewById(R.id.txtProductDescription);
        elegantNumberButton = (ElegantNumberButton)findViewById(R.id.elegantNumberButton);

        getProductDetails(productId);

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

    }

    public void addToCart() {
    String scurrentDate, scurrentTime;

    Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM, dd, yyyy");
        scurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        scurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productId);
        cartMap.put("username", fullName);
        cartMap.put("productname", txtProductName.getText().toString());
        cartMap.put("price", txtProductPrice.getText().toString());
        cartMap.put("date", scurrentDate);
        cartMap.put("time", scurrentTime);
        cartMap.put("image", ImageID);

        cartListRef.child("User View").child(userID).child("Products").child(productId).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartListRef.child("Admin View").child(userID).child("Products").child(productId)
                            .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(productDetails.this, "Added to cart list", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(productDetails.this, MainActivity2.class));
                            }
                        }
                    });

                }

            }
        });
    }

    public void getProductDetails(final String productId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");
        reference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Product product = snapshot.getValue(Product.class);
                    if(product!=null) {

                        txtProductName.setText(product.getPname());
                        txtProductPrice.setText(product.getPrice());
                        txtProductDescription.setText(product.getDescription());
                        Picasso.get().load(product.getImage()).into(imgProductDetails);
                        ImageID=product.getImage();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}