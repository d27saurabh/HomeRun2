package javahelps.com.test3database;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;

import java.util.ArrayList;

public class SpiderGraphActivity extends AppCompatActivity {

    //instance variables
    ArrayList entries, entries1;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider_graph);

        pd = new ProgressDialog(SpiderGraphActivity.this);
        pd.setMessage("loading");

        entries = new ArrayList<>();
        entries1 = new ArrayList<>();

        setRadarChart();

    }

    public void setRadarChart(){

        //start the progress bar action
        pd.show();

        //add values to entries arraylist
        entries.add(new Entry(Float.valueOf(4),0));
        entries.add(new Entry(Float.valueOf(4),1));
        entries.add(new Entry(Float.valueOf(5),2));

        entries1.add(new Entry(Float.valueOf(5),0));
        entries1.add(new Entry(Float.valueOf(2),1));
        entries1.add(new Entry(Float.valueOf(3),2));



        //Initialize the radar chart
        RadarChart chart = (RadarChart) findViewById(R.id.chartr);

        RadarDataSet dataset_comp1 = new RadarDataSet(entries, "Lionel Messi");
        RadarDataSet dataset_comp2 = new RadarDataSet(entries1, "Lionel Messi");

        dataset_comp1.setColor(Color.BLUE);
        dataset_comp1.setDrawFilled(true);

        dataset_comp2.setColor(Color.GREEN);
        dataset_comp2.setDrawFilled(true);

        ArrayList dataSets = new ArrayList();
        dataSets.add(dataset_comp1);
        dataSets.add(dataset_comp2);


        ArrayList labels = new ArrayList();
        labels.add("AreaSQFT");
        labels.add("Price");
        labels.add("Estimated Rent");


        RadarData data = new RadarData(labels, dataSets);
        chart.setData(data);
        String description = "Showing Lionel Messi's Skill Analysis (scale of 1-5)";
        chart.setDescription(description);

        chart.invalidate();
        chart.animate();
        pd.hide();
    }
}
