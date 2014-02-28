package com.tddrampup.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.tddrampup.R;
import com.tddrampup.contentprovider.ListingContentProvider;
import com.tddrampup.contentprovider.ListingTable;
import com.tddrampup.fragments.ListingsFragment;
import com.tddrampup.models.Listing;
import com.tddrampup.services.VolleyCallback;
import com.tddrampup.services.VolleyHelper;

import java.util.List;

/**
 * Created by dx165-xl on 2014-02-28.
 */
public class SearchResultsActivity extends FragmentActivity {
    VolleyHelper volleyServiceLayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        getContentResolver().delete(ListingContentProvider.CONTENT_URI,null,null);
        handleIntent(getIntent());

        FragmentManager transaction = getSupportFragmentManager();
        transaction.beginTransaction().replace(R.id.content_frame,new ListingsFragment(),"LISTINGS_FRAGMENT").commit();
    }

    class Callback implements VolleyCallback {
        public void listCallbackCall(List<Listing> listings) {
            getContentResolver().delete(ListingContentProvider.CONTENT_URI,null,null);
            for (Listing listing:listings){
                insertListing(listing);
            }
        }

        @Override
        public void itemCallbackCall(Listing listing) {
            insertListing(listing);

            Intent detailIntent = new Intent(getApplicationContext(), DetailActivity.class);
            Uri todoUri = Uri.parse(ListingContentProvider.CONTENT_URI + "/" + listing.getId());
            detailIntent.putExtra(ListingContentProvider.CONTENT_ITEM_TYPE, todoUri);
            startActivity(detailIntent);
        }
    }

    private void insertListing(Listing listing) {
        if (listing!=null){
            ContentValues values = new ContentValues();
            values.put(ListingTable.COLUMN_ID,listing.getId());
            values.put(ListingTable.COLUMN_CITY,listing.getCity());
            values.put(ListingTable.COLUMN_STREET,listing.getStreet());
            values.put(ListingTable.COLUMN_PCODE,listing.getPcode());
            values.put(ListingTable.COLUMN_PROV,listing.getProv());
            values.put(ListingTable.COLUMN_NAME,listing.getName());
            values.put(ListingTable.COLUMN_PHONE,listing.getPhone());
            values.put(ListingTable.COLUMN_LATITUDE,listing.getLatitude());
            values.put(ListingTable.COLUMN_LONGITUDE,listing.getLongitude());
            values.put(ListingTable.COLUMN_PHONE,listing.getPhone());
            values.put(ListingTable.COLUMN_URL,listing.getUrl());
            Log.d("SQLITE", getContentResolver().insert(ListingContentProvider.CONTENT_URI, values).toString());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

            volleyServiceLayer = new VolleyHelper(getApplicationContext());
            volleyServiceLayer.volleyServiceLayerCallback = new Callback();
            volleyServiceLayer.GetListings(query,getMyLocation());

        }
    }

    private Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Location wasn't found, check the next most accurate place for the current location
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            // Finds a provider that matches the criteria
            String provider = lm.getBestProvider(criteria, true);
            // Use the provider to get the last known location
            myLocation = lm.getLastKnownLocation(provider);
        }

        return myLocation;
    }
}
