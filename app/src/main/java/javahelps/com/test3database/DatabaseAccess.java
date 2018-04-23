package javahelps.com.test3database;



import android.content.Context;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class DatabaseAccess {


    private static final String GET_ALL_HOUSES = "http://129.174.126.235:5959/api/housing/";

    private static DatabaseAccess instance = new DatabaseAccess();

    public static DatabaseAccess getInstance() {
        return instance;
    }




    public ArrayList<HouseDetails> housedetails(Context context,String userInput) {

        System.out.println("userinput in housedetailsmethod" + userInput);

        String query_url = "";
        final ArrayList<HouseDetails> houseList = new ArrayList<>();

        int numSpaces = 0;
        String[] splitUserInput = userInput.split(" ");

        //to count the number of spaces in the selected string
        for (char c : userInput.toCharArray()) {
            if (c == ' ') {
                numSpaces = numSpaces + 1;
            }
        }


        System.out.println("Value of numSpaces" + numSpaces);

        switch (numSpaces) {
            case 0:
                query_url = GET_ALL_HOUSES + "State=" + splitUserInput[0];
                break;

            case 1:
                String modifiedLocality = splitUserInput[1].substring(0,1).toUpperCase() + splitUserInput[1].substring(1).toLowerCase();
                query_url = GET_ALL_HOUSES + "State=" + splitUserInput[0] + "/Locality=" + modifiedLocality;
                break;

            case 2:
                query_url = GET_ALL_HOUSES + "ZipCode=" + splitUserInput[2];
                break;

            default:
                System.out.println("numspaces value is not 0 1 or 2");
                break;
        }

        System.out.println("Query url(housedeatils):" + query_url );


        final JsonArrayRequest getHouses = new JsonArrayRequest(Request.Method.GET, query_url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());

                try {
                    //JSONArray houses = response;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject house = response.getJSONObject(i);
                        String Address = house.getString("Address");
                        String Locality = house.getString("Locality");
                        String State = house.getString("State");
                        String ZipCode = house.getString("ZipCode");
                        Double Price = house.getDouble("Price");
                        HouseDetails newHouse = new HouseDetails(Address, Locality, State, ZipCode, Price);
                        houseList.add(newHouse);

                    }
                } catch (JSONException e) {
                    Log.v("JSON", "EXEC" + e.getLocalizedMessage());
                }
                //System.out.println("The first home on the list is : " + houseList.get(0).getName());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(context).add(getHouses); // to call the JSONarrayrequest response

        for(HouseDetails x:houseList){
            System.out.println(x.getAddress().toString()+" "+x.getLocality().toString()+" "+x.getState().toString());
        }
        return houseList;

    }


    public ArrayList<LatLng> position_details(Context context, String userInput) {

        Log.d("DatabaseAccess","Inside position details function");
          String query_url = "";
          final ArrayList<LatLng> positionList = new ArrayList<>();
          int numSpaces = 0;

           String[] splitUserInput = userInput.split(" ");

        //to count the number of spaces in the selected string
        for (char c : userInput.toCharArray()) {
            if (c == ' ') {
                numSpaces = numSpaces + 1;
            }
        }

        //System.out.println("Value of numSpaces" + numSpaces);

        switch (numSpaces) {
            case 0: query_url = GET_ALL_HOUSES + "State=" + splitUserInput[0];
                break;

            case 1: query_url = GET_ALL_HOUSES + "State=" + splitUserInput[0] + "/Locality=" + splitUserInput[1];
                break;

            case 2: query_url = GET_ALL_HOUSES + "ZipCode=" + splitUserInput[2];
                break;

            default: System.out.println("numspaces value is not 0 1 or 2");
                break;
        }

        final JsonArrayRequest getPositions = new JsonArrayRequest(Request.Method.GET, query_url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    JSONArray positions = response;
                    for (int i = 0; i < positions.length(); i++) {
                        JSONObject position = positions.getJSONObject(i);
                        double Latitude = Double.parseDouble(position.getString("Latitude"));
                        double Longitude = Double.parseDouble(position.getString("Longitude"));
                        LatLng newPosition = new LatLng(Latitude,Longitude);
                        positionList.add(newPosition);
                        System.out.println(positionList.get(0));

                    }
                } catch (JSONException e) {
                    Log.v("JSON", "EXEC" + e.getLocalizedMessage());
                }
                //System.out.println("The first home on the list is : " + houseList.get(0).getName());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(context).add(getPositions); // to call the JSONarrayrequest response
        return positionList;
    }


}
