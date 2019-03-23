package com.example.freshbazaar3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.freshbazaar3.ProductDisplays.ProductDisplayVegetables;
import com.example.freshbazaar3.ProductDisplays.ProductDisplayDairy;
import com.example.freshbazaar3.ViewHolder.ProductViewHolder;
import com.squareup.picasso.Picasso;

public class SearchProducts extends AppCompatActivity {

    private Button SearchBtn;
    private EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        inputText = findViewById(R.id.search_product_name);
        SearchBtn = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchProducts.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput= inputText.getText().toString();

                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Categories").child("Vegetables");

        FirebaseRecyclerOptions<Items> options=
                 new FirebaseRecyclerOptions.Builder<Items>()
                .setQuery(reference.orderByChild("itemName").startAt(SearchInput),Items.class)
                .build();

        FirebaseRecyclerAdapter<Items, ProductViewHolder > adapter =
                new FirebaseRecyclerAdapter<Items, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Items model) {


                        holder.txtProductName.setText(model.getItemName());
                        holder.txtProductInformation.setText(model.getExpDate());
                        holder.txtProductPrice.setText("Price = " +model.getCost());
                        Picasso.get().load(model.getImglink()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SearchProducts.this, ProductDisplayVegetables.class);
                                intent.putExtra("ItemId",model.getItemId());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder= new ProductViewHolder(view);
                        return holder;
                    }
                };

        searchList.setAdapter(adapter);
        adapter.startListening();

        ////


        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Categories").child("Dairy");

        FirebaseRecyclerOptions<Items> options2=
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(reference2.orderByChild("itemName").startAt(SearchInput),Items.class)
                        .build();

        FirebaseRecyclerAdapter<Items, ProductViewHolder > adapter2 =
                new FirebaseRecyclerAdapter<Items, ProductViewHolder>(options2) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Items model) {


                        holder.txtProductName.setText(model.getItemName());
                        holder.txtProductInformation.setText(model.getExpDate());
                        holder.txtProductPrice.setText("Price = " +model.getCost());
                        Picasso.get().load(model.getImglink()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SearchProducts.this, ProductDisplayDairy.class);
                                intent.putExtra("ItemId",model.getItemId());
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder= new ProductViewHolder(view);
                        return holder;
                    }
                };

        searchList.setAdapter(adapter2);
        adapter2.startListening();
    }
}
