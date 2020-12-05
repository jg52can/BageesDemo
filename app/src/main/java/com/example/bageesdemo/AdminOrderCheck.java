package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminOrderCheck extends AppCompatActivity {
    private RecyclerView ordersList;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_check);
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        ordersList = findViewById(R.id.recycler_admin_order_activity);
        ordersList.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(ordersRef, AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders,AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position, @NonNull AdminOrders model) {
                holder.userName.setText("Name: "+model.getName());
                holder.userAddress.setText("Address: "+model.getAddress());
                holder.userCity.setText("City: "+model.getCity());
                holder.userPhone.setText("Phone: "+model.getPhone());
                holder.userZip.setText("Zip: "+model.getZip());
                holder.userPrice.setText("Total Price: "+model.getTotalAmount());
                holder.userDate.setText("Date: "+model.getDate());
                holder.userTime.setText("Time: "+model.getTime());
            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order_layout, parent, false);
                return new AdminOrdersViewHolder(view);
            }
        };
        ordersList.setAdapter(adapter);
        adapter.startListening();

    }
    public static class AdminOrdersViewHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, userAddress, userPhone, userZip, userPrice, userCity, userDate, userTime;
        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.txt_admin_order_layout_username);
            userAddress = itemView.findViewById(R.id.txt_admin_order_layout_address);
            userPhone = itemView.findViewById(R.id.txt_admin_order_layout_phone);
            userZip = itemView.findViewById(R.id.txt_admin_order_layout_zip);
            userPrice = itemView.findViewById(R.id.txt_admin_order_layout_cost);
            userCity = itemView.findViewById(R.id.txt_admin_order_layout_city);
            userDate = itemView.findViewById(R.id.txt_admin_order_layout_date);
            userTime = itemView.findViewById(R.id.txt_admin_order_layout_time);
        }
    }

}