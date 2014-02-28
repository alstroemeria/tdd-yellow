package com.tddrampup.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tddrampup.R;
import com.tddrampup.contentprovider.ListingTable;
import com.tddrampup.models.Listing;


/**
 * Created by WX009-PC on 2/21/14.
 */
public class DetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    //private Listing mItem;

    private Listing mListing;
    private TextView nameTextView;
    private TextView locationTextView;
    private TextView websiteTextView;
    private Uri mlistingUri;

    public DetailFragment(Uri uri) {
        mlistingUri = uri;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        nameTextView = (TextView) rootView.findViewById(R.id.name_detail_fragment);
        locationTextView = (TextView) rootView.findViewById(R.id.location_detail_fragment);
        websiteTextView = (TextView) rootView.findViewById(R.id.website_detail_fragment);
        fillData();
        return rootView;
    }

    private void fillData() {
        String[] projection = { ListingTable.COLUMN_NAME,
                ListingTable.COLUMN_STREET};
        Cursor cursor = getActivity().getContentResolver().query(mlistingUri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_NAME)));
            locationTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_STREET)));
            //TODO: add more data above
            cursor.close();
        }
    }
}
