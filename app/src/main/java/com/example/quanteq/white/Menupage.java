package com.example.quanteq.white;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Menupage extends AppCompatActivity {

    private RecyclerView mFoodList;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);

        mFoodList = (RecyclerView)findViewById(R.id.orderlayout);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("item");
    }


    @Override

   protected void onStart() {
        super.onStart();
       // mAuth.addAuthStateListener(mAuthListener);
        FirebaseRecyclerAdapter <Food,FoodViewHolder> FBRA = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.singlemenuitem,
                FoodViewHolder.class,
                mDatabase
        ) {


            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                final String food_Key = getRef(position).getKey();
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent single = new Intent(Menupage.this, Orderpage.class);
                        single.putExtra("FoodId",food_Key);
                        startActivity(single);
                    }
                });

            }
        };

        mFoodList.setAdapter(FBRA);
   }



    public static  class FoodViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public FoodViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
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
