package com.example.bageesdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class cartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnBuy;
    private TextView txtTotalPrice;
    FirebaseUser user;
    private int totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerCartActivity);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        btnBuy = (Button) findViewById(R.id.btnCartActivity);
        txtTotalPrice = (TextView) findViewById(R.id.txtCartActivity);


         btnBuy.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent intent = new Intent(cartActivity.this, confirmationOrderActivity.class);
                 intent.putExtra("total amount", String.valueOf(totalAmount));
                 startActivity(intent);
                 finish();
             }
         });

        user = FirebaseAuth.getInstance().getCurrentUser();

    }
    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View")
                .child(user.getUid()).child("Products"),Cart.class).build();

        FirebaseRecyclerAdapter<Cart, cartHolder> adapter = new FirebaseRecyclerAdapter<Cart, cartHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull cartHolder holder, int position, @NonNull final Cart model) {
                holder.txtPrice.setText(model.getPrice());
                holder.txtProductName.setText(model.getProductname());
                Picasso.get().load(model.getImage()).into(holder.cartViewImage);

                int oneTypeProductPrice = Integer.valueOf(model.getPrice());
                totalAmount = oneTypeProductPrice +totalAmount;
                txtTotalPrice.setText("Total price: $"+String.valueOf(totalAmount));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]{
                                "Edit","Remove",
                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(cartActivity.this);
                        builder.setTitle("Cart Options: ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i == 0){
                                    Intent intent = new Intent(cartActivity.this, productDetails.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);

                                }

                                if(i == 1){
                                    cartListRef.child("User View").child(user.getUid()).child("Products")
                                            .child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(cartActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(cartActivity.this, MainActivity2.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });

                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public cartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_view, parent,false);
                cartHolder holder = new cartHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
    private void OrderStatus(){
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(user.getUid());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            String State = snapshot.child("state").getValue().toString();
            String UName =  snapshot.child("name").getValue().toString();

            if(State.equals("shipped")){

            }
            else{}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}