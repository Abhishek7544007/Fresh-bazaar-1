package com.example.freshbazaar3.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.freshbazaar3.Items;
import com.example.freshbazaar3.ProductDisplays.ProductDisplayFruit;
import com.example.freshbazaar3.R;
import com.example.freshbazaar3.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class Fruit extends AppCompatActivity {
    DatabaseReference ProductRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ProductRef = FirebaseDatabase.getInstance().getReference("Categories").child("Fruit");
    }

    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Items> options=
                new FirebaseRecyclerOptions.Builder<Items>().setQuery(ProductRef, Items.class).build();
        FirebaseRecyclerAdapter<Items, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Items, ProductViewHolder>(options ) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Items model) {

                        holder.txtProductName.setText(model.getItemName());
                        holder.txtProductInformation.setText(model.getExpDate());
                        holder.txtProductPrice.setText("Price = " +model.getCost());
                        Picasso.get().load(model.getImglink()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Fruit.this, ProductDisplayFruit.class);
                                intent.putExtra("ItemId",model.getItemId());
                                startActivity(intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder= new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
