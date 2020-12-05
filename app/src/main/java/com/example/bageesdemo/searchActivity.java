package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class searchActivity extends AppCompatActivity {
    private Button searchButton;
    private EditText searchText;
    private RecyclerView recyclerSearch;
    private String searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = (Button) findViewById(R.id.btn_Search);
        searchText = (EditText) findViewById(R.id.txt_search_Product);
        recyclerSearch = findViewById(R.id.recy_search);
        recyclerSearch.setLayoutManager(new LinearLayoutManager(searchActivity.this));

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInput = searchText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Products");

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder<Product>().
                setQuery(reference.orderByChild("pname").startAt(searchInput), Product.class).build();
        FirebaseRecyclerAdapter<Product, productAdapter> adapter = new FirebaseRecyclerAdapter<Product, productAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull productAdapter holder, int position, @NonNull final Product model) {
                holder.prdName.setText(model.getPname());
                holder.prdPrice.setText(model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imgProduct);
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(searchActivity.this, productDetails.class);
                        intent.putExtra("pid",model.getPid());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public productAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitemlayout, parent,false);
                productAdapter holder = new productAdapter(view);
                return holder;
            }
        };

        recyclerSearch.setAdapter(adapter);
        adapter.startListening();
    }
}