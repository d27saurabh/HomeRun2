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
    private static final String GET_ALL_POSITIONS = "http://129.174.126.235:5959/api/housing/Coordinates/";
    private static final String GET_ALL_GRAPHDETAILS = "http://129.174.126.235:5959/api/housing/";
    private static DatabaseAccess instance = new DatabaseAccess();
    private static final String TAG = "DatabaseActivity";

    private JSONArray detailsForSpiderGraph;

    private long mRequestStartTime;

    public static DatabaseAccess getInstance() {
        return instance;
    }




    public ArrayList<HouseDetails> housedetails(Context context,String userInput) {

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
        query_url = query_url + "/" + 0;
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

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(context).add(getHouses); // to call the JSONarrayrequest response
       // Log.d(TAG,"Address Vlaue:"+houseList.get(0).getAddress());
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
            case 0: query_url = GET_ALL_POSITIONS + "State=" + splitUserInput[0];
                break;

            case 1: query_url = GET_ALL_POSITIONS + "State=" + splitUserInput[0] + "/Locality=" + splitUserInput[1];
                break;

            case 2: query_url = GET_ALL_POSITIONS + "ZipCode=" + splitUserInput[2];
                break;

            default: System.out.println("numspaces value is not 0 1 or 2");
                break;
        }

        query_url = query_url + "/" + 0;

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
                    }
                } catch (JSONException e) {
                    Log.v("JSON", "EXEC" + e.getLocalizedMessage());
                }
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

    public ArrayList<HouseDetails> nextHouseDetails(Context context,String userInput, int counter) {

        String query_url = "";

        final ArrayList<HouseDetails> houseList1 = new ArrayList<>();

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
                String modifiedLocality = splitUserInput[1].substring(0, 1).toUpperCase() + splitUserInput[1].substring(1).toLowerCase();
                query_url = GET_ALL_HOUSES + "State=" + splitUserInput[0] + "/Locality=" + modifiedLocality;
                break;

            case 2:
                query_url = GET_ALL_HOUSES + "ZipCode=" + splitUserInput[2];
                break;

            default:
                System.out.println("numspaces value is not 0 1 or 2");
                break;
        }

        query_url = query_url + "/" + counter;

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
                        houseList1.add(newHouse);

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

        for(HouseDetails x:houseList1){
            System.out.println(x.getAddress().toString()+" "+x.getLocality().toString()+" "+x.getState().toString());
        }
        return houseList1;


    }

    public ArrayList<LatLng> nextPositionDetails(Context context, String userInput,int counter) {

        Log.d("DatabaseAccess","Inside position details function");
        String query_url = "";
        final ArrayList<LatLng> positionList1 = new ArrayList<>();
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
            case 0: query_url = GET_ALL_POSITIONS + "State=" + splitUserInput[0];
                break;

            case 1: query_url = GET_ALL_POSITIONS + "State=" + splitUserInput[0] + "/Locality=" + splitUserInput[1];
                break;

            case 2: query_url = GET_ALL_POSITIONS + "ZipCode=" + splitUserInput[2];
                break;

            default: System.out.println("numspaces value is not 0 1 or 2");
                break;
        }

        query_url = query_url + "/" + 0;

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
                        positionList1.add(newPosition);
                        //Log.d(TAG,"next Latitude:" + String.valueOf(positionList1.get(i).latitude)+ " and longitude:"+ String.valueOf(positionList1.get(i).longitude));
                    }
                } catch (JSONException e) {
                    Log.v("JSON", "EXEC" + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
            }
        });
        Volley.newRequestQueue(context).add(getPositions); // to call the JSONarrayrequest response

        //Log.d(TAG,"next Latitude:" + String.valueOf(positionList1.get(0).latitude)+ " and longitude:"+ String.valueOf(positionList1.get(0).longitude));
        return positionList1;
    }

    public ArrayList<SpiderGraphDetails> graphDetails (Context context, String address){
        Log.d(TAG,"Inside graphDetails Activity");

        String query_url = GET_ALL_GRAPHDETAILS + address;

        Log.d(TAG,"query to get graph details" + query_url);

        final ArrayList<SpiderGraphDetails> graphList = new ArrayList<>();

        mRequestStartTime = System.currentTimeMillis();

        final JsonArrayRequest getgraphDetails = new JsonArrayRequest(Request.Method.GET, query_url, new JSONArray(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG,"inside the onResponse ");
               System.out.println("graph query response"+ response.toString());
                try {
                    JSONArray positions = response;
                    detailsForSpiderGraph = positions;
                    for (int i = 0; i < positions.length(); i++) {
                        JSONObject position = positions.getJSONObject(i);
                        Double area = Double.parseDouble(position.getString("AreaSpace_SQFT"));
                        Double price = Double.parseDouble(position.getString("Price"));
                        Double estimatedRent = Double.parseDouble(position.getString("EstimatedRent"));
                        String houseType = position.getString("Status");
                        String zipCode = position.getString("ZipCode");

                        SpiderGraphDetails newposition = new SpiderGraphDetails(area,price,estimatedRent,houseType,zipCode);
                        Log.d(TAG,"area for address one:"+String.valueOf(newposition.getArea()));
                        graphList.add(newposition);

                        long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;

                        Log.d(TAG,"time required" + totalRequestTime);

                       Log.d(TAG,"newposition area value in inner class"+String.valueOf(newposition.getArea()));

                    }
                } catch (JSONException e) {
                    Log.v("JSON", "EXEC" + e.getLocalizedMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("API", "Err" + error.getLocalizedMessage());
                long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
            }
        });
        Volley.newRequestQueue(context).add(getgraphDetails); // to call the JSONarrayrequest response

       // Log.d(TAG,"newposition area value"+String.valueOf(graphList.get(0).getEstimatedRent()));
        return graphList;
    }
}
