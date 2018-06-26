package com.example.quanteq.white;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class viewOrders extends AppCompatActivity {

private RecyclerView mFoodList;
private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        mFoodList = (RecyclerView)findViewById(R.id.orderlayout);
        mFoodList.setHasFixedSize(true);
        mFoodList.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Orders");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Orders,OrdersViewHolder> FRBA = new FirebaseRecyclerAdapter<Orders, OrdersViewHolder>(

                Orders.class,
                R.layout.singleorders,
                OrdersViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(OrdersViewHolder viewHolder, Orders model, int position) {
            viewHolder.setItemName(model.getItemname());
            viewHolder.setAddress(model.getAddress());
            viewHolder.setQuantity(model.getQuantity());
            viewHolder.setTable(model.getTable());
            viewHolder.setUsername(model.getUsername());



            }
        };
        mFoodList.setAdapter(FRBA);
    }
    public static class OrdersViewHolder extends RecyclerView.ViewHolder{
        View orderView;
        public OrdersViewHolder(View itemView){
            super(itemView);
            orderView = itemView;

        }
        public void setItemName(String itemname) {
            TextView itemname_content = (TextView) orderView.findViewById(R.id.orderItemName);
            itemname_content.setText(itemname);
        }

        public void setTable(String table) {
            TextView Table_content = (TextView) orderView.findViewById(R.id.orderTable);
            Table_content.setText(table);
        }

        public void setQuantity(String quantity) {
            TextView Quantity_content = (TextView) orderView.findViewById(R.id.orderQuantity);
           Quantity_content.setText(quantity);
        }

        public void setAddress(String address) {
            TextView Address_content = (TextView) orderView.findViewById(R.id.orderAddress);
            Address_content.setText(address);

        }


        public void setUsername(String username){
            TextView Username_content = (TextView)orderView.findViewById(R.id.orderUsername);
            Username_content.setText(username);







        }
    }
}
