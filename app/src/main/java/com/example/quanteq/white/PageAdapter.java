package com.example.quanteq.white;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Quanteq on 2/15/2018.
 */

public class PageAdapter extends FragmentStatePagerAdapter {


    int mNoOfTabs;

    public PageAdapter (){
        super(null);

    }

    public PageAdapter(FragmentManager fm, int NumberOfTabs){

        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {

            case 0:
              //  Appetizer appetizer = new Appetizer();
                return new Appetizer();

            case 1:
                MainDish maindish = new MainDish();
                return maindish;

            case 2:
                Dessert dessert = new Dessert();
                return dessert;

            case 3:

                return new Drink();



           


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
