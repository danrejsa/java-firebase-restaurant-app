package com.example.quanteq.white;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;



/**
 * Created by Quanteq on 6/5/2018.
 */

public class PageAdapterStart extends FragmentStatePagerAdapter {

    int mNoOfTabs;

    public PageAdapterStart (){
        super(null);

    }

    public PageAdapterStart(FragmentManager fm, int NumberOfTabs){

        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch(position) {

            case 0:
                return new AppetizerStart();


            case 1:
                return new MainDishStart();


            case 2:
            return new DessertStart();

            case 3:

                return new DrinkStart();






            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}

