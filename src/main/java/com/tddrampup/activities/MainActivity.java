package com.tddrampup.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tddrampup.R;
import com.tddrampup.contentprovider.ListingContentProvider;
import com.tddrampup.contentprovider.ListingTable;
import com.tddrampup.fragments.GoogleMapFragment;
import com.tddrampup.fragments.ListingsFragment;
import com.tddrampup.models.Listing;
import com.tddrampup.services.VolleyCallback;
import com.tddrampup.services.VolleyHelper;

import java.util.List;

public class MainActivity extends FragmentActivity implements ListingsFragment.onListViewItemClickedListener {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mMenuTitles;

    private VolleyHelper volleyServiceLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        volleyServiceLayer = new VolleyHelper(getApplicationContext());
        volleyServiceLayer.volleyServiceLayerCallback = new Callback();
        volleyServiceLayer.GetListings();

        setupDrawer();

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }

    private void setupDrawer() {
        mTitle = mDrawerTitle = getTitle();
        mMenuTitles = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onListViewItemClicked(long id) {
        volleyServiceLayer.GetListing(id);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        String item = getResources().getStringArray(R.array.menu_array)[position];
        FragmentManager transaction = getSupportFragmentManager();
        switch (position){
            case 0:
                transaction.beginTransaction()
                        .replace(R.id.content_frame,new ListingsFragment(),"LISTINGS_FRAGMENT")
                        .commit();
                break;
            case 1:
                transaction.beginTransaction()
                        .replace(R.id.content_frame, new GoogleMapFragment(), "MAP_FRAGMENT")
                        .commit();

                break;
        }

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mMenuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    class Callback implements VolleyCallback {
        public void listCallbackCall(List<Listing> listings) {
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
}
