package com.selabg11.freshbazaar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class feedback extends AppCompatActivity implements View.OnClickListener {

    //Declaring EditText
   // private EditText editTextEmail;
   // private EditText editTextSubject;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;

   // private RatingBar ratingBar;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the views
      //  editTextEmail = (EditText) findViewById(R.id.editTextEmail);
      //  editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);


        buttonSend = (Button) findViewById(R.id.btn1);

        //Adding click listener
        buttonSend.setOnClickListener(this);
    }


    private void sendEmail() {
        //Getting content for email
      //  String email = editTextEmail.getText().toString().trim();
          String email = "freshfoodbazaar55@gmail.com";
       // String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, message);

        //Executing sendmail to send email
        sm.execute();
    }

    @Override
    public void onClick(View v) {
        sendEmail();
    }
}