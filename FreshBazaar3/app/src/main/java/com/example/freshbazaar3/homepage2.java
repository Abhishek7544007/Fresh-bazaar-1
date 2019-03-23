package com.example.freshbazaar3;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.example.freshbazaar3.Categories.Cereals;
import com.example.freshbazaar3.Categories.Dairy;
import com.example.freshbazaar3.Categories.Eggs;
import com.example.freshbazaar3.Categories.Fruit;
import com.example.freshbazaar3.Categories.Seafood;
import com.example.freshbazaar3.Categories.Vegetables;

public class homepage2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth lauth;
    //private DatabaseReference ProductRef;
    private ImageView vegetables, fruit, dairy, eggs, fish, cereals;
    FloatingActionButton cartbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
  // ProductRef= FirebaseDatabase.getInstance().getReference().child("Categories");

       vegetables=(ImageView)findViewById(R.id.vegetable);
        fruit=(ImageView) findViewById(R.id.fruit);
        dairy=(ImageView) findViewById(R.id.dairy);
        eggs=(ImageView) findViewById(R.id.eggs);
        fish=(ImageView) findViewById(R.id.fish);
        cereals=(ImageView) findViewById(R.id.cereals);
        cartbtn=(FloatingActionButton)findViewById(R.id.nav_cart);


        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepage2.this, Cart.class));
            }
        });

//        fruit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(homepage2.this, Fruits.class));
//            }
//        });
//dairy.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        startActivity(new Intent(homepage2.this, Dairy.class));
//    }
//});
//eggs.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        startActivity(new Intent(homepage2.this, Eggs.class));
//    }
//});
//
//fish.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        startActivity(new Intent(homepage2.this, Fish.class));
//    }
//});
//cereals.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View view) {
//        startActivity(new Intent(homepage2.this, Cereals.class));
//    }
//});


    }


    public void vegfunc(View view)
    {
        Intent intent=new Intent(homepage2.this, Vegetables.class);
        startActivity(intent);

    }

    public void cerealfunc(View view)
    {
        Intent intent=new Intent(homepage2.this, Cereals.class);
        startActivity(intent);

    }

    public void dairyfunc(View view)
    {
        Intent intent=new Intent(homepage2.this, Dairy.class);
        startActivity(intent);

    }

    public void fruitfunc(View view)
    {
        Intent intent=new Intent(homepage2.this, Fruit.class);
        startActivity(intent);

    }

    public void seafunc(View view)
    {
        Intent intent=new Intent(homepage2.this, Seafood.class);
        startActivity(intent);

    }

    public void eggfunc(View view)
    {
        Intent intent=new Intent(homepage2.this, Eggs.class);
        startActivity(intent);

    }

    public void searchActivity(View view)
    {
        Intent intent=new Intent(homepage2.this,SearchProducts.class);
                startActivity(intent);
    }
  /*  @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Items> options=
                new FirebaseRecyclerOptions.Builder<Items>().setQuery(ProductRef, Items.class).build();
        FirebaseRecyclerAdapter<Items, ProductViewHolder> adapter=
                new FirebaseRecyclerAdapter<Items, ProductViewHolder>(options ) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Items model) {

                        holder.txtProductName.setText(model.getItemName());
                        holder.txtProductInformation.setText(model.getExpDate());
                        holder.txtProductPrice.setText("Price = " +model.getCost());
                        Picasso.get().load(model.getImglink()).into(holder.imageView);
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
    }*/

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i = new Intent(homepage2.this,homepage2.class);


            startActivity(i);


        }
//        else if (id == R.id.nav_deal) {
//
//        }
//        else if (id == R.id.nav_wish_list) {
//
//        }
        else if (id == R.id.nav_cart) {




        }
//        else if (id == R.id.nav_track_order) {
//
//        }
//        else if (id == R.id.nav_notifications) {
//
//        }
        else if (id == R.id.nav_settings) {

        }
//        else if (id == R.id.nav_help) {
//
//        }
        else if (id == R.id.nav_feedback) {
            Intent i = new Intent(homepage2.this,feedback.class);


            startActivity(i);

        }
        else if (id == R.id.nav_policies) {

            Intent i = new Intent(homepage2.this,privacy_policy.class);


            startActivity(i);


        }
        else if (id == R.id.nav_logout) {


            Intent i = new Intent(homepage2.this, login.class);
            startActivity(i);
            lauth.signOut();

        }

        else if (id == R.id.nav_item_upload) {


            Intent i = new Intent(homepage2.this, ItemUploadActivity.class);
            startActivity(i);


        }

        else if (id == R.id.nav_wallet) {

            Intent i = new Intent(homepage2.this, wallet.class);
            startActivity(i);

        }
        else if (id == R.id.nav_about_us) {
            Intent i = new Intent(homepage2.this, About_us.class);
            startActivity(i);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
