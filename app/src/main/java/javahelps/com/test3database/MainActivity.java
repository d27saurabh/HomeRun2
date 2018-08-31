package javahelps.com.test3database;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private ListView listView;
    private Marker mMarker;
    String iReceiveFromFirstActivity;
    int count;
    int mSelectedItem;
    ArrayList<HouseDetails> mSelected;
    String SelectedItemsAddress;
    static int CABMode = 0;
    HeatmapTileProvider mProvider;
    TileOverlay mOverlay;
    LatLng latLng;
    private int PROXIMITY_RADIUS = 10000;
    boolean isLoading = false;
    int responseCounter=0;
    public Handler mHandler;
    public Handler mHandlerPosition;
    public View ftView;

    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15;
    public ListView mListView;
    public PersonListAdapter adapter;
    //var
    private Boolean mLocationPermissionGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    public ArrayList<HouseDetails> retrivedetails;
    public ArrayList<LatLng> retrivePositions;
    private ArrayList<HouseDetails> nextHundredDetails;
    private ArrayList<LatLng> nextHundredPositions;
    private ArrayList<String> address;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLocationPermission();


        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                iReceiveFromFirstActivity = null;
            } else {
                iReceiveFromFirstActivity = extras.getString("userSelectedDetail");
            }
        }
        else {
            iReceiveFromFirstActivity = (String) savedInstanceState.getSerializable("userSelectedDetail");
        }

        address = new ArrayList<String>();
        nextHundredPositions = new ArrayList<LatLng>();
        mListView = (ListView) findViewById(R.id.listView);
        DatabaseAccess databaseAccess = new DatabaseAccess();

        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ftView = li.inflate(R.layout.footer_view,null);
        mHandler = new Myhandle();
        mHandlerPosition = new MyhandlePosition();

        //code for back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        retrivedetails = DatabaseAccess.getInstance().housedetails(this, iReceiveFromFirstActivity);
        retrivePositions = DatabaseAccess.getInstance().position_details(this, iReceiveFromFirstActivity);


        adapter = new PersonListAdapter(this, R.layout.adapter_view_layout, retrivedetails);
        mListView.setAdapter(adapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {}

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                if(view.getLastVisiblePosition() == totalItemCount-1 && mListView.getCount()>=10 && isLoading==false){
                    isLoading = true;
                    responseCounter = totalItemCount;
                    Thread thread = new ThreadGetMoreData();
                    thread.start();

                    isLoading = true;
                    Thread thread1 = new ThreadGetMoreDataPositions();
                    thread1.start();
                }
            }
        });


        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            private int nr = 0;
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {

                if(checked) {
                    nr++;
                    adapter.setNewSelection(position, checked);
                    SelectedItemsAddress=String.valueOf(retrivedetails.get(position).getAddress());
                    address.add(SelectedItemsAddress);
                }
                else{
                    nr--;
                    adapter.removeSelection(position);
                    SelectedItemsAddress=String.valueOf(retrivedetails.get(position).getAddress());
                    address.remove(SelectedItemsAddress);
                }
                actionMode.setTitle(nr + " selected");
            Log.d(TAG,"Selected Address:" + SelectedItemsAddress);

            for(String x : address){
                Log.d(TAG,"address contents" + x);
            }

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

                //on long press, create a action bar change, here inflating the spider icon symbol
                Toast.makeText(MainActivity.this,"Select 3 items to compare",Toast.LENGTH_LONG);
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.my_context_menu, menu);
                nr = 0;
                CABMode = 1;
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                 return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.compare:
                        //intent to spider activity
                        if(1<nr&&nr<4){
                            Intent intent = new Intent(getApplicationContext(),SpiderGraphActivity.class);
                            intent.putExtra("Address",address);
                            startActivity(intent);
                        }

                            if(nr<2){
                                Toast.makeText(getApplicationContext(),"Select atleast 3 items to compare",Toast.LENGTH_SHORT);
                            }
                            else if(nr<3)
                            {
                                Toast.makeText(getApplicationContext(),"Select atmost 3 items to compare",Toast.LENGTH_SHORT);
                            }


                        break;

                    case R.id.mc:
                        if(nr==1){
                            Intent intent = new Intent(getApplicationContext(),MortgageCalc.class);
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
               //write code for do anything that is need to be done while th long press is going to end
                adapter.clearSelection();
                CABMode = 0;
                markLocations();
                mListView.setAdapter(adapter);
               // mListView.setSelected(false);
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                // TODO Auto-generated method stub

                mListView.setItemChecked(position, !adapter.isPositionChecked(position));

                //mListView.setSelected(true);

                return false;
            }

        });



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Creates a new marker set
                MarkerOptions options = new MarkerOptions();
                //clears the existing map
                mMap.clear();
                options.position(retrivePositions.get(i))
                        .title(String.valueOf(retrivedetails.get(i).getAddress()))
                        .alpha(1f)
                        .icon(BitmapDescriptorFactory.defaultMarker(200f));
                mMap.addMarker(options);
                moveCamera(retrivePositions.get(i),17f);
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                latLng = retrivePositions.get(i);

                setNearbyRestaurants();
                setNearbyHospitals();
                setNearybySchools();
            }
        });

        markLocations();
    }


    public class Myhandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case 0:
                    //Add loading view during search processing
                    mListView.addFooterView(ftView);
                    break;
                case 1:
                    //Update data adapter and UI
                    adapter.addAll(nextHundredDetails);
                    //Remove loading view after update listview
                    mListView.removeFooterView(ftView);
                    isLoading = false;
                    break;
                default:
                    break;

            }
        }
    }


    public class ThreadGetMoreData extends Thread{
        @Override
        public void run() {
            //Add footer View after getting data
            mHandler.sendEmptyMessage(0);
            //Search for more data
            nextHundredDetails = DatabaseAccess.getInstance().nextHouseDetails(MainActivity.this,iReceiveFromFirstActivity,responseCounter);
            retrivedetails.addAll(nextHundredDetails);

            //Delay time
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Send result to the handle
            Message msg = mHandler.obtainMessage(1,nextHundredDetails);
            mHandler.sendMessage(msg);


        }
    }

    //for next 10 details
    public class ThreadGetMoreDataPositions extends Thread{
        @Override
        public void run() {
            //Add footer View after getting data
            mHandlerPosition.sendEmptyMessage(0);
            //Search for more data
            nextHundredPositions = DatabaseAccess.getInstance().nextPositionDetails(MainActivity.this,iReceiveFromFirstActivity,responseCounter);
            //Delay time
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Send result to the handle
            Message msg = mHandlerPosition.obtainMessage(1,nextHundredPositions);
            mHandlerPosition.sendMessage(msg);
        }
    }
    public class MyhandlePosition extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case 0://Add loading view during search processing
                    mListView.addFooterView(ftView);
                    break;
                case 1:
                    retrivePositions.addAll(nextHundredPositions);
                    mListView.removeFooterView(ftView);
                    isLoading=false;
                    break;
                default:
                    break;

            }
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapready : map is ready");
        mMap = googleMap;

        if (mLocationPermissionGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.setIndoorEnabled(true);

        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if(item.getItemId() == android.R.id.home){
            this.finish();
        }

        if(item.getItemId() == R.id.filter ){
            Log.i("FilterClicked",  "Filter");
            Intent i = new Intent(this, FilterActivity.class);
            i.putExtra("userSelectedDetail",iReceiveFromFirstActivity);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServiceOk: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make the map requests
            Log.d(TAG, "isServiceOk: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void moveCamera(LatLng latLag, float zoom) {
        Log.d(TAG, "moveCamera: moving camera to :lat" + latLag.latitude + ", lng: " + latLag.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLag, zoom));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLag, zoom));
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainActivity.this);
    }

    //Location Permission method
    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permission = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permission, LOCATION_PERMISSION_REQUEST_CODE);
        }

    }

    private void getDeviceLocation () {
        Log.d(TAG, "getDeviceLocation: getting the device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (mLocationPermissionGranted) {
                Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            //moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException" + e.getMessage());

        }

    }

    private void markLocations(){

//        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
//        databaseAccess.open();
//        ArrayList<HouseDetails> retrivedetails = databaseAccess.housedetails(iReceiveFromFirstActivity);
//        ArrayList<LatLng> retrivePositions = databaseAccess.position_details(iReceiveFromFirstActivity);
        //retrivedetails = DatabaseAccess.getInstance().housedetails(this, iReceiveFromFirstActivity);
        //retrivePositions = DatabaseAccess.getInstance().position_details(this, iReceiveFromFirstActivity);

        int i=0;

        //Adding markers according to the location values from db(on state zip and city selected)
        int size = retrivedetails.size();

        Log.d(TAG,"Retrive details size:"+ String.valueOf(size));
        for(LatLng location : retrivePositions){
            Log.d(TAG, "getDeviceLocation: location" + location);
            MarkerOptions options = new MarkerOptions()
                    .position(location)
                    .title(String.valueOf(retrivedetails.get(i).getAddress()))
                    .alpha(0.85f)
                    .icon(BitmapDescriptorFactory.defaultMarker(0f));

            mMap.clear();
            mMap.addMarker(options);
            Log.d(TAG, "getDeviceLocation: title:" + String.valueOf(retrivedetails.get(i).getAddress()));
            moveCamera(location,12f);
            i++;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called");
        mLocationPermissionGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                        Log.d(TAG, "onRequestPermissionsResult: permission granted");
                        mLocationPermissionGranted = true;
                        //initialize our  map
                        initMap();
                    }
                }
            }
        }
    }

    public void createHeatMap(View view){

        mMap.clear();
        mMap.getMinZoomLevel();
        addHeatMap();
    }


    //for adding the heatmaps
    private void addHeatMap() {

        //get the locations from database

        //retrivePositions = DatabaseAccess.getInstance().position_details(this,iReceiveFromFirstActivity);

        // Create a heat map tile provider, passing it the latlngs of the police stations.
        mProvider = new HeatmapTileProvider.Builder()
                .data(retrivePositions)
                .build();
        // Add a tile overlay to the map, using the heat map tile provider.
        mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCUyt8THJ-_Bs9ACVZyDFH1E3jIiAzHql8");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }


    public void setNearbyRestaurants(){

        //to get nearby restaurants
        String Restaurant = "restaurant";
        //mMap.clear();
        String url = getUrl(latLng.latitude, latLng.longitude, Restaurant);
        Object[] DataTransfer = new Object[3];
        DataTransfer[0] = mMap;
        DataTransfer[1] = url;
        DataTransfer[2] = Restaurant;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);

    }

    public void setNearbyHospitals(){
        //to get nearby hospitals
        String Hospital = "hospital";
        Log.d("onClick", "Button is Clicked");
        String url = getUrl(latLng.latitude, latLng.longitude, Hospital);
        Object[] DataTransfer = new Object[3];
        DataTransfer[0] = mMap;
        DataTransfer[1] = url;
        DataTransfer[2] = Hospital;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);
    }

    public void setNearybySchools(){
        //to get nearby schools
        String School = "school";
        Log.d("onClick", "Button is Clicked");
        String url = getUrl(latLng.latitude, latLng.longitude, School);
        Object[] DataTransfer = new Object[3];
        DataTransfer[0] = mMap;
        DataTransfer[1] = url;
        DataTransfer[2] = School;
        Log.d("onClick", url);
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute(DataTransfer);
    }


}