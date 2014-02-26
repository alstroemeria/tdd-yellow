package com.tddrampup.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Listing{

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String street;
    @Expose
    private String city;
    @Expose
    private String prov;
    @Expose
    private String pcode;
    @Expose
    private String phone;
    @Expose
    private String geoCodeLongitude;
    @Expose
    private String geoCodeLatitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProv() {
        return prov;
    }

    public void setProv(String prov) {
        this.prov = prov;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGeoCodeLongitude() {
        return geoCodeLongitude;
    }

    public void setGeoCodeLongitude(String geoCodeLongitude) {
        this.geoCodeLongitude = geoCodeLongitude;
    }

    public String getGeoCodeLatitude() {
        return geoCodeLatitude;
    }

    public void setGeoCodeLatitude(String geoCodeLatitude) {
        this.geoCodeLatitude = geoCodeLatitude;
    }
}