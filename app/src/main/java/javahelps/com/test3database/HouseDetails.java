package javahelps.com.test3database;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by admin on 11/4/2017.
 */

public class HouseDetails {

    private String address;
    private String locality;
    private String state;
    private String zipcode;
    private Double price ;
//    private Double latval = 0.0;
//    private Double longval = 0.0;


    public HouseDetails(String address, String locality, String state, String zipcode, Double price) {
        this.address = address;
        this.locality = locality;
        this.state = state;
        this.zipcode = zipcode;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public Double getPrice() {
        return price;
    }
}
