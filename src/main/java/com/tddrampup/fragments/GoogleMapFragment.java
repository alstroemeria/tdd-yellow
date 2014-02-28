package com.tddrampup.fragments;

import android.content.Context;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tddrampup.R;
import com.tddrampup.contentprovider.ListingContentProvider;
import com.tddrampup.contentprovider.ListingTable;
import com.tddrampup.models.Listing;

import java.util.List;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class GoogleMapFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private List<Listing> mListings;
    private GoogleMap map;
    private boolean isFirstLoad = true;

    public GoogleMapFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.google_map_fragment, container, false);
            map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMap();
            getActivity().getSupportLoaderManager().initLoader(0, null, this);

            map.setMyLocationEnabled(true);
            Location currentLocation = getMyLocation();
            if(currentLocation!=null){
                LatLng currentCoordinates = new LatLng(
                        currentLocation.getLatitude(),
                        currentLocation.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentCoordinates, 10));
            }

        } catch (InflateException e) {

        }
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Fragment fragment = getFragmentManager().findFragmentById(R.id.google_map);

        if(!getActivity().isFinishing()&& fragment!=null) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection =  { ListingTable.COLUMN_NAME, ListingTable.COLUMN_LATITUDE,
                ListingTable.COLUMN_LONGITUDE, ListingTable.COLUMN_ID };
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                ListingContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()){
            do{
                Double lat = Double.parseDouble(data.getString(data.getColumnIndex(ListingTable.COLUMN_LATITUDE)));
                Double log = Double.parseDouble(data.getString(data.getColumnIndex(ListingTable.COLUMN_LONGITUDE)));
                String name = data.getString(data.getColumnIndex(ListingTable.COLUMN_NAME));
                LatLng coordinates = new LatLng(lat,log);
                map.addMarker(new MarkerOptions().position(coordinates).title(name));
            }while(data.moveToNext());
        }
        //data.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private Location getMyLocation() {
        // Get location from GPS if it's available
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
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
