package com.example.lukas.artgallerydrow.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lukas.artgallerydrow.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UploadPreview extends AppCompatActivity {

    private Button btnCancel, btnUpload;

    private EditText txtTitle, txtPrice, txtDesc;

    private ImageView imgPreview;

    private Spinner spKind, spType;

    private String type, subtype;

    private int userID;
    private double price;
    private byte[] imageByte;

    private boolean isTitleTrue, isDescTrue, isPriceTrue = false;

    private Uri uri;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_preview);
        userID = getIntent().getExtras().getInt("userID");

        txtTitle = (EditText) findViewById(R.id.txtTitlePreview);
        txtTitleCheck();

        txtPrice = (EditText) findViewById(R.id.txtPricePreview);
        txtPriceCheck();

        txtDesc = (EditText) findViewById(R.id.txtDescPreview);
        txtDescCheck();

        spKind = (Spinner) findViewById(R.id.spKindItem);
        spType = (Spinner) findViewById(R.id.spTypeItem);

        ArrayAdapter<CharSequence> arrKindsAdapter = ArrayAdapter.createFromResource(this, R.array.itemKinds, android.R.layout.simple_spinner_item);
        arrKindsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spKind.setAdapter(arrKindsAdapter);
        spKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // (TextView)view.getText(); -vzima imeto na itema
                type = ((TextView) view).getText().toString();
                String[] strArrForSelItem = testSubtypesByType(type);
                ArrayAdapter ad1 = new ArrayAdapter(UploadPreview.this, android.R.layout.simple_spinner_item, strArrForSelItem);
                ad1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spType.setAdapter(ad1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subtype = ((TextView) view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgPreview = (ImageView) findViewById(R.id.imagePreview);
        uri = Uri.parse(getIntent().getExtras().getString("image"));

        progress = ProgressDialog.show(this, "Please wait", "Loading image...", true);
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream inputStream;

                    inputStream = getContentResolver().openInputStream(uri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);

                    imageByte = Validator.getBytes(image);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.dismiss();
                        }
                    });

                } catch (final Exception ex) {

                }
            }
        }.start();

        imgPreview.setImageURI(uri);

        btnUpload = (Button) findViewById(R.id.btnSaveUpload);
        btnUploadListener();

        btnCancel = (Button) findViewById(R.id.btnCancelSave);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void btnUploadListener() {
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadItem(txtTitle.getText().toString(), price, imageByte, type, subtype, txtDesc.getText().toString(), userID);
            }
        });
    }

    private void uploadItem(String title, double priceItem, byte[] img, String type, String subtype, String desc, int sellerId) {
        if (isTitleTrue && isPriceTrue && isDescTrue) {
            BackgroundDBTasks bt = new BackgroundDBTasks(UploadPreview.this);
            bt.execute("uploadImage", null, title, priceItem, img, type, subtype, desc, sellerId);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "You have invalid fields!", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void txtDescCheck() {
        txtDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 200) {
                    isDescTrue = true;
                } else {
                    txtDesc.setError("Invalid field..");
                    isDescTrue = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void txtPriceCheck() {
        txtPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().matches("[0-9.]+") && s.length() <= 9) {
                    isPriceTrue = true;
                    price = Double.parseDouble(txtPrice.getText().toString());
                } else {
                    txtPrice.setError("Invalid field..");
                    isPriceTrue = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void txtTitleCheck() {
        txtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!(s.toString().matches("[a-zA-Z0-9 ]+"))) {
                    txtTitle.setError("Invalid field...");
                    isTitleTrue = false;
                } else {
                    isTitleTrue = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String[] testSubtypesByType(String type) {
        DBOperations dbOper = new DBOperations(UploadPreview.this);
        Cursor res = dbOper.selectSubtypeForSpecType(type);

        ArrayList<String> mStringList = new ArrayList<>();

        while (res.moveToNext()) {
            mStringList.add(res.getString(0));
        }

        String[] stockArr = new String[mStringList.size()];
        stockArr = mStringList.toArray(stockArr);

        return stockArr;

    }


    @Override
    public void onPause() {
        super.onPause();
        if (progress != null)
            progress.dismiss();
    }
}
