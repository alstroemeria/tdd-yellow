package com.tddrampup.fragments;

import android.content.Context;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
    private TextView phoneTextView;
    private Uri mlistingUri;
    private GoogleMap map;

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
        phoneTextView = (TextView) rootView.findViewById(R.id.phone_detail_fragment);

        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMap();
        map.setMyLocationEnabled(true);
        fillData();

        return rootView;
    }

    private void fillData() {
        String[] projection = { ListingTable.COLUMN_NAME, ListingTable.COLUMN_LONGITUDE,
                ListingTable.COLUMN_LATITUDE, ListingTable.COLUMN_STREET,
                ListingTable.COLUMN_URL,ListingTable.COLUMN_PHONE};
        Cursor cursor = getActivity().getContentResolver().query(mlistingUri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_NAME)));
            locationTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_STREET)));
            websiteTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_URL)));
            phoneTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_PHONE)));
            LatLng currentCoordinates = new LatLng(
                    Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_LATITUDE))),
                    Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(ListingTable.COLUMN_LONGITUDE))));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 12));
            map.addMarker(new MarkerOptions().position(currentCoordinates));
            cursor.close();
        }
    }


}
