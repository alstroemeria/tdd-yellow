package com.tddrampup.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tddrampup.R;
import com.tddrampup.adapters.ListingAdapter;
import com.tddrampup.contentprovider.ListingContentProvider;
import com.tddrampup.contentprovider.ListingTable;
import com.tddrampup.services.VolleyService;
import com.tddrampup.singletons.Listings;

/**
 * Created by dx165-xl on 2014-02-27.
 */
public class ListingsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String mUrl = "http://api.sandbox.yellowapi.com/FindBusiness/?what=Restaurants&where=Toronto&pgLen=40&pg=1&dist=1&fmt=JSON&lang=en&UID=jkhlh&apikey=4nd67ycv3yeqtg97dku7m845";

    private ListView mListView;
    private ListingAdapter mListingAdapter;
    private LayoutInflater mLayoutInflater;
    private VolleyService volleyServiceLayer;
    private SimpleCursorAdapter adapter;
    public  onListViewItemClickedListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupAdapter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onListViewItemClickedListener) {
            mListener = (onListViewItemClickedListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // Opens the second activity if an entry is clicked
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mListener.onListViewItemClicked(id);
    }

    public interface onListViewItemClickedListener {
        public void onListViewItemClicked(long id);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection =  { ListingTable.COLUMN_CITY,
                ListingTable.COLUMN_NAME, ListingTable.COLUMN_PCODE,
                ListingTable.COLUMN_PROV, ListingTable.COLUMN_STREET,
                ListingTable.COLUMN_LATITUDE, ListingTable.COLUMN_LONGITUDE,
                ListingTable.COLUMN_ID };
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                ListingContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        adapter.swapCursor(null);
    }


    private void setupAdapter(){
//        mListingAdapter = new ListingAdapter(mLayoutInflater, Listings.getInstance().getListings());
//        mListView.setAdapter(mListingAdapter);
//        mListingAdapter.notifyDataSetChanged();

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { ListingTable.COLUMN_NAME, ListingTable.COLUMN_STREET };
        // Fields on the UI to which we map
        int[] to = new int[] { R.id.listing_title, R.id.listing_address };

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(getActivity().getApplicationContext(), R.layout.row_listview, null, from,
                to, 0);

        setListAdapter(adapter);
    }
}
