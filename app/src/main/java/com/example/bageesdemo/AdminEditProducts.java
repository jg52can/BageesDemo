package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminEditProducts extends AppCompatActivity {
    TextView edittest,editprdname,editprdprc,editprddecs;
    ImageView imageeditView;
    String ProductID="";
    private static final int gallerypic = 1;
    private Uri imageUri;
    Button btneditproduct,btndeleteproduct;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_products);
        imageeditView = (ImageView)findViewById(R.id.imgEditSelectProduct);
        ProductID = getIntent().getStringExtra("pid");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(ProductID);

        edittest = (TextView)findViewById(R.id.txtEditTest);
        editprdname = (EditText)findViewById(R.id.txtEditProductName);
        editprdprc = (EditText)findViewById(R.id.txtEditProductPrice);
        editprddecs = (EditText)findViewById(R.id.txtEditProductDesc);
        btneditproduct = (Button)findViewById(R.id.btnEditProduct);
        btndeleteproduct = (Button)findViewById(R.id.btnDeleteProduct);

        btndeleteproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
        btneditproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyChanges();
            }
        });

        ProductsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String name = snapshot.child("pname").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();

                    editprdname.setText(name);
                    editprdprc.setText(price);
                    editprddecs.setText(description);
                    Picasso.get().load(image).into(imageeditView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void deleteProduct() {
        ProductsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(AdminEditProducts.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AdminEditProducts.this,adminhome.class));
                finish();
            }
        });
    }

    private void applyChanges() {
        String pNAme = editprdname.getText().toString();
                String pPrice = editprdprc.getText().toString();
                        String pDesc = editprddecs.getText().toString();

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", ProductID);
        productMap.put("description", pDesc);
        productMap.put("price", pPrice);
        productMap.put("pname", pNAme);
        ProductsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(AdminEditProducts.this,adminhome.class));
                    finish();
                }
            }
        });
    }
}