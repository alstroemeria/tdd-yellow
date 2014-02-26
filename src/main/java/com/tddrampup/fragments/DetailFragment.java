package com.tddrampup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tddrampup.R;
import com.tddrampup.models.Listing;
import com.tddrampup.singletons.Listings;

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

    public DetailFragment() {
        //mListing = listing;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.

            // using la singleton for now.
            mListing = Listings.getInstance().getListing(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        nameTextView = (TextView) rootView.findViewById(R.id.name_detail_fragment);
        locationTextView = (TextView) rootView.findViewById(R.id.location_detail_fragment);
        websiteTextView = (TextView) rootView.findViewById(R.id.website_detail_fragment);
        populateTextViews();
        return rootView;
    }

    public void populateTextViews() {
        nameTextView.setText(mListing.getName());
        locationTextView.setText(mListing.getStreet() + ", " + mListing.getCity() + ", " + mListing.getProv() + ", " + mListing.getPcode());
        //websiteTextView.setText(mListing.getMerchantUrl());
    }
}
