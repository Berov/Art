package com.example.lukas.artgallerydrow.controller;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.bumptech.glide.Glide;
import com.example.lukas.artgallerydrow.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SellerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PICK_IMAGE = 100;
    private static final int CHANGE_PROFILE = 50;
    private DrawerLayout sellerDrowerLayout;
    private ActionBarDrawerToggle sellerToggle;

    private TextView headerUser;
    private TextView headerEmail;
    private ImageView imageHeader;
    private int userID;

    private CustomRecyclerViewSeller adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        userID = getIntent().getExtras().getInt("userID");

        sellerDrowerLayout = (DrawerLayout) findViewById(R.id.seller_drow_layout);
        sellerDrowerLayout.setBackgroundResource(R.color.colorAccent);
        sellerToggle = new ActionBarDrawerToggle(this, sellerDrowerLayout, R.string.seller_open, R.string.seller_close);
        sellerDrowerLayout.addDrawerListener(sellerToggle);
        sellerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navViewSeller = (NavigationView) findViewById(R.id.seller_nav_view);
        navViewSeller.setBackgroundResource(R.color.colorAccent);
        View v = navViewSeller.getHeaderView(0);
        navViewSeller.setNavigationItemSelectedListener(this);

        headerUser = (TextView) v.findViewById(R.id.nav_header_seller_name);
        headerUser.setText(getUsername());

        headerEmail = (TextView) v.findViewById(R.id.nav_header_seller_email);

        imageHeader = (ImageView) v.findViewById(R.id.imgHeaderSeller);

        allItemsForSale();

        byte[] bytes = bytesImage();
        if(bytes == null){
            imageHeader.setImageResource(R.mipmap.emptyprofile);
        }else{
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            RoundedBitmapDrawable round = RoundedBitmapDrawableFactory.create(getResources(),bitmap);
            round.setCircular(true);

            imageHeader.setImageDrawable(round);
        }

        headerEmail.setText(getIntent().getExtras().getString("email"));
    }

    private byte[] bytesImage(){
        DBOperations dbOper = new DBOperations(SellerActivity.this);
        Cursor res = dbOper.checkUserForImage(userID);
        byte[] b = null;
        while (res.moveToNext()){
            b = res.getBlob(0);
        }
        if(b == null){
            return null;
        }else{
            return b;
        }
    }

    private String getUsername() {
        DBOperations dbOper = new DBOperations(SellerActivity.this);
        Cursor res = dbOper.getUserName(userID);
        String name = null;
        while (res.moveToNext()){
            name = res.getString(0);
        }
        return name;
    }

    @Override
    protected void onResume() {
        super.onResume();
        allItemsForSale();
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
            Cursor res = null;
            res = dbOper.getSoldItems(userID);

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
            Intent intent1 = new Intent(SellerActivity.this,UserProfileActivity.class);
            intent1.putExtra("userID",userID);
            startActivityForResult(intent1, CHANGE_PROFILE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.seller_drow_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void allItemsForSale() {
        DBOperations dbOper = new DBOperations(SellerActivity.this);
        Cursor res = dbOper.gelAllItems();
        if(res == null){
            Toast.makeText(getApplicationContext(),"No image for sale!", Toast.LENGTH_SHORT).show();
            return;
        }
        RecyclerView recycler = (RecyclerView) findViewById(R.id.my_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CustomRecyclerViewSeller(this,res);
        //add adapter clicklisteners eventualno
        recycler.setAdapter(adapter);
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {

                Uri uriImg = data.getData();

                CropImage.activity(uriImg)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(100,100)
                        .setMaxCropResultSize(1281,1282)
                        .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();

                InputStream inputStream;

                try {
                    inputStream = getContentResolver().openInputStream(resultUri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    if (image.getWidth() > 1300 || image.getHeight() > 1300) {
                        Toast.makeText(getApplicationContext(), "Picture is too big!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent intent = new Intent(SellerActivity.this, UploadPreview.class);
                    intent.putExtra("userID", userID);
                    intent.putExtra("image", resultUri.toString());
                    startActivity(intent);

                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Unable to open image...", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        if(requestCode == CHANGE_PROFILE){
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "You canceled updates..", Toast.LENGTH_SHORT).show();
            }else if(resultCode == RESULT_OK){
                recreate();
            }
        }

    }

}
