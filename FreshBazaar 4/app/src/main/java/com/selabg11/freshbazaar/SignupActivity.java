package com.selabg11.freshbazaar;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;



public class SignupActivity extends AppCompatActivity {
    private EditText inputEmail, inputPassword, inputName, inputPhone;
    private Button btnSignIn;
    private Button btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private Spinner spinnertype;
    private  String Userid;
    private ImageView userProfilePic,iv_put;
    final int PICK_IMAGE_REQUEST = 10;



    private DatabaseReference myRef;
    Uri imagePath;
    private StorageReference storageReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        spinnertype = findViewById(R.id.spinnertype);
        inputName = findViewById(R.id.Username);
        inputPhone = findViewById(R.id.Pnumber);
        userProfilePic=findViewById(R.id.ivprofile);
        iv_put=findViewById(R.id.iv_put);
        storageReference=FirebaseStorage.getInstance().getReference();
        iv_put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();

            }

        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                final String name = inputName.getText().toString();
                final String pn = inputPhone.getText().toString();
                final String type = spinnertype.getSelectedItem().toString();
                final String add = "Address";

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
                if(imagePath==null)
                {
                    Toast.makeText(getApplicationContext(),"Uplaod an Profile Pic",Toast.LENGTH_LONG);
                }
                progressBar.setVisibility(View.VISIBLE);
//final Users user=new Users()
                // public Users(String userId, String userName, String userPn, String userEmail, String userType) {

                try{
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {

                                Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Registration Complete. Check your email for verification.", Toast.LENGTH_SHORT).show();

                                            try
                                            {
                                                auth.signInWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                    @Override
                                                    public void onSuccess(@NonNull AuthResult authResult) {
                                                        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                                                        // Toast.makeText(login.this, "login successful"+firebaseUser.getUid(), Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(SignupActivity.this, login.class);
                                                        Userid=firebaseUser.getUid();

                                                        startActivity(i);
                                                        //Toast.makeText(SignupActivity.this, "Signed in user id" + firebaseUser.getUid(), Toast.LENGTH_LONG).show();
                                                        // public Users(String userId, String userName, String userPn, String userEmail, String userType) {
                                                        // StorageReference imageReference = storageReference.child(FirebaseAuth.getInstance().getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                                                        StorageReference imageReference = storageReference.child(FirebaseAuth.getInstance().getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                                                        UploadTask uploadTask = imageReference.putFile(imagePath);
                                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(SignupActivity.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                Toast.makeText(SignupActivity.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        Users user=new Users(Userid,name,pn,email,type,"","0");
                                                        myRef.child("Users").child(Userid).setValue(user);
                                                        Toast.makeText(SignupActivity.this, "Upload successful info!", Toast.LENGTH_SHORT).show();



                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(Exception e) {
                                                        Toast.makeText(SignupActivity.this, "Not signed in .", Toast.LENGTH_SHORT).show();

                                                    }

                                                });
                                            }catch(Exception e)
                                            {
                                                Toast.makeText(SignupActivity.this, "Not signed in .", Toast.LENGTH_SHORT).show();


                                            }


                                        } else {
                                            Toast.makeText(SignupActivity.this, "email Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(SignupActivity.this, " create Authentication failed.", Toast.LENGTH_SHORT).show();

                            }

                            // auth.signOut();
                            //Toast.makeText(SignupActivity.this, "current signed in user."+auth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();


                        }
                    });
                }catch(Exception e)
                {
                    Toast.makeText(SignupActivity.this, "try Authentication failed.", Toast.LENGTH_SHORT).show();

                }




            }



        });



        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(SignupActivity.this,login.class));

            }

        });

    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQUEST  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            imagePath = data.getData();
            userProfilePic.setImageURI(imagePath);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

