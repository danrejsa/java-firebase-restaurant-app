package com.example.quanteq.white;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class rateUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_us);

        final RatingBar ratingBar = (RatingBar)findViewById(R.id.rating_rating_bar);
        Button submit  = (Button)findViewById(R.id.submit);
        final TextView RateUs = (TextView)findViewById(R.id.rateUs);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            RateUs.setText(" Thanks for rating us " + ratingBar.getRating());
            }
        });

    }
}
