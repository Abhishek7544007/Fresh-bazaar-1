package com.selabg11.freshbazaar;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class wallet extends BaseActivity {

    private EditText amount;
    private TextView balance;
    DatabaseReference my_wallet_ref;
    private Button add;

    String new_bal ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        amount=findViewById(R.id.etEnter);
        balance=findViewById(R.id.total);
        add=findViewById(R.id.btnAdd);
        String user_id=FirebaseAuth.getInstance().getUid();
        my_wallet_ref = FirebaseDatabase.getInstance().getReference("Users").child(user_id).child("wallet_balance");
        show_balance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new_bal=balance.getText().toString();

                //  String a = balance.getText().toString();
                // String b = amount.getText().toString();
                //   int c=Integer.valueOf(a)+Integer.valueOf(b);
                //b=Integer.parseInt(a);
                //a=amount.getText().toString();
                // b=b+Integer.parseInt(a);
                //    String s="hello";
                String am=amount.getText().toString();
                //  Toast.makeText(getApplicationContext(),"current balance"+new_bal +"add money"+am,Toast.LENGTH_LONG).show();
                Boolean flag=true;

                Integer sum=add(new_bal,am);
                flag = sum <= 10000;
                String wal=String.valueOf(sum);
                if(flag==true)
                    my_wallet_ref.setValue(wal);
                else
                {
                    Toast.makeText(getApplicationContext(),"Maximum Wallet Limit:10000",Toast.LENGTH_LONG).show();


                }

                amount.setText("");
                show_balance();
                new_bal=balance.getText().toString();
            }
        });

    }
    int add(String a,String b)
    {

        return Integer.valueOf(a)+Integer.valueOf(b);
    }
    void show_balance()
    {
        showProgressDialog();
        my_wallet_ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bal = dataSnapshot.getValue(String.class);
                //do what you want with the email\
                balance.setText(bal);
                hideProgressDialog();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


}
