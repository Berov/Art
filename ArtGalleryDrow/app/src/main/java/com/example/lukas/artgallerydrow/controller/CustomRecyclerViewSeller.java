package com.example.lukas.artgallerydrow.controller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lukas.artgallerydrow.R;

/**
 * Created by plame_000 on 30-Sep-17.
 */

public class CustomRecyclerViewSeller extends RecyclerView.Adapter<CustomRecyclerViewSeller.ViewHolder> {

    private Cursor mData; // data to show
    private LayoutInflater mInflater;

    public CustomRecyclerViewSeller(Context ctx, Cursor data){
        this.mData = data;
        this.mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //setvame view-to na custom reda v custom_row_recycler / xml-a na itema
        View view = mInflater.inflate(R.layout.custom_row_recycler,parent,false);
        ViewHolder viewHold = new ViewHolder(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData.moveToPosition(position);

        String title = mData.getString(1);
        String desc = mData.getString(6);
        String price = mData.getString(2);
        byte[] image = mData.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        holder.txtCustomTitle.setText(title);
        holder.txtCustomDesc.setText(desc);
        holder.txtCustomPrice.setText(price);
        holder.imgCustomRow.setImageBitmap(bitmap);

//        buff.append("ID: " + res.getString(0) + "\n");
//        buff.append("Title: " + res.getString(1) + "\n");
//        buff.append("Price: " + res.getString(2) + "\n");
//        buff.append("Type: " + res.getString(4) + "\n");
//        buff.append("SubType: " + res.getString(5) + "\n");
//        buff.append("Desc: " + res.getString(6) + "\n");
//        buff.append("SellerID: " + res.getString(7) + "\n");
//        buff.append("Buyer_id: " + res.getString(8) + "\n\n");

    }

    @Override
    public int getItemCount() {
        return mData.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // setvash si vsi4ki poleta ot custom_row_recycler(tvoq)
        public TextView txtCustomTitle;
        public TextView txtCustomDesc;
        public TextView txtCustomPrice;
        public ImageView imgCustomRow;

        public ViewHolder(View itemView) {
            super(itemView);

            //tuk si im prihvashtash id-tata
            txtCustomTitle = (TextView) itemView.findViewById(R.id.title_row);
            txtCustomDesc = (TextView) itemView.findViewById(R.id.desc_row);
            txtCustomPrice = (TextView) itemView.findViewById(R.id.desc_price);
            imgCustomRow = (ImageView) itemView.findViewById(R.id.img_row);
            // vij za click listener

        }

    }
}
