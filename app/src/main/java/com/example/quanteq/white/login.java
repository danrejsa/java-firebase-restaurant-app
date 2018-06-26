package com.example.quanteq.white;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class login extends AppCompatActivity {


    private  Button enterlog, enterreg,btnResetPassword;
    private  EditText  entermail, enterpass;
    private FirebaseAuth firebaseAuth;
    private  ProgressDialog progressDial;
    private DatabaseReference mDatabase;
    String st;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDial = new ProgressDialog(this);
        enterreg = (Button)findViewById(R.id.enterregister);
        enterlog = (Button)findViewById(R.id.enterlogin);
        entermail = (EditText)findViewById(R.id.enteremail);
        enterpass = (EditText)findViewById(R.id.enterpassword);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);



        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, ResetPasswordActivity.class));
            }
        });

        enterreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, MainActivity.class);
                startActivity(intent);
            }
        });
        enterlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }


        });
    }

    private void SignIn() {

        final String email, pass;
        email = entermail.getText().toString();
        pass = enterpass.getText().toString();

        if (email.isEmpty()) {
            entermail.setError("Email is required");
            entermail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            entermail.setError("Please enter a valid email");
            entermail.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            enterpass.setError("Password is required");
            enterpass.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            enterpass.setError("Minimum lenght of password should be 6");
            enterpass.requestFocus();
            return;
        }

        progressDial.setMessage("please wait....");
        progressDial.show();
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDial.dismiss();
                if (task.isSuccessful()) {
                    checkUserExist();

                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void checkUserExist(){
        final String user_id = firebaseAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    Intent menu = new Intent(login.this,Q_Restaurant.class);
                    st = entermail.getText().toString();
                    menu.putExtra("Value",st);
                    startActivity(menu);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {


       Intent logg = new Intent(login.this, QrestaurantNavStart.class);
       startActivity(logg);

    }

}