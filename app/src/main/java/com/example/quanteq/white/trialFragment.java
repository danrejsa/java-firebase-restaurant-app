package com.example.quanteq.white;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * Created by Quanteq on 4/6/2018.
 */

public class trialFragment extends Fragment   {
    private RecyclerView recApps;
    private DatabaseReference mDatabase;
    TextView apans;
    private Dialog mDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.singlemenuitem, container, false);
        apans =(TextView)view.findViewById(R.id.OPTIONpp);
        mDialog = new Dialog(getActivity());
        apans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView close;
                   mDialog.setContentView(R.layout.pop_up);
                   close = (TextView)mDialog.findViewById(R.id.close);


                  close.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                          mDialog.dismiss();
                         }
                    });
                   mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                   mDialog.show();
            }
        });

        return view;
    }






    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}





