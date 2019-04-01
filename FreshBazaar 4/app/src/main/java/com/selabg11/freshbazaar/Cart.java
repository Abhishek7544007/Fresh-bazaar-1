package com.selabg11.freshbazaar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.selabg11.freshbazaar.ViewHolder.cartviewholder;

public class Cart extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button NextProcessButton;
    private TextView txtTotalAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        recyclerView=findViewById(R.id.cartlist);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        NextProcessButton=(Button)findViewById(R.id.next);
        txtTotalAmount=(TextView)findViewById(R.id.totalprice);
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartref= FirebaseDatabase.getInstance().getReference("Cart");

        FirebaseRecyclerOptions<cart_model> options=new FirebaseRecyclerOptions.Builder<cart_model>()
                .setQuery(cartref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Product"), cart_model.class).build();

FirebaseRecyclerAdapter<cart_model, cartviewholder> adapter=new FirebaseRecyclerAdapter<cart_model, cartviewholder>(options) {
    @Override
    protected void onBindViewHolder(@NonNull cartviewholder holder, int position, @NonNull cart_model model) {


        holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
        holder.txtProductPrice.setText("Price = " + model.getPrice());
        holder.txtProductName.setText(model.getPname());
    }

    @NonNull
    @Override
    public cartviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitemslayout, parent, false);
        cartviewholder holder;
        holder = new cartviewholder(view);
        return holder;
    }
};

recyclerView.setAdapter(adapter);
adapter.startListening();
    }





}
