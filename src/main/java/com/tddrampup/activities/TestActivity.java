package com.tddrampup.activities;


import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.tddrampup.R;
import com.tddrampup.adapters.ListingAdapter;
import com.tddrampup.contentprovider.ListingsDataSource;
import com.tddrampup.models.Listing;
import com.tddrampup.singletons.Listings;

public class TestActivity extends ListActivity {
    private ListingsDataSource datasource;
    private ListingAdapter mListingAdapter;
    List<Listing> mValues;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fragment);

        datasource = new ListingsDataSource(this);
        datasource.open();

        mValues = datasource.getAllListings();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        //ArrayAdapter<Listing> adapter = new ArrayAdapter<Listing>(this,
        //       android.R.layout.simple_list_item_1, values);

        mListingAdapter = new ListingAdapter(getLayoutInflater(), mValues);

        setListAdapter(mListingAdapter);
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        Listing comment = null;
        switch (view.getId()) {
            case R.id.add:
                String[] comments = new String[] { "Cool", "Very nice", "Hate it" };
                int nextInt = new Random().nextInt(3);
                // save the new comment to the database
                comment = datasource.createListing(comments[nextInt]);
                mValues.add(comment);
                break;
            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    comment = (Listing) getListAdapter().getItem(0);
                    datasource.deleteListing(comment);
                    mValues.remove(comment);
                }
                break;
        }
        mListingAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}