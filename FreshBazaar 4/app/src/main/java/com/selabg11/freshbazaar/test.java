package com.selabg11.freshbazaar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class test extends BaseActivity {
    private ImageView profile_pic,edit_pic;

    private  TextView  acc_name2,edit_email;
    private EditText edit_name,edit_number,edit_address;




    private Button save_btn;

    final int PICK_IMAGE_REQUEST = 10;



    String name,email,number,address;

    DatabaseReference databaseReference;
    DatabaseReference nm_ref;
    DatabaseReference pn_ref;
    DatabaseReference add_ref;




    Uri imagePath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final String  user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        edit_name=findViewById(R.id.edit_acc_name);
        acc_name2=findViewById(R.id.disp_acc_name3);
        edit_email=findViewById(R.id.edit_email);
        edit_number=findViewById(R.id.edit_ph_num);
        edit_address=findViewById(R.id.edit_address);
        save_btn=findViewById(R.id.save_btn);
        profile_pic=findViewById(R.id.imageView3);
        edit_pic=findViewById(R.id.iv_edit2);

        final FirebaseUser user=FirebaseAuth.
                getInstance().getCurrentUser();
        edit_email.setText(user.getEmail());

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
        nm_ref=databaseReference.child("userName");
        pn_ref=databaseReference.child("userPn");
        add_ref=databaseReference.child("userAdd");
        edit_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                chooseImage();
            }
        });
        nm_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nm=dataSnapshot.getValue(String.class);
                acc_name2.setText(nm);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        showProgressDialog();


        final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child(FirebaseAuth.getInstance().getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(
                new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).transform(new CircleTransform2()).into(profile_pic);

                        hideProgressDialog();
                    }
                });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=edit_name.getText().toString();
                number=edit_number.getText().toString();
                address=edit_address.getText().toString();


              //  Toast.makeText(test.this, "Address"+address, Toast.LENGTH_SHORT).show();


                nm_ref.setValue(name);
                pn_ref.setValue(number);
                add_ref.setValue(address);

                StorageReference imageReference = storageReference.child(FirebaseAuth.getInstance().getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
                UploadTask uploadTask = imageReference.putFile(imagePath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(test.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        Toast.makeText(test.this, "Upload successful!", Toast.LENGTH_SHORT).show();
                    }
                });
                showProgressDialog();

                Intent i = new Intent(test.this,profile_display.class);
                startActivity(i);
                hideProgressDialog();

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
            profile_pic.setImageURI(imagePath);
        }
    }



}
class CircleTransform2 implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}

