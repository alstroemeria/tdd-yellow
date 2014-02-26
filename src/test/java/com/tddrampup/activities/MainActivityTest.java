package com.tddrampup.activities;

import android.support.v4.app.Fragment;
import android.widget.Button;

import com.tddrampup.R;
import com.tddrampup.models.Address;
import com.tddrampup.models.Listing;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity activity;
    private  ArrayList<Listing> listings;

    public void createFakeData() {
        Listing one = new Listing();
        Address addOne = new Address();
        addOne.setCity("Toronto");
        addOne.setStreet("Street");
        one.setName("One");
        one.setAddress(addOne);
        listings = new ArrayList<Listing>();
        listings.add(one);
    }


    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        //createFakeData();
    }

    @Test
    public void onCreate_shouldDisplayHomeFragment() throws Exception {
        //Fragment homeFragment = activity.getSupportFragmentManager().findFragmentById(R.id.list_fragment);
        //assertThat(homeFragment).isVisible();
    }
//
//    @Test
//    public void onListButtonClicked_shouldOpenListFragment() throws Exception {
//        Button listButton = (Button) activity.findViewById(R.id.list_button);
//        listButton.performClick();
//        Fragment listFragment = activity.getFragmentManager().findFragmentByTag("MY_LIST_FRAGMENT");
//        assertThat(listFragment).isVisible();
//    }

    @Ignore
    @Test
    public void onMapButtonClicked_shouldOpenMapFragment() throws Exception {
        Button mapButton = (Button) activity.findViewById(R.id.map_button);
        mapButton.performClick();
        Fragment mapFragment = activity.getSupportFragmentManager().findFragmentByTag("MY_MAP_FRAGMENT");
        assertThat(mapFragment).isVisible();
    }

//    @Test
//    public void onListViewItemClicked_shouldOpenDetailFragment() throws Exception {
//        ListFragment listFragment = new ListFragment();
//        ListView listView = (ListView) listFragment.getView().findViewById(R.id.list_view);
//        ListingAdapter listingAdapter = new ListingAdapter(activity.getLayoutInflater(), listings);
//        listView.setAdapter(listingAdapter);
//        shadowOf(listView).populateItems();
//        shadowOf(listView).performItemClick(0);
//        Fragment detailFragment = activity.getFragmentManager().findFragmentByTag("MY_DETAIL_FRAGMENT");
//        assertThat(detailFragment).isVisible();
//    }
}
