package com.selabg11.freshbazaar.ProductDisplays;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selabg11.freshbazaar.Items;
import com.selabg11.freshbazaar.R;
import com.squareup.picasso.Picasso;

public class ProductDisplayDairy extends AppCompatActivity {
    private Button addtoCart;
    private ImageView ProductImg;
    private ElegantNumberButton prodCount;
    private TextView ProductName, ProductCost, ProductExpiry, ProductAmt;
    private String productId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display_dairy);

        productId = getIntent().getStringExtra("ItemId");
        addtoCart = findViewById(R.id.add_to_cart);
        ProductImg = findViewById(R.id.product_display_img);
        ProductAmt = findViewById(R.id.product_display_amount);
        ProductCost = findViewById(R.id.product_display_price);
        ProductExpiry = findViewById(R.id.product_display_expiry);
        ProductName = findViewById(R.id.product_display_name);

        getItemDetails(productId);
    }

    private void getItemDetails(String productId) {

        DatabaseReference productref = FirebaseDatabase.getInstance().getReference("Categories").child("Dairy");
        productref.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Items items = dataSnapshot.getValue(Items.class);

                    ProductName.setText(items.getItemName());
                    ProductCost.setText("Price: Rs."+items.getCost()+"per "+items.getUnit());
                    ProductExpiry.setText("Expires on "+items.getExpDate());
                    ProductAmt.setText(items.getAmount()+" "+items.getUnit());
                    Picasso.get().load(items.getImglink()).into(ProductImg);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
