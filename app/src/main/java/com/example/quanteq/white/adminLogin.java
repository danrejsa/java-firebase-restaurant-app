package com.example.quanteq.white;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminLogin extends AppCompatActivity {

    private Button enterlog1;
    private EditText entermail1, enterpass1;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDial;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);


        progressDial = new ProgressDialog(this);
        enterlog1 = (Button)findViewById(R.id.enterlogin1);
        entermail1 = (EditText)findViewById(R.id.enteremail1);
        enterpass1 = (EditText)findViewById(R.id.enterpassword1);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");



        enterlog1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                SignIn();
            }


        });
    }

    private void SignIn() {

        final String email,pass;
        email = entermail1.getText().toString();
        pass = enterpass1.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(adminLogin.this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(adminLogin.this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDial.setMessage("loading....please wait");
        progressDial.show();
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkUserExist();


                }

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDial.dismiss();
                        Toast.makeText(adminLogin.this, "Incorrect login details", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void checkUserExist(){
        final String user_id = firebaseAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Intent menu = new Intent(adminLogin.this,welcome.class);
                    startActivity(menu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
