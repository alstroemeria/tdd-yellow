package com.tddrampup.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.tddrampup.R;
import com.tddrampup.fragments.ListingsFragment;

/**
 * Created by dx165-xl on 2014-02-28.
 */
public class SearchResultsActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentManager transaction = getSupportFragmentManager();
        transaction.beginTransaction().replace(R.id.content_frame,new ListingsFragment(),"LISTINGS_FRAGMENT").commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

        }
    }
}
