package javahelps.com.test3database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class firstScreen extends AppCompatActivity {

    ArrayList<String> City =  new ArrayList<String>();
    ArrayList<String> State = new ArrayList<String>();
    ArrayList<String> Zipcode = new ArrayList<String>();
    ArrayList<String> stateCity = new ArrayList<String>();
    ArrayList<String> stateCityZipcode = new ArrayList<String>();
    ArrayList<String> stateCityZipcodeFinal = new ArrayList<String>();
    String mUserSelected;
    int numSpaces;
    String [] spiltmUserSelected;
    String response;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);


        getdata("http://www.gomashup.com/json.php?fds=geo/usa/zipcode/state/CT&jsoncallback=");


        /*DownloadTask task = new DownloadTask();
        task.execute("http://www.gomashup.com/json.php?fds=geo/usa/zipcode/state/VA&jsoncallback=");*/

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,stateCityZipcodeFinal);
        AutoCompleteTextView msearchView = (AutoCompleteTextView) findViewById(R.id.searchView);
        msearchView.setThreshold(1);
        msearchView.setAdapter(adapter);

        msearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mUserSelected = (String)adapterView.getItemAtPosition(i);
            }
        });
    }

   /* //Download the json where zipcode, county and state is provided
    public class DownloadTask extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... urls) {

            //Creating the url object and connection
            String result = "";
            URL url;
            java.net.HttpURLConnection urlConnection = null;

            try {
                //gConnect to the url using urlconnection.
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);

                //Start reading the source code of the url site provided
                int data = reader.read();

                //and store the read source code in the result
                while (data != -1) {

                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }

                return result;

            } catch (Exception e) {
                e.printStackTrace();
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            //find and get the city value from downloaded source code
            Pattern p = Pattern.compile("City\" : \"(.*?)\",");
            Matcher m = p.matcher(result);

            while(m.find()){

                City.add(m.group(1));
            }

            //find and get the zipcode value from downloaded source code
            p = Pattern.compile("Zipcode\" : \"(.*?)\",");
            m = p.matcher(result);

            while(m.find()){

                Zipcode.add(m.group(1));
            }


            //find and get the state value from downloaded source code
            p = Pattern.compile("State\" : \"(.*?)\",");
            m = p.matcher(result);

            while(m.find()){

                State.add(m.group(1));
            }

            //perform operations on state city zipcode values inorder to store them in (state city) and (state city zipcode) formats
            for(int x = 0 ; x<Zipcode.size();x++){

                stateCity.add(State.get(x) + " " + City.get(x));
                stateCityZipcode.add(State.get(x)+ " " + City.get(x) + " " + Zipcode.get(x));
            }

            //to remove redundancy from (state city) format
            HashSet hs = new HashSet();
            hs.addAll(stateCity);
            stateCity.clear();
            stateCity.addAll(hs);

            //store all the formats to provide option to autocomplete textview
            stateCityZipcodeFinal.add(State.get(0));
            stateCityZipcodeFinal.addAll(stateCity);
            stateCityZipcodeFinal.addAll(stateCityZipcode);


            for(String x:stateCityZipcodeFinal) {

                Log.i("Final Suggestions:",x);
            }
        }
    }*/

    public void getdata( String urlString){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlString,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //find and get the city value from downloaded source code
                        Pattern p = Pattern.compile("City\" : \"(.*?)\",");
                        Matcher m = p.matcher(response);

                        while(m.find()){

                            City.add(m.group(1));
                        }

                        //find and get the zipcode value from downloaded source code
                        p = Pattern.compile("Zipcode\" : \"(.*?)\",");
                        m = p.matcher(response);

                        while(m.find()){

                            Zipcode.add(m.group(1));
                        }


                        //find and get the state value from downloaded source code
                        p = Pattern.compile("State\" : \"(.*?)\",");
                        m = p.matcher(response);

                        while(m.find()){

                            State.add(m.group(1));
                        }

                        //perform operations on state city zipcode values inorder to store them in (state city) and (state city zipcode) formats
                        for(int x = 0 ; x<Zipcode.size();x++){

                            stateCity.add(State.get(x) + " " + City.get(x));
                            stateCityZipcode.add(State.get(x)+ " " + City.get(x) + " " + Zipcode.get(x));
                        }

                        //to remove redundancy from (state city) format
                        HashSet hs = new HashSet();
                        hs.addAll(stateCity);
                        stateCity.clear();
                        stateCity.addAll(hs);

                        //store all the formats to provide option to autocomplete textview
                        stateCityZipcodeFinal.add(State.get(0));
                        stateCityZipcodeFinal.addAll(stateCity);
                        stateCityZipcodeFinal.addAll(stateCityZipcode);


                        for(String x:stateCityZipcodeFinal) {

                            Log.i("Final Suggestions:",x);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }



    public void Search(View v){

        System.out.println("Value of the user input: " + mUserSelected);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("userSelectedDetail",mUserSelected);

        startActivity(i);
    }
}

