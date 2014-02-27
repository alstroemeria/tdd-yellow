package com.tddrampup.services;

import com.tddrampup.models.Listing;
import java.util.List;

/**
 * Created by WX009-PC on 2/20/14.
 */
public interface VolleyServiceCallback {
    void listCallbackCall(List<Listing> listings);
    void itemCallbackCall(Listing listing);
}
