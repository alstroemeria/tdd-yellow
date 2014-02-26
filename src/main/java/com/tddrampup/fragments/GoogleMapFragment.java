package com.tddrampup.fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tddrampup.R;
import com.tddrampup.models.Listing;

import java.util.List;

/**
 * Created by WX009-PC on 2/19/14.
 */
public class GoogleMapFragment extends Fragment {

    private List<Listing> mListings;
    private GoogleMap map;
    private boolean isFirstLoad = true;

    public GoogleMapFragment(List<Listing> listings) {
        mListings = listings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.google_map_fragment, container, false);

        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.google_map)).getMap();
        map.setMyLocationEnabled(true);
        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if(isFirstLoad) {
                    Location myLocation = map.getMyLocation();
                    if (myLocation != null) {
                        LatLng myLatLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 15.f));
                        isFirstLoad = false;
                    }
                }
            }
        });
        addMarkers();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //Fragment fragment = getFragmentManager().findFragmentById(R.id.google_map);
        //if (fragment != null)
        //   getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    public void addMarkers() {
        for(Listing tempListing : mListings) {
            if (tempListing.getGeoCodeLatitude()!=null ) {
                LatLng coordinates = new LatLng(Double.parseDouble(tempListing.getGeoCodeLatitude().toString()), Double.parseDouble(tempListing.getGeoCodeLongitude().toString()));
                map.addMarker(new MarkerOptions().position(coordinates).title(tempListing.getName()));
            }
        }
    }
}
