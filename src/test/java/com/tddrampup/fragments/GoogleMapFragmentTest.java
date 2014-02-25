package com.tddrampup.fragments;



import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tddrampup.models.Listing;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

/**
 * Created by WX009-PC on 2/21/14.
 */
@Ignore
@RunWith(RobolectricTestRunner.class)
public class GoogleMapFragmentTest {
    private FragmentActivity activity;
    private GoogleMapFragment googleMapFragment;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        googleMapFragment = new GoogleMapFragment(null);
        fragmentTransaction.add(googleMapFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(FragmentActivity.class).create().visible().get();
        addFragment();
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
//        assertTrue(googleMapFragment != null);
    }
}