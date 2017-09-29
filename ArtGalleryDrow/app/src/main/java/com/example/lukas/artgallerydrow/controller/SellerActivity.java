package com.example.lukas.artgallerydrow.controller;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.lukas.artgallerydrow.R;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout sellerDrowerLayout;
    private ActionBarDrawerToggle sellerToggle;

    private TextView headerUser;
    private TextView headerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        sellerDrowerLayout = (DrawerLayout) findViewById(R.id.seller_drow_layout);
        sellerToggle = new ActionBarDrawerToggle(this, sellerDrowerLayout, R.string.seller_open, R.string.seller_close);
        sellerDrowerLayout.addDrawerListener(sellerToggle);
        sellerToggle.syncState();

        // this set button to open sidebar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navViewSeller = (NavigationView)findViewById(R.id.seller_nav_view);
        View v = navViewSeller.getHeaderView(0);
        navViewSeller.setNavigationItemSelectedListener(this);

        headerUser = (TextView) v.findViewById(R.id.nav_header_seller_name);
        headerEmail = (TextView) v.findViewById(R.id.nav_header_seller_email);

        headerUser.setText(getIntent().getExtras().getString("User"));
        headerEmail.setText(getIntent().getExtras().getString("email"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(sellerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_soldItems) {

        } else if (id == R.id.nav_itemsForSale) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.seller_drow_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
