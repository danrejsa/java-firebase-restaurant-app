package com.example.quanteq.white;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class QrestaurantNavStart extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DrinkStart.OnFragmentInteractionListener, AppetizerStart.OnFragmentInteractionListener,MainDishStart.OnFragmentInteractionListener,DessertStart.OnFragmentInteractionListener {
    String phone = "081 63493874";

    MaterialSearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrestaurant_nav_start);
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
        final PageAdapterStart adapter = new PageAdapterStart(getSupportFragmentManager(), tabLayout.getTabCount());
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
                    Toast.makeText(QrestaurantNavStart.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(QrestaurantNavStart.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.qrestaurant_nav_start, menu);
        //  MenuItem item = menu.findItem(R.id.action_search);
        //searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent settings = new Intent(QrestaurantNavStart.this, SettingsActivity.class);
                startActivity(settings);
                break;

            case R.id.action_login:
                Intent login = new Intent(QrestaurantNavStart.this, login.class);
                startActivity(login);

                return true;
        }

        return super.onOptionsItemSelected(item);


    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_menu_layout) {
            Intent intent = new Intent(QrestaurantNavStart.this, QrestaurantNavStart.class);
            startActivity(intent);

        } else if (id == R.id.nav_about_layout) {
            fragmentManager.beginTransaction().replace(R.id.content_frame, new aboutFragment()).commit();

        }else if (id == R.id.nav_login) {
            Intent intent = new Intent(QrestaurantNavStart.this, login.class);
            startActivity(intent);




        }

        else if (id == R.id.nav_locationlayout) {
            Intent intent = new Intent(QrestaurantNavStart.this, MapsActivity.class);
            startActivity(intent);




        } else if (id == R.id.nav_rate) {
            Intent intent = new Intent(QrestaurantNavStart.this, rateUs.class);
            startActivity(intent);


        }else if (id == R.id.nav_exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(QrestaurantNavStart.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // finish();
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
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
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(QrestaurantNavStart.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                          // finish();
                            finishAffinity();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

