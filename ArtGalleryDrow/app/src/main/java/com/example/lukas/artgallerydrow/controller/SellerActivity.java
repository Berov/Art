package com.example.lukas.artgallerydrow.controller;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    private DrawerLayout sellerDrowerLayout;
    private ActionBarDrawerToggle sellerToggle;

    private TextView headerUser;
    private TextView headerEmail;
    private int userID;

    private CustomRecyclerViewSeller adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        allItemsForSale();

        sellerDrowerLayout = (DrawerLayout) findViewById(R.id.seller_drow_layout);
        sellerToggle = new ActionBarDrawerToggle(this, sellerDrowerLayout, R.string.seller_open, R.string.seller_close);
        sellerDrowerLayout.addDrawerListener(sellerToggle);
        sellerToggle.syncState();

        // this set button to open sidebar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navViewSeller = (NavigationView) findViewById(R.id.seller_nav_view);
        View v = navViewSeller.getHeaderView(0);
        navViewSeller.setNavigationItemSelectedListener(this);

        headerUser = (TextView) v.findViewById(R.id.nav_header_seller_name);
        headerEmail = (TextView) v.findViewById(R.id.nav_header_seller_email);

        userID = getIntent().getExtras().getInt("userID");
        headerUser.setText(getIntent().getExtras().getString("User"));
        headerEmail.setText(getIntent().getExtras().getString("email"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (sellerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_soldItems) {
            DBOperations dbOper = new DBOperations(SellerActivity.this);
            Cursor res = dbOper.getSoldItems(userID);

            RecyclerView recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
            recycler.setLayoutManager(new LinearLayoutManager(this));
            adapter = new CustomRecyclerViewSeller(this,res);
            //add adapter clicklisteners eventualno
            recycler.setAdapter(adapter);
        } else if (id == R.id.nav_itemsForSale) {
            allItemsForSale();
        } else if (id == R.id.nav_addItem) {
            openGallery();
        } else if(id == R.id.nav_profile_settings){
            // suzdai si layout za settings i tam da pokazvash profila na user-a
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.seller_drow_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void allItemsForSale() {
        DBOperations dbOper = new DBOperations(SellerActivity.this);
        Cursor res = dbOper.gelAllItems();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomRecyclerViewSeller(this,res);
        //add adapter clicklisteners eventualno
        recycler.setAdapter(adapter);
    }

    public void openGallery() {
        Intent photoPick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoPick, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {

                Uri uriImg = data.getData();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(uriImg);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    if(image.getWidth() > 1300 || image.getHeight() > 1300){
                        Toast.makeText(getApplicationContext(),"Picture is too big!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    byte[] bytes = getBytes(image);
                    //zashto gurmi pri opredeleni snimki?!!?!? i shto nqma exception?!?!?! :D
                    Intent intent = new Intent(SellerActivity.this,UploadPreview.class);
                    intent.putExtra("userID",userID);
                    intent.putExtra("image",bytes);
                    startActivity(intent);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to open image...", Toast.LENGTH_LONG).show();
                }

        }else{
            Toast.makeText(getApplicationContext(),"Cannot open this image", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
