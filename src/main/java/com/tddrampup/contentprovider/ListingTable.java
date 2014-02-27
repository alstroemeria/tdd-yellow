package com.tddrampup.contentprovider;

import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;

public class ListingTable {

    // Database table
    public static final String TABLE_LISTING = "listing";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_PROV= "prov";
    public static final String COLUMN_PCODE = "pcode";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATITUDE = "latitude";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LISTING
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_STREET + " text not null, "
            + COLUMN_CITY + " text not null, "
            + COLUMN_PROV + " text not null, "
            + COLUMN_PCODE + " text not null, "
            + COLUMN_PHONE + " text,"
            + COLUMN_LONGITUDE + " text not null, "
            + COLUMN_LATITUDE + " text not null "
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ListingTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_LISTING);
        onCreate(database);
    }
}