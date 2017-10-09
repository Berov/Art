package com.example.lukas.artgallerydrow.controller.Model;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukas.artgallerydrow.R;
import com.example.lukas.artgallerydrow.controller.Controller.BuyerActivity;

/**
 * Created by Lukas on 7.10.2017 г..
 */

public class CustomRecyclerViewBuyer extends RecyclerView.Adapter<CustomRecyclerViewBuyer.ViewHolder> {

    private Cursor customCursorItemsData;
    private LayoutInflater inflater;
    private  String money;
    private int buyerID;
    private Context ct;

    public CustomRecyclerViewBuyer(Cursor customCursorItemsData, Context ctx, String money, int buyerID) {
        this.customCursorItemsData = customCursorItemsData;
        this.inflater = LayoutInflater.from(ctx);
        this.money = money;
        this.buyerID=buyerID;
        this.ct = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_custom_row, parent, false);
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        customCursorItemsData.moveToPosition(position);

        final int sellerID = customCursorItemsData.getInt(7);
        String itemDescription = customCursorItemsData.getString(6);
        final String price = customCursorItemsData.getString(2);
        String title = customCursorItemsData.getString(1);
        byte[] image = customCursorItemsData.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        final  int itemID = customCursorItemsData.getInt(0);

        holder.itemTitle.setText(title);
        holder.itemPrice.setText("Price: " + price +"$");
        holder.itemDescription.setText("    " + itemDescription);
        holder.itemImage.setImageBitmap(bitmap);
        holder.itemButtonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double userMoney = Double.parseDouble(money);
                double itemMoney = Double.parseDouble(price);

                if(userMoney >= itemMoney){
                    double rest = userMoney-itemMoney;

                    changeBuyerMoney(buyerID, rest);
                    changeSellerMoney(sellerID, itemMoney);
                    setItemBuyerID(buyerID, itemID);
                    Intent intent = new Intent(ct, BuyerActivity.class);
                    intent.putExtra("userID",buyerID);
                    ct.startActivity(intent);

                }else{
                    Toast.makeText(ct, "Sorry! You have not enought money to buy this item!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void setItemBuyerID(int buyerID, int itemID) {
        DBOperations dbo = DBOperations.getInstance(ct);
        dbo.updateItemRow(itemID, buyerID);
    }

    private void changeSellerMoney(int sellerID, double itemMoney) {
        DBOperations dbo = DBOperations.getInstance(ct);
        dbo.updateSellerMoney(sellerID,itemMoney);
    }

    private void changeBuyerMoney(int buyerID, double rest) {
        DBOperations dbo = DBOperations.getInstance(ct);
        dbo.updateBuyerMoney(buyerID,rest);
    }

    @Override
    public int getItemCount() {
        return customCursorItemsData.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemTitle;
        private TextView itemDescription;
        private TextView itemPrice;
        private Button itemButtonBuy;

        public ViewHolder(View itemView) {
            super(itemView);

            itemImage = (ImageView) itemView.findViewById(R.id.item_image);
            itemTitle = (TextView) itemView.findViewById(R.id.item_title_text_view);
            itemDescription = (TextView) itemView.findViewById(R.id.item_description_text_view);
            itemPrice = (TextView) itemView.findViewById(R.id.item_price_text_view);
            itemButtonBuy = (Button) itemView.findViewById(R.id.item_btn_buy);

        }

    }
}
