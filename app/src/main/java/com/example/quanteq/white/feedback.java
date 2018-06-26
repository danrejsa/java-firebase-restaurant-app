package com.example.quanteq.white;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class feedback extends AppCompatActivity {


    private Button feedBut;
    private EditText feed;
    private DatabaseReference mRef,userData;
    private Uri uri = null;
    FirebaseUser current_user;
     FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        feed = (EditText) findViewById(R.id.feedback);
       // feedBut = (Button) findViewById(R.id.feedbackButton);

        mRef = FirebaseDatabase.getInstance().getReference().child("Feedback");
        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser();
        userData = FirebaseDatabase.getInstance().getReference().child("users").child(current_user.getUid());
    }

    public void addFeedbackItemClicked(View view) {
        final String feedback_text = feed.getText().toString().trim();
        if (!TextUtils.isEmpty(feedback_text)) {
            final DatabaseReference newPosts = mRef.push();
            userData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newPosts.child("Report").setValue(feedback_text);
                    newPosts.child("Username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(feedback.this, "Your FeedBack been received. Thanks", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
