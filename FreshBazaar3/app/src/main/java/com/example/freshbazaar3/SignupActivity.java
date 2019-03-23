package com.example.freshbazaar3;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputPhone;
    private Button btnSignIn;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Spinner spinnertype;

    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        spinnertype = findViewById(R.id.spinnertype);
        inputName = findViewById(R.id.Username);
        inputPhone = findViewById(R.id.Pnumber);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String name = inputName.getText().toString();
                String pn = inputPhone.getText().toString();
                String type = spinnertype.getSelectedItem().toString();
                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Email is mandatory");
                }

                if (password.length() < 4) {
                    inputPassword.setError("Password must be minimum 4 characters");

                }


                if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    inputEmail.setError("Enter a valid email");
                }


                if (name.equals("")) {
                    inputName.setError("Name is mandatory");


                }
                if (TextUtils.isEmpty(pn)) {
                    inputPhone.setError("Mobile Number is Mandatory");
                }
                if (pn.length() > 10) {
                    inputPhone.setError("Enter 10 Digits Number only");

                }

                //String id = databaseUsers.push().getKey();
                String id=FirebaseAuth.getInstance().getCurrentUser().getUid();

                Users user = new Users(id, name, pn, email, type);
                databaseUsers = databaseUsers.child(type);
                databaseUsers.child(id).setValue(user);
                databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
                progressBar.setVisibility(View.VISIBLE);


                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {

                                Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Registration Complete. Check your email for verification.", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this, login.class));     //LINK HOME PAGE HER
                                        } else {
                                            Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }



        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(SignupActivity.this,login.class));

            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

