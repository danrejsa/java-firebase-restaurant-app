package com.example.quanteq.white;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class History extends AppCompatActivity {

 //private TextView infoq;
 private RecyclerView rFoodList;
    private DatabaseReference mDatabase, userData;
    private FirebaseAuth auth;
   // private FirebaseUser current_user;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser current_user;

    private Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        rFoodList = (RecyclerView)findViewById(R.id.nenurec);
        rFoodList.setHasFixedSize(true);
        rFoodList.setLayoutManager(new LinearLayoutManager(this));
        current_user =FirebaseAuth.getInstance().getCurrentUser();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");


    }

    @Override
    protected void onStart() {
        super.onStart();
        final String uid = current_user.getUid();

        FirebaseRecyclerAdapter<dailySales,FoodViewHolder> FRBA = new FirebaseRecyclerAdapter<dailySales, FoodViewHolder>(

                dailySales.class,
                R.layout.singledailylayout,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, dailySales model, int position) {

                viewHolder.setUsername(model.getUsername());
                viewHolder.setQuantity(model.getQuantity());
                viewHolder.setAddress(model.getAddress());
                viewHolder.setTable(model.getTable());
                viewHolder.setItemname(model.getItemname());


                final String food_Key = getRef(position).getKey();



            }
        };
        rFoodList.setAdapter(FRBA);
    }



    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public FoodViewHolder(View itemView){
            super(itemView);
            mView = itemView;


        }
        public void setUsername(String username){
            TextView user_name = (TextView)mView.findViewById(R.id.Name1);
            user_name.setText(username);

        }
        public void setQuantity(String quantity){
            TextView qt = (TextView)mView.findViewById(R.id.desc);
            //current_user.child("Name").setValue(quantity);
            qt.setText(quantity);

        }
        public void setAddress(String address){
            TextView food_addr = (TextView)mView.findViewById(R.id.Price1);
            food_addr.setText(address);

        }
        public void setTable(String table){
            TextView food_table = (TextView)mView.findViewById(R.id.table1);
            food_table.setText(table);

        }
        public void setItemname(String itemname){
            TextView food_item = (TextView)mView.findViewById(R.id.itemname1);
            food_item.setText(itemname);

        }

    }
}







//infoq = (TextView) findViewById(R.id.infoq);

// /if (getIntent().getExtras() != null) {
//  for (String key : getIntent().getExtras().keySet()) {
//  String value = getIntent().getExtras().getString(key);
//  infoq.append("\n" + key + ": " + value);
// }
//}