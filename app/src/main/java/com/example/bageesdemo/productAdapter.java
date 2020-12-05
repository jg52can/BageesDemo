package com.example.bageesdemo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class productAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView prdName, prdPrice;
    ImageView imgProduct;
    itemCLickListener listener;
    CardView cardView;
    public productAdapter(@NonNull View itemView) {
        super(itemView);

        imgProduct = (ImageView) itemView.findViewById(R.id.prdImage);
        prdName = (TextView) itemView.findViewById(R.id.txtPrdItemName);
        prdPrice = (TextView) itemView.findViewById(R.id.txtPrdItemPrice);
        cardView = (CardView)itemView.findViewById(R.id.cardView1);
    }
 public void setItemClickListener(itemCLickListener listener){
        this.listener= listener;
 }
    @Override
    public void onClick(View v) {
      listener.onClick(v, getAdapterPosition(), false);
    }
}
