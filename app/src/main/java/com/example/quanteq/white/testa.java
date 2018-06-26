package com.example.quanteq.white;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Quanteq on 4/9/2018.
 */

public class testa extends Appetizer {

    TextView apans;
    private Dialog mDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.singlemenuitem, container, false);
        apans = (TextView) view.findViewById(R.id.OPTIONpp);
        mDialog = new Dialog(getActivity());
        apans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView close;
                mDialog.setContentView(R.layout.pop_up);
                close = (TextView) mDialog.findViewById(R.id.close);


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
}
