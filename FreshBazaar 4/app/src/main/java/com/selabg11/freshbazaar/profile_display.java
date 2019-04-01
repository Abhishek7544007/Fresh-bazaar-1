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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class profile_display extends BaseActivity {
    private ImageView profilepic,edit;
    private TextView acc_name,acc_name2,acc_email,acc_number,acc_address,wallet;

    private ProgressDialog mProgressDialog;


    private Button btn;
    Uri filePath;



    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    String imgDownloadUrl;
    String type;

    StorageReference ProfImg;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_display);
        String  user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        acc_name=findViewById(R.id.disp_acc_name);
        acc_name2=findViewById(R.id.disp_acc_name2);
        acc_email=findViewById(R.id.disp_email);
        acc_number=findViewById(R.id.disp_ph_num);
        acc_address=findViewById(R.id.disp_address);
        edit=findViewById(R.id.iv_edit);
        profilepic=findViewById(R.id.imageView2);
        wallet=findViewById(R.id.disp_wallet);





        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
        final StorageReference storageReference = FirebaseStorage.getInstance().getReference(FirebaseAuth.getInstance().getUid()).child("Images/Profile Pic");
        showProgressDialog();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                Users user=dataSnapshot.getValue(Users.class);
                acc_name.setText(user.getUserName());
                acc_name2.setText(user.getUserName());
                acc_email.setText(user.getUserEmail());
                acc_number.setText(user.getUserPn());
                acc_address.setText(user.getUserAdd());
                type=user.getUserType();
                wallet.setText("₹ "+user.getWallet_balance());
                hideProgressDialog();
                showProgressDialog();

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).transform(new CircleTransform()).into(profilepic);


                    }
                });

                hideProgressDialog();

                //   Toast.makeText(profile_display.this,"Updated",Toast.LENGTH_LONG).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }



        });



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(profile_display.this,test.class);
                i.putExtra("v",type);
                startActivity(i);
            }
        });



    }


}

class CircleTransform implements Transformation {
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

