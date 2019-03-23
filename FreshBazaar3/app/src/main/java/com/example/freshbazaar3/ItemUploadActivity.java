package com.example.freshbazaar3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class ItemUploadActivity extends AppCompatActivity {
    Button upload;
    EditText name,prod,exp,amt,cost;
    Spinner units,categs;
    ImageView itempic;
    Uri filePath;
    final int PICK_IMAGE_REQUEST = 10;
    String imgDownloadUrl;

    DatabaseReference databaseItems;
    StorageReference ProdImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_upload);
        upload = findViewById(R.id.itemUpload);
        name = findViewById(R.id.itemName);
        prod = findViewById(R.id.dateProd);
        exp = findViewById(R.id.dateExp);
        amt = findViewById(R.id.itemAmount);
        cost = findViewById(R.id.itemPrice);
        units = findViewById(R.id.unitSelect);
        categs = findViewById(R.id.categSelect);
        itempic = findViewById(R.id.itemDisplay);

        databaseItems = FirebaseDatabase.getInstance().getReference("Categories");
        ProdImgs = FirebaseStorage.getInstance().getReference().child("Item pics");

        itempic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();

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
            filePath = data.getData();
            itempic.setImageURI(filePath);
        }
    }

    void verifyData(){
        String itmname = name.getText().toString();
        String proddate = prod.getText().toString();
        String expdate = exp.getText().toString();
        String amount = amt.getText().toString();
        String price = cost.getText().toString();

        if((TextUtils.isEmpty(itmname))||(itmname.equals(""))){
            name.setError("Item must have a name");
        }

        else if((TextUtils.isEmpty(proddate))||(proddate.equals(""))){
            prod.setError("Production date mandatory");
        }

        else if((TextUtils.isEmpty(expdate))||(expdate.equals(""))){
            exp.setError("Expiry date mandatory");
        }

        else if((TextUtils.isEmpty(amount))||(amount.equals(""))){
            amt.setError("Amount is mandatory");
        }

        else if((TextUtils.isEmpty(price))||(price.equals(""))){
            amt.setError("Price is mandatory");
        }

        else if(filePath == null) {
            Toast.makeText(ItemUploadActivity.this, "Please add an image", Toast.LENGTH_SHORT).show();
        }

        else{
            uploadImage();
        }

    }


    private void uploadImage() {

        final StorageReference ref = ProdImgs.child("Product Pics/"+ UUID.randomUUID().toString());
        final UploadTask uploadTask = ref.putFile(filePath);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(ItemUploadActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ItemUploadActivity.this,"Image uploaded",Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                            throw task.getException();

                        imgDownloadUrl = ref.getDownloadUrl().toString();
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                            imgDownloadUrl = task.getResult().toString();
                        Toast.makeText(ItemUploadActivity.this, "Starting Data Upload..", Toast.LENGTH_SHORT).show();
                        uploadData();
                    }
                });
            }
        });
    }

    void uploadData(){
        String itmname = name.getText().toString();
        String proddate = prod.getText().toString();
        String expdate = exp.getText().toString();
        String amount = amt.getText().toString();
        String unit = units.getSelectedItem().toString();
        String category = categs.getSelectedItem().toString();
        String price = cost.getText().toString();
        String imageurl = imgDownloadUrl;

        String id = databaseItems.push().getKey();
        Items item = new Items(id, itmname, proddate, expdate, amount, unit, category, price, imageurl);
        databaseItems = databaseItems.child(category);
        databaseItems.child(id).setValue(item);
        databaseItems = FirebaseDatabase.getInstance().getReference("Categories");

        Toast.makeText(this, "Item Upload Complete!!", Toast.LENGTH_SHORT).show();
    }

}
