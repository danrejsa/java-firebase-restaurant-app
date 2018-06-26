package com.example.quanteq.white;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class welcome extends AppCompatActivity {
    TextView tv;
    String st;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv = (TextView)findViewById(R.id.textViewer);
        st = getIntent().getExtras().getString("Value");
        tv.setText(st);


    }
}

        /*li = (ListView)findViewById(R.id.listView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        itemList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                String user_name = dataSnapshot.child(uid).child("name").getValue(String.class);
                String user_address = dataSnapshot.child(uid).child("address").getValue(String.class);
                String user_quantity = dataSnapshot.child(uid).child("quantity").getValue(String.class);

                itemList.add(user_name);
                itemList.add(user_quantity);
                itemList.add(user_address);

                adapter = new ArrayAdapter<String>(welcome.this, android.R.layout.simple_list_item_1, itemList);
                li.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}


/*{
        "rules": {
        ".read": "auth != null",
        ".write": "auth != null"
        }



{
  "rules": {
    "users": {
      "$uid": {
        ".read": "auth != null && auth.uid == $uid"
      }
    }
  }
}
        }*/