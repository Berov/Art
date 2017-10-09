package com.example.lukas.artgallerydrow.controller.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lukas.artgallerydrow.controller.Utils.GalleryConnector;

/**
 * Created by plame_000 on 29-Sep-17.
 */

public class DBOperations extends SQLiteOpenHelper {
    private static DBOperations sInstance = null;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Gallery.db";

    private static final String USER_QUERY = "create table "
            + GalleryConnector.User.TABLE_NAME + "("
            + GalleryConnector.User.ID + " integer primary key,"
            + GalleryConnector.User.ADDRESS + " text,"
            + GalleryConnector.User.NAME + " text,"
            + GalleryConnector.User.EMAIL + " text,"
            + GalleryConnector.User.PASS + " text,"
            + GalleryConnector.User.TYPE + " text,"
            + GalleryConnector.User.MONEY + " real,"
            + GalleryConnector.User.IMG + " blob);";

    private static final String TYPE_QUERY = "create table "
            + GalleryConnector.Type.TABLE_NAME + "("
            + GalleryConnector.Type.ID + " integer primary key,"
            + GalleryConnector.Type.Type + " text);";

    private static final String SUBTYPE_QUERY = "create table "
            + GalleryConnector.SubType.TABLE_NAME + "("
            + GalleryConnector.SubType.ID + " integer primary key,"
            + GalleryConnector.SubType.SUBTYPE + " text,"
            + GalleryConnector.SubType.TYPE_ID + " integer,"
            + "FOREIGN KEY (" + GalleryConnector.SubType.TYPE_ID + ") REFERENCES " + GalleryConnector.Type.TABLE_NAME + "(" + GalleryConnector.Type.ID + "));";

    private static final String ITEMS_QUERY = "create table "
            + GalleryConnector.Items.TABLE_NAME + "("
            + GalleryConnector.Items.ID + " integer primary key,"
            + GalleryConnector.Items.TITLE + " text,"
            + GalleryConnector.Items.PRICE + " real,"
            + GalleryConnector.Items.IMG + " blob,"
            + GalleryConnector.Items.TYPE_ITEM + " text,"
            + GalleryConnector.Items.SUBTYPE_ITEM + " text,"
            + GalleryConnector.Items.DESCRIPTION + " text,"
            + GalleryConnector.Items.SELLER_ID + " integer,"
            + GalleryConnector.Items.BUYER_ID + " integer,"
            + "FOREIGN KEY (" + GalleryConnector.Items.SELLER_ID + ") REFERENCES " + GalleryConnector.User.TABLE_NAME + "(" + GalleryConnector.User.ID + "), FOREIGN KEY ("
            + GalleryConnector.Items.BUYER_ID + ") REFERENCES " + GalleryConnector.User.TABLE_NAME + "(" + GalleryConnector.User.ID + "));";

    private DBOperations(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBOperations getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBOperations(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.execSQL(USER_QUERY);
        db.execSQL(TYPE_QUERY);
        db.execSQL(SUBTYPE_QUERY);
        db.execSQL(ITEMS_QUERY);
        insertQueries(db);
    }

    private void insertQueries(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + GalleryConnector.Type.TABLE_NAME + " values(1, 'graphics');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(2, 'painting');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(3, 'sculpture');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(4, 'wood carving');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(5, 'ceramics');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(6, 'glassware');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(7, 'textiles');");
        db.execSQL("insert into " + GalleryConnector.Type.TABLE_NAME + " values(8, 'iconography');");

        db.execSQL("insert into Subtypes values(null, 'linocut', 1)");
        db.execSQL("insert into Subtypes values(null, 'screen printing', 1)");
        db.execSQL("insert into Subtypes values(null, 'etching', 1)");
        db.execSQL("insert into Subtypes values(null, 'aquarelle', 2)");
        db.execSQL("insert into Subtypes values(null, 'oil painting', 2)");
        db.execSQL("insert into Subtypes values(null, 'acrylic painting', 2)");
        db.execSQL("insert into Subtypes values(null, 'charcoal', 2)");
        db.execSQL("insert into Subtypes values(null, 'pencil painting', 2)");
        db.execSQL("insert into Subtypes values(null, 'pastel', 2)");
        db.execSQL("insert into Subtypes values(null, 'stone', 3)");
        db.execSQL("insert into Subtypes values(null, 'metal', 3)");
        db.execSQL("insert into Subtypes values(null, 'gypsum', 3)");
        db.execSQL("insert into Subtypes values(null, 'classic wood carving', 4)");
        db.execSQL("insert into Subtypes values(null, 'modern carving', 4)");
        db.execSQL("insert into Subtypes values(null, 'interior carving', 4)");
        db.execSQL("insert into Subtypes values(null, 'souvenir woodcarving', 4)");
        db.execSQL("insert into Subtypes values(null, 'wall panel', 5)");
        db.execSQL("insert into Subtypes values(null, 'souvenir ceramics', 5)");
        db.execSQL("insert into Subtypes values(null, 'household ceramics', 5)");
        db.execSQL("insert into Subtypes values(null, 'stained glass', 6)");
        db.execSQL("insert into Subtypes values(null, 'household glass sculpture', 6)");
        db.execSQL("insert into Subtypes values(null, 'souvenir glass', 6)");
        db.execSQL("insert into Subtypes values(null, 'jewelery', 6)");
        db.execSQL("insert into Subtypes values(null, 'clothes', 7)");
        db.execSQL("insert into Subtypes values(null, 'accessories', 7)");
        db.execSQL("insert into Subtypes values(null, 'textile panel', 7)");
        db.execSQL("insert into Subtypes values(null, 'carpets', 7)");
        db.execSQL("insert into Subtypes values(null, 'new style', 8)");
        db.execSQL("insert into Subtypes values(null, 'old style', 8)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNewItem(SQLiteDatabase db, String title, Double price, byte[] img, String type, String subType, String desc, Integer sellerId) {
        ContentValues values = new ContentValues();
        values.putNull(GalleryConnector.Items.ID);
        values.put(GalleryConnector.Items.TITLE, title);
        values.put(GalleryConnector.Items.PRICE, price);
        values.put(GalleryConnector.Items.IMG, img);
        values.put(GalleryConnector.Items.TYPE_ITEM, type);
        values.put(GalleryConnector.Items.SUBTYPE_ITEM, subType);
        values.put(GalleryConnector.Items.DESCRIPTION, desc);
        values.put(GalleryConnector.Items.SELLER_ID, sellerId);
        // beshe samo za test :D values.put(GalleryConnector.Items.BUYER_ID,1);
        values.putNull(GalleryConnector.Items.BUYER_ID);
        db.insert(GalleryConnector.Items.TABLE_NAME, null, values);
    }

    public void addUserInfo(SQLiteDatabase db, String address, String name, String email, String password, String type, double money) {
        ContentValues values = new ContentValues();
        values.putNull(GalleryConnector.User.ID);
        values.put(GalleryConnector.User.ADDRESS, address);
        values.put(GalleryConnector.User.NAME, name);
        values.put(GalleryConnector.User.EMAIL, email);
        values.put(GalleryConnector.User.PASS, password);
        values.put(GalleryConnector.User.TYPE, type);
        values.put(GalleryConnector.User.MONEY, money);
        db.insert(GalleryConnector.User.TABLE_NAME, null, values);
    }

    public void updateItemRow(int itemID, int buyerID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GalleryConnector.Items.BUYER_ID,buyerID);
        db.update(GalleryConnector.Items.TABLE_NAME,values,"id="+itemID,null);
    }

