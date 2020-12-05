package com.example.bageesdemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView txtProductName, txtPrice;
    ImageView cartViewImage;

    public cartHolder(@NonNull View itemView) {
        super(itemView);
        txtProductName = itemView.findViewById(R.id.txtCartItemsProduct);
        txtPrice = itemView.findViewById(R.id.txtCartItemsPrice);
        cartViewImage = itemView.findViewById(R.id.img_cart_items_view);
    }

    @Override
    public void onClick(View v) {

    }
}
