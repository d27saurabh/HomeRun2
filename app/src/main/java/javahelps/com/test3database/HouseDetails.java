package javahelps.com.test3database;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 11/4/2017.
 */

public class HouseDetails {

    private String address;
    private String county;
    private String state;
    private String zipcode;
    private String price;
    private float latval;
    private float longval;

    public HouseDetails (String address, String county, String state, String zipcode,String price){
        this.address = address;
        this.county = county;
        this.state  = state;
        this.zipcode = zipcode;
        this.price =  price;
        /*this.latval = latval;
        this.longval = longval;*/
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
