package com.example.quanteq.white;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add extends AppCompatActivity {
    private ImageButton foodImage;
    private static final int GALLREQ =1;
    private EditText name,desc,price;
    private Uri uri = null;
    private StorageReference storageReference = null;
    private DatabaseReference mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = findViewById(R.id.dishName);
        desc = findViewById(R.id.dishDesc);
        price = findViewById(R.id.dishPrice);
        storageReference = FirebaseStorage.getInstance().getReference();
        mRef = FirebaseDatabase.getInstance().getReference("Drink");


    }

    public void dishButtonClicked (View view){
        Intent gal = new Intent(Intent.ACTION_GET_CONTENT);
        gal.setType("image/*");
        startActivityForResult(gal,GALLREQ);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLREQ && resultCode==RESULT_OK){
            uri = data.getData();
            foodImage = findViewById(R.id.dishButton);
            foodImage.setImageURI(uri);
        }
    }
    public void addDishButtonClicked(View view){
        final String name_text = name.getText().toString().trim();
         final String desc_text = desc.getText().toString().trim();
         final String price_text = price.getText().toString().trim();

        if (!TextUtils.isEmpty(name_text) && !TextUtils.isEmpty(desc_text) && !TextUtils.isEmpty(price_text)) {
            StorageReference filepath = storageReference.child(uri.getLastPathSegment());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadurl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(Add.this, "Image uploaded", Toast.LENGTH_LONG).show();
                    DatabaseReference newPost = mRef.push();
                    newPost.child("name").setValue(name_text);
                    newPost.child("desc").setValue(desc_text);
                    newPost.child("price").setValue(price_text);
                    newPost.child("image").setValue(downloadurl.toString());
                }
            });
        }
    }

}
