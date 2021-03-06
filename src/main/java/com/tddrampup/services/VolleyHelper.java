package com.tddrampup.services;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tddrampup.models.Listing;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by WX009-PC on 2/20/14.
 */
public class VolleyHelper {

    private RequestQueue mRequestQueue;
    public VolleyCallback volleyServiceLayerCallback;

    public VolleyHelper(Context context){
        mRequestQueue =  Volley.newRequestQueue(context);
    }

    public void GetListings(String search, Location location) {
        Log.d("location", location.getLatitude()+ " " + location.getLongitude());


        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,"http://api.sandbox.yellowapi.com/FindBusiness/?what=" +search +
                "&where=cZ%3C"+ location.getLongitude() +"%3E%2C%3C"+ location.getLatitude() +"%3E&&pgLen=40&pg=1&dist=1&fmt=JSON&lang=en&UID=fakeUID&apikey=c56ta8h34znvqzkqaspjexar" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JsonParser parser = new JsonParser();
                JsonArray listings = parser.parse(response.toString()).getAsJsonObject().getAsJsonObject().getAsJsonArray("listings");
                ArrayList<Listing> myListings = new ArrayList<Listing>();

                for(int i =0; i < listings.size(); i++){
                    JsonObject rawListing = listings.get(i).getAsJsonObject();
                    Listing listing = parseListing(rawListing);
                    if (listing != null){
                        myListings.add(listing);
                    }
                }
                volleyServiceLayerCallback.listCallbackCall(myListings);
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGGING:", error.toString());
            }
        });
        mRequestQueue.add(jr);

    }

    public void GetListing(Long id) {
        String url ="http://api.sandbox.yellowapi.com/GetBusinessDetails/?listingId="+ id +"&bus-name=Yellow-Pages-Group&city=Toronto&prov=Ontario&fmt=JSON&lang=en&UID=fgdfg&apikey=4nd67ycv3yeqtg97dku7m845";
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JsonParser parser = new JsonParser();
                JsonObject listing = parser.parse(response.toString()).getAsJsonObject();
                volleyServiceLayerCallback.itemCallbackCall(parseDetailedListing(listing));
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGGING:", error.getMessage());
            }
        });
        mRequestQueue.add(jr);

    }

    private Listing parseListing(JsonObject rawListing) {
        Gson gson = new Gson();
        try {
            Listing myListing = gson.fromJson(rawListing, Listing.class);
            myListing.setStreet(rawListing.getAsJsonObject().getAsJsonObject("address").getAsJsonPrimitive("street").getAsString());
            myListing.setCity(rawListing.getAsJsonObject().getAsJsonObject("address").getAsJsonPrimitive("city").getAsString());
            myListing.setProv(rawListing.getAsJsonObject().getAsJsonObject("address").getAsJsonPrimitive("prov").getAsString());
            myListing.setPcode(rawListing.getAsJsonObject().getAsJsonObject("address").getAsJsonPrimitive("pcode").getAsString());
            myListing.setLatitude(rawListing.getAsJsonObject().getAsJsonObject("geoCode").getAsJsonPrimitive("latitude").getAsString());
            myListing.setLongitude(rawListing.getAsJsonObject().getAsJsonObject("geoCode").getAsJsonPrimitive("longitude").getAsString());
            return myListing;
        }
        catch(Exception e){
            Log.d("Volley Service", e.toString());
            return null;
        }
    }

    private Listing parseDetailedListing(JsonObject rawListing) {
        Listing myListing = parseListing(rawListing);
        try {
            myListing.setPhone(rawListing.getAsJsonObject().getAsJsonArray("phones").get(0).getAsJsonObject().getAsJsonPrimitive("dispNum").getAsString());

            JsonArray webUrl = rawListing.getAsJsonObject().getAsJsonObject("products").getAsJsonArray("webUrl");

            if (webUrl.size()>0){
                myListing.setUrl(webUrl.get(0).getAsString());
            }
        }
        catch(Exception e){
            Log.d("Volley Service", e.toString());
        }
        return myListing;

    }
}