    public void updateBuyerMoney(int buyerID, double resMoney){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GalleryConnector.User.MONEY,resMoney);
        db.update(GalleryConnector.User.TABLE_NAME,values,"id="+buyerID,null);
    }

    public void updateSellerMoney(int sellerID, double itemMoney) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + GalleryConnector.User.TABLE_NAME + " WHERE Users.id IS '"+sellerID+"'", null);
        String money = "-1";
        while (res.moveToNext()){
            money = res.getString(6);
        }
        double userCurrentMoney = Double.parseDouble(money);
        double moneyForUpdate = userCurrentMoney + itemMoney;
        ContentValues values = new ContentValues();
        values.put(GalleryConnector.User.MONEY,moneyForUpdate);
        db.update(GalleryConnector.User.TABLE_NAME,values,"id="+sellerID,null);
    }

    public void updateUserProfile(SQLiteDatabase db, Integer id, String address, String username, String pass, byte[] img) {
        ContentValues values = new ContentValues();
        values.put(GalleryConnector.User.ADDRESS, address);
        values.put(GalleryConnector.User.NAME, username);
        values.put(GalleryConnector.User.PASS, pass);
        if (img == null) {
            values.putNull(GalleryConnector.User.IMG);
        } else {
            values.put(GalleryConnector.User.IMG, img);
        }
        db.update(GalleryConnector.User.TABLE_NAME, values, "id=" + id, null);
    }

    public Cursor testGetUserInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + GalleryConnector.User.TABLE_NAME, null);
        return res;
    }

    public Cursor testGetTypeInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + GalleryConnector.Type.TABLE_NAME, null);
        return res;
    }

    public Cursor testGetSubTypeInfo() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + GalleryConnector.SubType.TABLE_NAME, null);
        return res;
    }

    public Cursor selectSubtypeForSpecType(String type) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT subtype FROM Subtypes join Types on Subtypes.type_id = Types.id WHERE Types.type = '" + type + "'";
        Cursor res = db.rawQuery(sql, null);
        return res;
    }

    public Cursor getSoldItems(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + GalleryConnector.Items.TABLE_NAME + " WHERE Items.buyer_id IS NOT null AND Items.seller_id IS '"+ id +"'";
        Cursor res = db.rawQuery(sql, null);
        return res;
    }
    public Cursor gelAllItems(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + GalleryConnector.Items.TABLE_NAME + " WHERE Items.buyer_id IS null AND Items.seller_id IS '"+ id +"'";
        Cursor res = db.rawQuery(sql, null);

        return res;
    }

    public Cursor getUserName(int userID) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT name FROM " + GalleryConnector.User.TABLE_NAME + " WHERE Users.id IS '" + userID + "'";
        Cursor res = db.rawQuery(sql, null);
        return res;
    }

    public Cursor checkUserForImage(int userID) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT img FROM " + GalleryConnector.User.TABLE_NAME + " WHERE Users.id IS '" + userID + "'";
        Cursor res = db.rawQuery(sql, null);
        return res;
    }

    public Cursor getItemsForSaleByType(String type) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Items WHERE Items.typeItem = '" + type + "' AND Items.buyer_id IS null";
        Cursor result = db.rawQuery(sql, null);
        return result;

    }

    public Cursor getItemsForSaleBySubtype(String subtype) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Items WHERE Items.subtypeItem = '" + subtype + "' AND Items.buyer_id IS null";
        Cursor result = db.rawQuery(sql, null);
        return result;

    }

    public Cursor getInfoForUserByID(int id){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM Users WHERE Users.id = '" + id +  "'";
        Cursor res = db.rawQuery(sql,null);
        return res;
    }

}
