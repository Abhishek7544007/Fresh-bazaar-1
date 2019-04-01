package com.selabg11.freshbazaar;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import static com.selabg11.freshbazaar.R.id.nav_logout;

 abstract public class homepage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
     FirebaseAuth lauth;


     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
      /*   setContentView(R.layout.activity_homepage);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

         setSupportActionBar(toolbar);

         FloatingActionButton cart = (FloatingActionButton) findViewById(R.id.nav_cart);
         cart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
             }
         });

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                 this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.syncState();

         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);


     }

     //Adapter


     @Override
     public void onBackPressed() {
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         if (drawer.isDrawerOpen(GravityCompat.START)) {
             drawer.closeDrawer(GravityCompat.START);
         } else {
             AlertDialog.Builder builder = new AlertDialog.Builder(homepage.this);
             builder.setTitle(R.string.app_name);
             builder.setIcon(R.mipmap.ic_launcher);
             builder.setMessage("Do you want to exit Homepage?")
                     .setCancelable(false)
                     .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             finish();
                         }
                     })
                     .setNegativeButton("No", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             dialog.cancel();
                         }
                     });
             AlertDialog alert = builder.create();
             alert.show();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(homepage.this,homepage.class);


            startActivity(i);


        }
        else if (id == R.id.nav_deal) {

        } else if (id == R.id.nav_wish_list) {

        }
        else if (id == R.id.nav_cart) {


            

        }
        else if (id == R.id.nav_track_order) {

        }
        else if (id == R.id.nav_notifications) {

        }
        else if (id == R.id.nav_settings) {

        }
        else if (id == R.id.nav_help) {

        }else if (id == R.id.nav_feedback) {
            Intent i = new Intent(homepage.this,feedback.class);


            startActivity(i);

        }
        else if (id == R.id.nav_policies) {

            Intent i = new Intent(homepage.this,privacy_policy.class);


            startActivity(i);


        }
        else if (id == nav_logout) {


            Intent i = new Intent(homepage.this, login.class);
            startActivity(i);
            lauth.signOut();

        }

        else if (id == R.id.nav_wallet) {

            Intent i = new Intent(homepage.this, wallet.class);
            startActivity(i);

        }
        else if (id == R.id.nav_about_us) {
            Intent i = new Intent(homepage.this, About_us.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

 }  }

*/
     }}