package com.tddrampup.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.tddrampup.models.Listing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jacky on 27/02/14.
 */
public class ListingsDataSource {
    // Database fields

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME };


    public ListingsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Listing createListing(String name) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Listing newListing = cursorToListing(cursor);
        cursor.close();
        return newListing;
    }

    public void deleteListing(Listing listing) {
        long id = listing.getId();
        System.out.println("Listing deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Listing> getAllListings() {
        List<Listing> comments = new ArrayList<Listing>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Listing listing = cursorToListing(cursor);
            comments.add(listing);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Listing cursorToListing(Cursor cursor) {
        Listing listing = new Listing();
        listing.setId(cursor.getLong(0));
        listing.setName(cursor.getString(1));
        return listing;
    }
}