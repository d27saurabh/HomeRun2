package javahelps.com.test3database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";

    public static EditText mState, mCity, mZipcode;
    public static boolean proceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mState = (EditText) findViewById(R.id.stateET);
        mState.getText();

        mCity = (EditText) findViewById(R.id.cityET);
        mCity.getText();

        mZipcode  = (EditText) findViewById(R.id.zipcodeET);
        mZipcode.getText();
     }

    public void gotoMainActivity(View view){

        if((mState.getText().toString()).matches("")) {
            Log.d(TAG, "gotoMainActivity: Inside the null check if statement");
            Toast.makeText(this, "Please enter State", Toast.LENGTH_SHORT).show();
            proceed=false;
        }else if((mCity.getText().toString()).matches("")){
            Toast.makeText(this, "Please enter City", Toast.LENGTH_SHORT).show();
            proceed=false;
        }else if((mZipcode.getText().toString()).matches("")){
            Toast.makeText(this, "Please enter Zipcode", Toast.LENGTH_SHORT).show();
            proceed=false;
        }else{
            proceed=true;
        }


        if(proceed==true) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }
}
