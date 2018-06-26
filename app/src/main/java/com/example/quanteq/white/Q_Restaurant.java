package com.example.quanteq.white;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;


public class Q_Restaurant extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Drink.OnFragmentInteractionListener, Appetizer.OnFragmentInteractionListener,MainDish.OnFragmentInteractionListener,Dessert.OnFragmentInteractionListener {
    String phone = "081 63493874";
    String st;

    MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q__restaurant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Appetizer"));
        tabLayout.addTab(tabLayout.newTab().setText("Main Dish"));
        tabLayout.addTab(tabLayout.newTab().setText("Dessert"));
        tabLayout.addTab(tabLayout.newTab().setText("Drinks"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton mCallButton = (FloatingActionButton) findViewById(R.id.callButton);
        FloatingActionButton mSmsButton = (FloatingActionButton) findViewById(R.id.smsButton);
        final LinearLayout mCallLayout = (LinearLayout) findViewById(R.id.callLayout);
        final LinearLayout mSmsLayout = (LinearLayout) findViewById(R.id.smsLayout);


        // edtSeach = (EditText)findViewById(R.id.edtSearch);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallLayout.getVisibility() == View.VISIBLE && mSmsLayout.getVisibility() == View.VISIBLE) {
                    mCallLayout.setVisibility(View.GONE);
                    mSmsLayout.setVisibility(View.GONE);
                } else {
                    mCallLayout.setVisibility(View.VISIBLE);
                    mSmsLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        mCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_DIAL);
                mIntent.setData(Uri.parse("tel:" + phone));
                if (mIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mIntent);
                } else {
                    Toast.makeText(Q_Restaurant.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW);
                mIntent.setData(Uri.parse("sms:" + phone));
                if (mIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mIntent);
                } else {
                    Toast.makeText(Q_Restaurant.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);



        View navView =  navigationView.inflateHeaderView(R.layout.nav_header_q__restaurant);

        ImageView imgvw = (ImageView)navView.findViewById(R.id.imageView);
        TextView tv = (TextView)navView.findViewById(R.id.tex);


        imgvw.setImageResource(R.drawable.dare2);
        st =getIntent().getExtras().getString("Value");
        tv.setText(st);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.q__restaurant, menu);
        //  MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settings = new Intent(Q_Restaurant.this, SettingsActivity.class);
                startActivity(settings);
                break;



        }

        return super.onOptionsItemSelected(item);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_home) {
            Intent home = new Intent(this, Q_Restaurant.class);
            startActivity(home);

        }
        else if (id == R.id.nav_about_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new aboutFragment()).commit();

             } else if (id == R.id.nav_history_layout) {
            Intent intent = new Intent(Q_Restaurant.this, welcome.class);
            startActivity(intent);

        } else if (id == R.id.nav_feedbacklayout) {
            Intent intent = new Intent(Q_Restaurant.this, feedback.class);
            startActivity(intent);

        } else if (id == R.id.nav_locationlayout) {
            Intent intent = new Intent(Q_Restaurant.this, MapsActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_signOut) {
            Intent intent = new Intent(Q_Restaurant.this, QrestaurantNavStart.class);
            startActivity(intent);

        } else if (id == R.id.nav_rate) {
            Intent intent = new Intent(Q_Restaurant.this, rateUs.class);
            startActivity(intent);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}


