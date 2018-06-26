package com.example.quanteq.white;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Nenupage extends AppCompatActivity {

    private RecyclerView rFoodList;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Dialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nenupage);

        rFoodList = (RecyclerView)findViewById(R.id.nenurec);
        rFoodList.setHasFixedSize(true);
        rFoodList.setLayoutManager(new LinearLayoutManager(this));
       // mDatabase = FirebaseDatabase.getInstance().getReference().child("item");
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");
        mDialog = new Dialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Foot,FoodViewHolder> FRBA = new FirebaseRecyclerAdapter<Foot, FoodViewHolder>(

                Foot.class,
                R.layout.singlemenuitem,
                FoodViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Foot model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final String food_Key = getRef(position).getKey();
                viewHolder.apans.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(Nenupage.this, "Hello Dani", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single = new Intent(Nenupage.this, Orderpage.class);
                        single.putExtra("FoodID",food_Key);
                        startActivity(single);
                    }
                });


            }
        };
        rFoodList.setAdapter(FRBA);
    }



    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView apans;
        public FoodViewHolder(View itemView){
            super(itemView);
            mView = itemView;
            apans = (TextView)mView.findViewById(R.id.OPTIONpp);

        }
        public void setName(String name){
            TextView food_name = (TextView)mView.findViewById(R.id.foodName);
            food_name.setText(name);

        }
        public void setDesc(String desc){
            TextView food_desc = (TextView)mView.findViewById(R.id.foodDesc);
            food_desc.setText(desc);

        }
        public void setPrice(String price){
            TextView food_price = (TextView)mView.findViewById(R.id.foodPrice);
            food_price.setText(price);

        }
        public void setImage(Context ctx, String image){
            ImageView food_image = (ImageView) mView.findViewById(R.id.foodImage);
            Picasso.with(ctx).load(image).into(food_image);




        }
    }
}





