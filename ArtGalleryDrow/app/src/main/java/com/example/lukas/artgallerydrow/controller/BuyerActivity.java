package com.example.lukas.artgallerydrow.controller;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;

public class BuyerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView userImage;
    private TextView username, email, editProfile;
    private int userID;
    public static final int CHANGE_PROFILE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userID = getIntent().getExtras().getInt("userID");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // drawer.setDrawerListener(toggle);
        drawer.addDrawerListener(toggle);
        //drawer.setBackgroundResource(R.color.colorMiddle);


        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setBackgroundResource(R.color.colorMiddle);
        navigationView.setItemBackgroundResource(R.color.colorAccent);


        View v = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        userImage = (ImageView) v.findViewById(R.id.buyer_img_header);
        username = (TextView) v.findViewById(R.id.buyer_name_header);
        email = (TextView) v.findViewById(R.id.buyer_email_header);
        editProfile = (TextView) v.findViewById(R.id.buyer_edit_profile);
        //TODO add money!!!!!!!!!!!!!!!!!!!!!!

        username.setText(getIntent().getExtras().getString("User").toString());
        email.setText(getIntent().getExtras().getString("email").toString());


        byte[] bytes = bytesImage();
        if (bytes == null) {
            userImage.setImageResource(R.mipmap.emptyprofile);
        } else {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

            RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
            round.setCircular(true);

            userImage.setImageDrawable(round);
        }

//        editProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent1 = new Intent(BuyerActivity.this, UserProfileActivity.class);
//                intent1.putExtra("userID", userID);
//                startActivityForResult(intent1, CHANGE_PROFILE);
//            }
//        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_PROFILE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "You canceled updates..", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_OK) {
                recreate();
            }
        }
    }

    //------------------------------------------------------------------------------------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buyer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.buyer_edit_profile) {
            Intent intent1 = new Intent(BuyerActivity.this, UserProfileActivity.class);
            intent1.putExtra("userID", userID);
            startActivityForResult(intent1, CHANGE_PROFILE);
            return true;
        }else if(id == R.id.buyer_logout){
            Intent intent1 = new Intent(BuyerActivity.this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.b_grapphics) {
            //TextView camera = (TextView) findViewById(R.id.temp_test);
            //camera.setText("this is camera");
            getItemByType("graphics");

        } else if (id == R.id.b_linocut) {
            getItemBySubtype("linocut");

        } else if (id == R.id.b_screen_printing) {
            getItemBySubtype("screen printing");

        } else if (id == R.id.b_etching) {
            getItemBySubtype("etching");


        } else if (id == R.id.b_painting) {//-----------
            getItemByType("painting");

        } else if (id == R.id.b_aquarelle) {
            getItemBySubtype("aquarelle");

        } else if (id == R.id.b_oil_painting) {
            getItemBySubtype("oil painting");

        } else if (id == R.id.b_acrylic_painting) {
            getItemBySubtype("acrylic painting");

        } else if (id == R.id.b_charcoal) {
            getItemBySubtype("charcoal");

        } else if (id == R.id.b_pencil_painting) {
            getItemBySubtype("pencil painting");

        } else if (id == R.id.b_pastel) {
            getItemBySubtype("pastel");

        } else if (id == R.id.b_sculpture) {//-----------
            getItemByType("sculpture");

        } else if (id == R.id.b_stone) {
            getItemBySubtype("stone");

        } else if (id == R.id.b_metal) {
            getItemBySubtype("metal");

        } else if (id == R.id.b_gypsum) {
            getItemBySubtype("gypsum");

        } else if (id == R.id.b_wood_carving) {//-----------
            getItemByType("wood carving");

        } else if (id == R.id.b_classic_wood_carving) {
            getItemBySubtype("classic wood carving");

        } else if (id == R.id.b_modern_carving) {
            getItemBySubtype("modern carving");

        } else if (id == R.id.b_interior_carving) {
            getItemBySubtype("interior carving");

        } else if (id == R.id.b_souvenir_woodcarving) {
            getItemBySubtype("souvenir woodcarving");

        } else if (id == R.id.b_ceramics) {//-----------
            getItemByType("ceramics");

        } else if (id == R.id.b_wall_panel) {
            getItemBySubtype("wall panel");

        } else if (id == R.id.b_souvenir_ceramics) {
            getItemBySubtype("souvenir ceramics");

        } else if (id == R.id.b_household_ceramics) {
            getItemBySubtype("household ceramics");

        } else if (id == R.id.b_glassware) {//-----------
            getItemByType("glassware");

        } else if (id == R.id.b_stained_glass) {
            getItemBySubtype("stained glass");

        } else if (id == R.id.b_household_glass_sculpture) {
            getItemBySubtype("household glass sculpture");

        } else if (id == R.id.b_souvenir_glass) {
            getItemBySubtype("souvenir glass");

        } else if (id == R.id.b_jewelery) {
            getItemBySubtype("jewelery");

        } else if (id == R.id.b_extiles) {//-----------
            getItemByType("textiles");

        } else if (id == R.id.b_clothes) {
            getItemBySubtype("clothes");

        } else if (id == R.id.b_accessories) {
            getItemBySubtype("accessories");

        } else if (id == R.id.b_textile_panel) {
            getItemBySubtype("textile panel");

        } else if (id == R.id.b_carpets) {
            getItemBySubtype("carpets");

        } else if (id == R.id.b_iconography) {//-----------
            getItemByType("iconography");

        } else if (id == R.id.b_new_style) {
            getItemBySubtype("new style");

        } else if (id == R.id.b_old_style) {
            getItemBySubtype("old style");

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getItemByType(String type) {

        DBOperations operator = new DBOperations(BuyerActivity.this);
        Cursor dbResult = operator.getItemsForSaleByType(type);
        if (dbResult == null) {
            Toast.makeText(getApplicationContext(), "There are no items for sale!", Toast.LENGTH_LONG).show();
            return;
        }
        RecyclerView recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        CustomRecyclerViewBuyer adapter = new CustomRecyclerViewBuyer(dbResult, this);
        recycler.setAdapter(adapter);

    }

    private void getItemBySubtype(String subtype) {

        DBOperations operator = new DBOperations(BuyerActivity.this);
        Cursor dbResult = operator.getItemsForSaleBySubtype(subtype);
        if (dbResult == null) {
            Toast.makeText(getApplicationContext(), "There are no items for sale!", Toast.LENGTH_LONG).show();
            return;
        }
        RecyclerView recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        CustomRecyclerViewBuyer adapter = new CustomRecyclerViewBuyer(dbResult, this);
        recycler.setAdapter(adapter);

    }


    private byte[] bytesImage() {
        DBOperations dbOper = new DBOperations(BuyerActivity.this);
        Cursor res = dbOper.checkUserForImage(userID);
        byte[] b = null;
        while (res.moveToNext()) {
            b = res.getBlob(0);
        }
        if (b == null) {
            return null;
        } else {
            return b;
        }
    }
}
