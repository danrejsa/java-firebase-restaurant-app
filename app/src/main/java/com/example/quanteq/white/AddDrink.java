package com.example.quanteq.white;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class AddDrink extends AppCompatActivity {

    private ImageButton foodImage;
    private static final int GALLREQ =1;
    private EditText name,desc,price;
    private Uri uri = null;
    private StorageReference storageReference = null;
    private DatabaseReference mRef, userData;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser current_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drink);

        name = (EditText)findViewById(R.id.itemName);
        desc = (EditText)findViewById(R.id.itemDesc);
        price = (EditText)findViewById(R.id.itemPrice);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user = firebaseAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Drink");
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
    }

    public void ImageButtonClicked (View view){
        Intent gal = new Intent(Intent.ACTION_GET_CONTENT);
        gal.setType("image/*");
        startActivityForResult(gal,GALLREQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLREQ && resultCode==RESULT_OK){
            uri = data.getData();
            foodImage =(ImageButton)findViewById(R.id.foodImageButton);
            foodImage.setImageURI(uri);
        }
    }
    public void addItemButtonClicked(View view){
        final String name_text = name.getText().toString().trim();
        final String desc_text = desc.getText().toString().trim();
        final String price_text = price.getText().toString().trim();

        if (!TextUtils.isEmpty(name_text) && !TextUtils.isEmpty(desc_text) && !TextUtils.isEmpty(price_text)){

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child(uri.getLastPathSegment());
            ref.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           final DatabaseReference newPost = mRef.push();
                           final Uri downloadurl = taskSnapshot.getDownloadUrl();
                           newPost.child("name").setValue(name_text);
                                newPost.child("desc").setValue(desc_text);
                                newPost.child("price").setValue(price_text);
                               newPost.child("image").setValue(downloadurl.toString());
                            progressDialog.dismiss();
                            Toast.makeText(AddDrink.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddDrink.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });


          //  final DatabaseReference newPost = mRef.push();
           // StorageReference filepath = storageReference.child(uri.getLastPathSegment());
         //   filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               // @Override
               // public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               //     final DatabaseReference newPost = mRef.push();
                //    final Uri downloadurl = taskSnapshot.getDownloadUrl();
                //    Toast.makeText(AddDrink.this, "Image uploaded", Toast.LENGTH_LONG).show();
                //    newPost.child("name").setValue(name_text);
                //    newPost.child("desc").setValue(desc_text);
                //    newPost.child("price").setValue(price_text);
                //    newPost.child("image").setValue(downloadurl.toString());
              //  }
            //});
        }
    }
}
