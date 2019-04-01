package com.selabg11.freshbazaar;


import android.support.annotation.NonNull;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import android.content.DialogInterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity {
    TextView fpassword;
    EditText email, password;
    Button login,tv;
    FirebaseAuth lauth;
    CheckBox shpass;
    int count=4;
    private static final String TAG = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        setContentView(R.layout.activity_login);
        lauth = FirebaseAuth.getInstance();
        tv = findViewById(R.id.notuser);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pass);
        login = findViewById(R.id.btn1);
      //  shpass = findViewById(R.id.shpass);
        fpassword= findViewById(R.id.fpassword);


     /*  shpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });*/









        login.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Enter Email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    email.setError("Email Invalid");
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Enter password");
                }
                if ((password.getText().toString()).length() < 4) {
                    password.setError("Password must be 4 Characters");
                }


                lauth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult) {

                        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if (firebaseUser.isEmailVerified()) {
                            Toast.makeText(login.this, "login successful", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(login.this,homepage2.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(login.this, "Email Not verified", Toast.LENGTH_SHORT).show();
                            firebaseUser.sendEmailVerification()
                                    .addOnCompleteListener(login.this, new OnCompleteListener<Void>() {
                                        @Override

                                        public void onComplete(Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(),
                                                        "Verification email sent to " + firebaseUser.getEmail(),
                                                        Toast.LENGTH_SHORT).show();
                                                Log.d("Verification", "Verification email sent to " + firebaseUser.getEmail());
                                            } else {
                                                Log.e(TAG, "sendEmailVerification", task.getException());
                                                Toast.makeText(getApplicationContext(),

                                                        "Failed to send verification email.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(login.this, "Incorrect Email/password", Toast.LENGTH_SHORT).show();
                        count--;
                        if (count > 0)

                            Toast.makeText(login.this, "Number of attempts remaining " + count, Toast.LENGTH_SHORT).show();
                        if (count == 0) {
                            login.setEnabled(false);
                            Toast.makeText(login.this, "Please Try Again Later... ", Toast.LENGTH_SHORT).show();
                        }
                    }

                });


            }

        });




        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(login.this,reset.class);
                startActivity(i);
            }
        });


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(login.this, SignupActivity.class);
                startActivity(i);
            }
        });






    }



    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit the App?")
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


