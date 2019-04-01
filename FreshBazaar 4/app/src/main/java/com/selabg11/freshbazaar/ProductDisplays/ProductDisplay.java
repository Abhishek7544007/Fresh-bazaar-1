package com.selabg11.freshbazaar.ProductDisplays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.selabg11.freshbazaar.Items;
import com.selabg11.freshbazaar.R;
import com.selabg11.freshbazaar.Users;
import com.selabg11.freshbazaar.homepage2;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDisplay extends AppCompatActivity {
    private Button addtoCart;
    private ImageView ProductImg;
    private ElegantNumberButton prodCount;
    private TextView ProductName, ProductCost, ProductExpiry, ProductAmt;
    private String productId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);

        productId = getIntent().getStringExtra("ItemId");
        addtoCart = findViewById(R.id.add_to_cart);
        ProductImg = findViewById(R.id.product_display_img);
        ProductAmt = findViewById(R.id.product_display_amount);
        ProductCost = findViewById(R.id.product_display_price);
        ProductExpiry = findViewById(R.id.product_display_expiry);
        ProductName = findViewById(R.id.product_display_name);
        prodCount=findViewById(R.id.number_btn);

        getItemDetails(productId);

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingtocart();
            }
        });

    }
    private void addingtocart(){
        String savecurrentdate, savecurrenttime;
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentdate=new SimpleDateFormat("MM DD, YYYY");

        savecurrentdate=currentdate.format(calForDate.getTime());
        SimpleDateFormat currenttime=new SimpleDateFormat("HH MM SS");
        savecurrenttime=currenttime.format(calForDate.getTime());
        DatabaseReference cartref=FirebaseDatabase.getInstance().getReference().child("Cart");

        final HashMap<String, Object> cartmap=new HashMap<>();
        cartmap.put("PID", productId);
        cartmap.put("Pname", ProductName.getText().toString());
        cartmap.put("Price", ProductCost.getText().toString());
        cartmap.put("Date", savecurrentdate);
        cartmap.put("Time", savecurrenttime);
        cartmap.put("Quantity", prodCount.getNumber());
        DataSnapshot datasnam;

        cartref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Product")
                .child(productId).updateChildren(cartmap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ProductDisplay.this,"Added to cart",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProductDisplay.this, homepage2.class));
                }
            }
        });

    }

    private void getItemDetails(String productId) {

        DatabaseReference productref = FirebaseDatabase.getInstance().getReference("Categories").child("Vegetables");
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
