package com.tddrampup.fragments;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ListView;

import com.tddrampup.R;
import com.tddrampup.activities.MainActivity;
import com.tddrampup.adapters.ListingAdapter;
import com.tddrampup.models.Address;
import com.tddrampup.models.Listing;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static org.robolectric.Robolectric.shadowOf;

/**
 * Created by WX009-PC on 2/21/14.
 */
@RunWith(RobolectricTestRunner.class)
public class ListFragmentTest {
    private FragmentActivity mActivity;
    private ListFragment mListFragment;
    private  ArrayList<Listing> mListings;

    public void createFakeData() {
        Listing one = new Listing();
        Address addOne = new Address();
        addOne.setCity("Toronto");
        addOne.setStreet("Street");
        one.setName("One");
        one.setAddress(addOne);
        mListings = new ArrayList<Listing>();
        mListings.add(one);
    }

    private void addFragment(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mListFragment = new ListFragment();
        fragmentTransaction.add(mListFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.buildActivity(FragmentActivity.class).create().visible().get();
//        addFragment();
        createFakeData();
    }


    @Ignore
    @Test
    public void onListViewItemClickedListener_shouldCallOnListViewItemClicked() throws Exception {
//        MainActivity mainActivity = mock(MainActivity.class);
        MainActivity mainActivity = Robolectric.buildActivity(MainActivity.class).create().visible().get();
        addFragment(mainActivity);

        ListView listView = (ListView) mListFragment.getView().findViewById(R.id.list_view);
        ListingAdapter listingAdapter = new ListingAdapter(mainActivity.getLayoutInflater(), mListings);
        listView.setAdapter(listingAdapter);

        shadowOf(listView).performItemClick(0);
//        verify(mainActivity).onListViewItemClicked(0);
    }


    // TODO: populate list test
    // TODO: attach test
    // TODO: detach test
    // TODO: recycling views test
}