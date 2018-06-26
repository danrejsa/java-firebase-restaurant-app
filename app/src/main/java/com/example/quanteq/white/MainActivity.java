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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp;
   // private ProgressBar progressBar;
    private FirebaseAuth auth;
    private DatabaseReference mDatabase;
    private  ProgressDialog progressDial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnSignIn = (Button)findViewById(R.id.sign_in_button);
        btnSignUp = (Button)findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressDial = new ProgressDialog(this);


        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
    }



    public void signUpClickedButton(View view) {

        final String email_text = inputEmail.getText().toString().trim();
        String password_text = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email_text)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password_text)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password_text.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDial.setMessage("Registering....please wait");
        progressDial.show();
        auth.createUserWithEmailAndPassword(email_text, password_text).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDial.dismiss();
                String user_id = auth.getCurrentUser().getUid();
                DatabaseReference current_user = mDatabase.child(user_id);
                current_user.child("Name").setValue(email_text);

                Toast.makeText(MainActivity.this, "Registration Successful",
                Toast.LENGTH_SHORT).show();


            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDial.dismiss();
                Toast.makeText(MainActivity.this, "Unsuccessful",
                        Toast.LENGTH_SHORT).show();
            }
        });
                //addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         ////   @Override
         //   public void onComplete(@NonNull Task<AuthResult> task) {
               // progressBar.setVisibility(View.GONE);
            //    progressDial.dismiss();
              //  if (!task.isSuccessful()) {
               //     String user_id = auth.getCurrentUser().getUid();
               //     DatabaseReference current_user = mDatabase.child(user_id);
                //    current_user.child("Name").setValue(email_text);



               // }


              //  else{
                //    Toast.makeText(MainActivity.this, " Unsuccessful", Toast.LENGTH_SHORT).show();
              //  }

          //  }
        //});
        //.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
         //   @Override
         //   public void onSuccess(AuthResult authResult) {
               // progressDial.dismiss();
           //     Toast.makeText(MainActivity.this, "Registration Successful",
                      //  Toast.LENGTH_SHORT).show();

           // }
      //  });
              //  .addOnFailureListener(new OnFailureListener() {
                //    @Override
                //    public void onFailure(@NonNull Exception e) {
                 //       progressDial.dismiss();
                  //      Toast.makeText(MainActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                 //   }
               // });
   //  progressBar.setVisibility(View.GONE);
    }

  //  @Override
  //  protected void onResume() {
    //    super.onResume();
      //  progressBar.setVisibility(View.GONE);
  //  }
  @Override
  public void onBackPressed() {


      Intent logg = new Intent(MainActivity.this, login.class);
      startActivity(logg);

  }
}
