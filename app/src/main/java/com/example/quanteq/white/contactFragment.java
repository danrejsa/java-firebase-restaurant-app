package com.example.quanteq.white;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Quanteq on 2/12/2018.
 */

public class contactFragment extends Fragment {
    View myView;
    Button faceBook,twitteR;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.contact_layout,container, false);
        return myView;





    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        faceBook = (Button)getActivity().findViewById(R.id.facebook);
        twitteR = (Button)getActivity().findViewById(R.id.twitter);


        faceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
                startActivity(facebookIntent);
            }
        });

        twitteR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"));
                startActivity(twitterIntent);
            }
       });



    }

}
