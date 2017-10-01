package com.example.lukas.artgallerydrow.controller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lukas.artgallerydrow.R;

import org.w3c.dom.Text;

/**
 * Created by plame_000 on 30-Sep-17.
 */

public class CustomRecyclerView extends RecyclerView.Adapter<CustomRecyclerView.ViewHolder> {

    private Cursor mData; // data to show
    private LayoutInflater mInflater;

    public CustomRecyclerView(Context ctx, Cursor data){
        this.mData = data;
        this.mInflater = LayoutInflater.from(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_row_recycler,parent,false);
        ViewHolder viewHold = new ViewHolder(view);
        return viewHold;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mData.moveToPosition(position);
        // napravi si byte to img za setvane na image s bitmapFactory
       // byte[] = currImg = mData.getBlob(X);
        //String desc = mData.getString(

    }

    @Override
    public int getItemCount() {
        return mData.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtCustomTitle;
        public TextView txtCustomDesc;
        public TextView txtCustomPrice;
        public ImageView imgCustomRow;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCustomTitle = (TextView) itemView.findViewById(R.id.title_row);
            txtCustomDesc = (TextView) itemView.findViewById(R.id.desc_row);
            txtCustomPrice = (TextView) itemView.findViewById(R.id.desc_price);
            imgCustomRow = (ImageView) itemView.findViewById(R.id.img_row);
            // vij za click listener

        }

    }
}
