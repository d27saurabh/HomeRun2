package javahelps.com.test3database;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class FilterActivity extends AppCompatActivity {

    SeekBar price, area, rent;
    TextView priceTV, areaTV, rentTV, bedsTV, bathsTV;
    Button bedPlus, bedMinus, bathPlus, bathMinus, searchButton;
    int bedValue, bathValue;
    String iReceiveFromFirstActivity;

    private final String TAG = "FilterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        //code for back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        Log.d(TAG,"User Selected Value:" + iReceiveFromFirstActivity);

        price = (SeekBar) findViewById(R.id.seekbarPrice);
        area = (SeekBar) findViewById(R.id.seekbarArea);
        rent = (SeekBar) findViewById(R.id.seekbarRent);

        priceTV = (TextView) findViewById(R.id.seekbarValuePrice);
        areaTV = (TextView) findViewById(R.id.seekbarValueArea);
        rentTV = (TextView) findViewById(R.id.seekbarValueRent);
        bedsTV = (TextView) findViewById(R.id.bedroomTV);
        bathsTV = (TextView) findViewById(R.id.bathroomTV);

        bedPlus = (Button) findViewById(R.id.btnBedPlus);
        bedMinus = (Button) findViewById(R.id.btnBedMinus);
        bathMinus = (Button) findViewById(R.id.btnBathMinus);
        bathPlus = (Button) findViewById(R.id.btnBathPlus);
        searchButton = (Button) findViewById(R.id.searchBtn);

        price.setMax(2000000);
        area.setMax(5000);
        rent.setMax(5000);

        price.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                priceTV.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        area.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                areaTV.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                rentTV.setText(i + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        bedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bedValue > 1){
                    bedValue = bedValue -1;
                    bedsTV.setText(bedValue+"");
                }
            }
        });

        bedPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bedValue < 6){
                    bedValue = bedValue+1;
                    bedsTV.setText(bedValue+"");
                }
            }
        });

        bathMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bathValue > 1){
                    bathValue = bathValue -1;
                    bathsTV.setText(bathValue+"");
                }
            }
        });

        bathPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bathValue < 6) {
                    bathValue = bathValue + 1;
                    bathsTV.setText(bathValue + "");

                }
            }
        });

    searchButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            String passValue = "CT";
            i.putExtra("userSelectedDetail",passValue);
            startActivity(i);

        }
    });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}