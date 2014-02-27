package com.tddrampup.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tddrampup.R;
import com.tddrampup.adapters.ListingAdapter;
import com.tddrampup.models.Listing;
import com.tddrampup.services.VolleyService;
import com.tddrampup.services.VolleyServiceCallback;
import com.tddrampup.singletons.Listings;

import java.util.List;

/**
 * Created by dx165-xl on 2014-02-27.
 */
public class ListingsFragment extends Fragment {

    private static final String mUrl = "http://api.sandbox.yellowapi.com/FindBusiness/?what=Restaurants&where=Toronto&pgLen=40&pg=1&dist=1&fmt=JSON&lang=en&UID=jkhlh&apikey=4nd67ycv3yeqtg97dku7m845";

    private ListView mListView;
    private ListingAdapter mListingAdapter;
    private LayoutInflater mLayoutInflater;
    private VolleyService volleyServiceLayer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;
        View rootView = mLayoutInflater.inflate(R.layout.list_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view);


        if (Listings.getInstance().getListings().isEmpty()){
            volleyServiceLayer = new VolleyService(rootView.getContext());
            volleyServiceLayer.volleyServiceLayerCallback = new Callback();
            volleyServiceLayer.GetListings();
        }
        else{
            setupAdapter();
        }

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class Callback implements VolleyServiceCallback {
        public void listCallbackCall(List<Listing> listings) {
            Listings.getInstance().setListings(listings);
            setupAdapter();
        }

        @Override
        public void itemCallbackCall(Listing listing) {
            // do nothing
        }
    }

    private void setupAdapter(){
        mListingAdapter = new ListingAdapter(mLayoutInflater, Listings.getInstance().getListings());
        mListView.setAdapter(mListingAdapter);
        mListingAdapter.notifyDataSetChanged();
    }
}
