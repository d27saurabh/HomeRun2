package javahelps.com.test3database;

/**
 * Created by admin on 10/16/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;


    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

/*
    public ArrayList<HouseDetails> housedetails() {
        ArrayList<HouseDetails> arraylist = new ArrayList<>();
            Cursor cursor = database.rawQuery("SELECT addressname,county,state,zipcode,price,latval,longval FROM detailsval" +
                    " WHERE state='" + StartActivity.mState.getText() +
                    "' and county ='" + StartActivity.mCity.getText() +
                    "' and zipcode =" + StartActivity.mZipcode.getText()
                    + ";", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HouseDetails addressdetails1 = new HouseDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                arraylist.add(addressdetails1);
                cursor.moveToNext();
            }
            cursor.close();

        return arraylist;
    }

    public ArrayList<LatLng> position_details(){
        ArrayList<LatLng> position =  new ArrayList<>();
            Cursor cursor_latlng = database.rawQuery("SELECT latval,longval FROM detailsval" +
                    " WHERE state='" + StartActivity.mState.getText() +
                    "' and county ='" + StartActivity.mCity.getText() +
                    "' and zipcode =" + StartActivity.mZipcode.getText()
                    + ";", null);
            cursor_latlng.moveToFirst();
            while (!cursor_latlng.isAfterLast()) {
                LatLng addressPosition = new LatLng(cursor_latlng.getFloat(0), cursor_latlng.getFloat(1));
                position.add(addressPosition);
                cursor_latlng.moveToNext();
            }
            cursor_latlng.close();

        return position;
    }*/

    public ArrayList<HouseDetails> housedetails(String userInput) {
        ArrayList<HouseDetails> arraylist = new ArrayList<>();
        Cursor cursor = null;
        int numSpaces = 0;
        String[] splitUserInput = userInput.split(" ");

        //to count the number of spaces in the selected string
        for(char c : userInput.toCharArray()){
            if (c == ' ') {
                numSpaces = numSpaces +1;
            }
        }

        System.out.println("Value of numSpaces" + numSpaces);

        switch(numSpaces){
            case 0: cursor = database.rawQuery("SELECT addressname,county,state,zipcode,price,latval,longval FROM detailsval" +
                    " WHERE state='" + splitUserInput[0]
                    + "';", null);
                    break;

            case 1: cursor = database.rawQuery("SELECT addressname,county,state,zipcode,price,latval,longval FROM detailsval" +
                    " WHERE state='" + splitUserInput[0] +
                    "' and UPPER(county) LIKE UPPER ('%" + splitUserInput[1]
                    + "%');", null);
                    break;

            case 2: cursor = database.rawQuery("SELECT addressname,county,state,zipcode,price,latval,longval FROM detailsval" +
                    " WHERE zipcode ='" + splitUserInput[2]
                    + "';", null);
                    break;

            default: System.out.println("numspaces value is not 0 1 or 2");
                    break;
        }

        cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HouseDetails addressdetails1 = new HouseDetails(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                arraylist.add(addressdetails1);
                cursor.moveToNext();
            }
            cursor.close();

        return arraylist;
    }

    public ArrayList<LatLng> position_details(String userInput){
        ArrayList<LatLng> position =  new ArrayList<>();
        Cursor cursor_latlng = null;

        int numSpaces = 0;
        String[] splitUserInput = userInput.split(" ");

        //to count the number of spaces in the selected string
        for(char c : userInput.toCharArray()){
            if (c == ' ') {
                numSpaces = numSpaces +1;
            }
        }

        switch(numSpaces){
            case 0: cursor_latlng = database.rawQuery("SELECT latval,longval FROM detailsval" +
                    " WHERE state='" + splitUserInput[0]
                    + "';", null);
                break;

            case 1: cursor_latlng = database.rawQuery("SELECT latval,longval FROM detailsval" +
                    " WHERE state='" + splitUserInput[0] +
                    "' and UPPER(county) LIKE UPPER ('%" + splitUserInput[1]
                    + "%');", null);
                break;

            case 2: cursor_latlng = database.rawQuery("SELECT latval,longval FROM detailsval" +
                    " WHERE zipcode ='" + splitUserInput[2]
                    + "';", null);
                break;

            default: System.out.println("numspaces value is not 0 1 or 2");
                break;
        }
            cursor_latlng.moveToFirst();
            while (!cursor_latlng.isAfterLast()) {
                LatLng addressPosition = new LatLng(cursor_latlng.getFloat(0), cursor_latlng.getFloat(1));
                position.add(addressPosition);
                cursor_latlng.moveToNext();
            }
            cursor_latlng.close();

        return position;
    }
}
