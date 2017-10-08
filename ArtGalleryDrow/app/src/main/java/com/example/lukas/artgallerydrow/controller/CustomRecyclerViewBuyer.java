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
 * Created by Lukas on 7.10.2017 г..
 */

public class CustomRecyclerViewBuyer extends RecyclerView.Adapter<CustomRecyclerViewBuyer.ViewHolder> {

    private Cursor customCursorItemsData;
    private LayoutInflater inflater;

    public CustomRecyclerViewBuyer(Cursor customCursorItemsData, Context ctx) {
        this.customCursorItemsData = customCursorItemsData;
        this.inflater = LayoutInflater.from(ctx);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_custom_row, parent, false);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        customCursorItemsData.moveToPosition(position);


        //provi id-tata na tablicata
        String price = customCursorItemsData.getString(2);
        byte[] image = customCursorItemsData.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        holder.itemTitle.setText("Някаква проба");
        holder.itemImage.setImageBitmap(bitmap);
// prihvashtam info ot kursora i se setva  na holdera

    }

    @Override
    public int getItemCount() {
        return customCursorItemsData.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemTitle;
//deklariram vsi`ko ot custom row

        public ViewHolder(View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title_text_view);
//TODO hvashtam po id deklariranite gore

        }


    }
}
