package com.tddrampup.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.Button;

import com.tddrampup.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class HomeFragmentTest {
    private Activity activity;
    private HomeFragment homeFragment;
    private Button listButton;
    private Button mapButton;

    private void addFragment() {
        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(homeFragment, null);
        fragmentTransaction.commit();
    }

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(Activity.class).create().visible().get();
        addFragment();
        homeFragment.mListener = mock(HomeFragment.onItemClickedListener.class);
        listButton = (Button) homeFragment.getView().findViewById(R.id.list_button);
        mapButton = (Button) homeFragment.getView().findViewById(R.id.map_button);
    }

    @Test
    public void ListButtonClick_shouldCallListClickListener() {
        //Robolectric.clickOn(listButton);
        listButton.performClick();
        verify(homeFragment.mListener).onListButtonClicked();
    }

    @Test
    public void MapButtonClick_shouldCallMapClickListener() {
        //Robolectric.clickOn(listButton);
        mapButton.performClick();
        verify(homeFragment.mListener).onMapButtonClicked();
    }

    // TODO: on attach test
    // TODO: on detach test
}
