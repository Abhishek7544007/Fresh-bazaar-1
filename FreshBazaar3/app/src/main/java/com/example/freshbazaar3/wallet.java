package com.example.freshbazaar3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class wallet extends AppCompatActivity {

    private EditText amount;
    private TextView balance;
    private Button add;
    private int b;
    private int c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        amount=findViewById(R.id.etEnter);
        balance=findViewById(R.id.total);
        add=findViewById(R.id.btnAdd);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  String a = balance.getText().toString();
                // String b = amount.getText().toString();
                //   int c=Integer.valueOf(a)+Integer.valueOf(b);
                //b=Integer.parseInt(a);
                //a=amount.getText().toString();
                // b=b+Integer.parseInt(a);
                //    String s="hello";
                String a=amount.getText().toString();
                String b=balance.getText().toString();
                //int i=Integer.valueOf(s)+Integer.valueOf(balance.getText().toString());
                String s=function(a,b);
                balance.setText(s);
                amount.setText("");
            }
        });

    }


    private String function(String a, String b)
    {
        int c=Integer.parseInt(a)+Integer.parseInt(b);
        String s= Integer.toString(c);
        return s;

    }


}
