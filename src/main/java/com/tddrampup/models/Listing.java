package com.tddrampup.models;

import com.google.gson.annotations.Expose;

public class Listing{

    @Expose
    private Long id;
    @Expose
    private String yellowId;
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
    private String longitude;
    @Expose
    private String latitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYellowId() {
        return yellowId;
    }

    public void setYellowId(String yellowId) {
        this.yellowId = yellowId;
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}