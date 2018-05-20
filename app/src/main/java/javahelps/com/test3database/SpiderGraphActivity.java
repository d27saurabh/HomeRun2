package javahelps.com.test3database;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

public class SpiderGraphActivity extends AppCompatActivity {

    //instance variables
    ArrayList entries, entries1,entries2;
    private final String TAG = "SpiderGraphActivity";
    ArrayList<String> address;
    private ProgressDialog pd;
    ArrayList<SpiderGraphDetails> addressDetails1,addressDetails2,addressDetails3;
    ArrayList<SpiderGraphDetails> addressRetrived1,addressRetrived2,addressRetrived3;

    public Handler mHandler1;
    public Handler mHandler2;
    public Handler mHandler3;

    boolean isLoading1=false;
    boolean isLoading2=false;
    boolean isLoading3=false;

    RadarDataSet dataset_comp1;
    RadarDataSet dataset_comp2;
    RadarDataSet dataset_comp3;

    ArrayList dataSets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider_graph);

        //code for back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();
        if (null != b) {
            address = b.getStringArrayList("Address");
            Log.i("List", "Passed Array List :: " + address);
        }

        //mHandler1 = new Myhandle1();
        //mHandler2 = new Myhandle2();
        //mHandler3 = new Myhandle3();


        /*addressDetails1 = new ArrayList<>();
        addressDetails2 = new ArrayList<>();
        addressDetails3 = new ArrayList<>();*/
        pd = new ProgressDialog(SpiderGraphActivity.this);
        pd.setMessage("loading");

        entries = new ArrayList<>();
        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();

        setRadarChart();



    }

    public void setRadarChart(){

        //start the progress bar action
        pd.show();

        isLoading1=true;
        /*Thread thread1 = new ThreadGetMoreData1();
        thread1.start();

        Thread thread2 = new ThreadGetMoreData2();
        thread2.start();

        Thread thread3 = new ThreadGetMoreData3();
        thread3.start();*/

        addressDetails1 = DatabaseAccess.getInstance().graphDetails(getApplicationContext(),address.get(0));
        //Log.d(TAG,"SpiderGraph activity area value for address1"+addressDetails1.get(0).getEstimatedRent().toString());
        //add values to entries arraylist
        entries.add(new Entry(Float.valueOf(2),0));
        entries.add(new Entry(Float.valueOf(4),1));
        entries.add(new Entry(Float.valueOf(5),2));

        entries1.add(new Entry(Float.valueOf(5),0));
        entries1.add(new Entry(Float.valueOf(2),1));
        entries1.add(new Entry(Float.valueOf(3),2));

        entries2.add(new Entry(Float.valueOf(2),0));
        entries2.add(new Entry(Float.valueOf(3),1));
        entries2.add(new Entry(Float.valueOf(1),2));



        //Initialize the radar chart
        RadarChart chart = (RadarChart) findViewById(R.id.chartr);

        if(address.size()==2){
            dataset_comp1 = new RadarDataSet(entries, address.get(0));
            dataset_comp2 = new RadarDataSet(entries1, address.get(1));

            dataset_comp1.setColor(Color.BLUE);
            dataset_comp1.setDrawFilled(true);

            dataset_comp2.setColor(Color.GREEN);
            dataset_comp2.setDrawFilled(true);

            dataSets = new ArrayList();
            dataSets.add(dataset_comp1);
            dataSets.add(dataset_comp2);

        }
        else {
            dataset_comp1 = new RadarDataSet(entries, address.get(0));
            dataset_comp2 = new RadarDataSet(entries1, address.get(1));
            dataset_comp3 = new RadarDataSet(entries2, address.get(2));

            dataset_comp1.setColor(Color.BLUE);
            dataset_comp1.setDrawFilled(true);

            dataset_comp2.setColor(Color.GREEN);
            dataset_comp2.setDrawFilled(true);

            dataset_comp3.setColor(Color.YELLOW);
            dataset_comp3.setDrawFilled(true);

            dataSets = new ArrayList();
            dataSets.add(dataset_comp1);
            dataSets.add(dataset_comp2);
            dataSets.add(dataset_comp3);

        }



        ArrayList labels = new ArrayList();
        labels.add("Area");
        labels.add("Price");
        labels.add("Estimated Rent");


        RadarData data = new RadarData(labels, dataSets);
        chart.setData(data);
       String description = "Houses Compared";
       chart.setDescription(description);

        chart.invalidate();
        chart.animate();
        pd.hide();
    }

    public class ThreadGetMoreData1 extends Thread{
        @Override
        public void run() {
            //Add footer View after getting data
            mHandler1.sendEmptyMessage(0);
            //Search for more data
            addressDetails1 = DatabaseAccess.getInstance().graphDetails(getApplicationContext(),address.get(0));

            //Delay time
            try{
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Send result to the handle
            Message msg = mHandler1.obtainMessage(1,addressDetails1);
            mHandler1.sendMessage(msg);
        }
    }

    public class Myhandle1 extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what)
            {
                case 0:
                    //Add loading view during search processing
                    break;
                case 1:
                    //Update data adapter and UI
                    //addressRetrived1.addAll(addressDetails1);
                    //Remove loading view after update listview
                    isLoading1 = false;
                    break;
                default:
                    break;

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
