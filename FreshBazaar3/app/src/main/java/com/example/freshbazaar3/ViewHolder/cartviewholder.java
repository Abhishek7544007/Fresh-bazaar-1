package com.example.freshbazaar3.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.freshbazaar3.Interface.ItemClickListner;
import com.example.freshbazaar3.R;

public class cartviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice, txtProductQuantity;
    private ItemClickListner itemClickListner;

    public cartviewholder(View itemView)
    {
        super(itemView);
        txtProductName=itemView.findViewById(R.id.cartprodname);
        txtProductPrice=itemView.findViewById(R.id.cartprodprice);
        txtProductQuantity=itemView.findViewById(R.id.cartprodquantity);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(),false);

    }

    public void setItemClickListner(ItemClickListner itemClickListner) {
        this.itemClickListner = itemClickListner ;
    }
}
