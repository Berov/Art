package com.example.lukas.artgallerydrow.controller.Model;

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

    private Cursor mData;
    private LayoutInflater mInflater;

    public CustomRecyclerViewSeller(Context ctx, Cursor data){
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mData.moveToPosition(position);

        String title = mData.getString(1);
        String type = mData.getString(4);
        String subType = mData.getString(5);
        String desc = mData.getString(6);
        String price = mData.getString(2);
        byte[] image = mData.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        holder.txtCustomTitle.setText("Title: "+title);
        holder.txtType.setText(type + " ");
        holder.txtSubType.setText(subType + " ");
        holder.txtCustomDesc.setText(desc);
        holder.txtCustomPrice.setText("Price:" + price+"$");
        holder.imgCustomRow.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mData.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtCustomTitle;
        private TextView txtCustomDesc;
        private TextView txtCustomPrice;
        private ImageView imgCustomRow;
        private TextView txtType;
        private TextView txtSubType;

        public ViewHolder(View itemView) {
            super(itemView);

            txtCustomTitle = (TextView) itemView.findViewById(R.id.title_row);
            txtCustomDesc = (TextView) itemView.findViewById(R.id.desc_row);
            txtCustomPrice = (TextView) itemView.findViewById(R.id.desc_price);
            imgCustomRow = (ImageView) itemView.findViewById(R.id.img_row);
            txtType = (TextView) itemView.findViewById(R.id.type_row);
            txtSubType = (TextView) itemView.findViewById(R.id.subtype_row);

        }

    }
}
