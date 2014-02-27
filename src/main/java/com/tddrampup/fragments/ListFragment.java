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
 * Created by WX009-PC on 2/19/14.
 */
public class ListFragment extends Fragment {

    private static final String mUrl = "http://api.sandbox.yellowapi.com/FindBusiness/?what=Restaurants&where=Toronto&pgLen=40&pg=1&dist=1&fmt=JSON&lang=en&UID=jkhlh&apikey=4nd67ycv3yeqtg97dku7m845";

    private ListView mListView;
    private ListingAdapter mListingAdapter;
    private LayoutInflater mLayoutInflater;
    private VolleyService volleyServiceLayer;

    public onListViewItemClickedListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mLayoutInflater = inflater;
        View rootView = mLayoutInflater.inflate(R.layout.list_fragment, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view);

        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onListViewItemClicked(position);
            }
        });

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

    public interface onListViewItemClickedListener {
        public void onListViewItemClicked(int position);
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
