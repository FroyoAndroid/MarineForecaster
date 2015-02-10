package com.mobilio.marineforecaster.POJO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobilio.marineforecaster.DataHelper.DBHelper;
import com.mobilio.marineforecaster.OpenHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO PC on 11-02-2015.
 */
public class Backend {

    public static final String LOGTAG = "Backend";
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allcolumns= {

            DBHelper.COLUMN_CATEGORY,
            DBHelper.COLUMN_STATE,
            DBHelper.COLUMN_CITY,
            DBHelper.COLUMN_LATITUDE,
            DBHelper.COLUMN_LONGITUDE,
            DBHelper.COLUMN_PHONE1,
            DBHelper.COLUMN_PHONE2,
            DBHelper.COLUMN_PHONE3,
            DBHelper.COLUMN_PHONE4

    };

    public Backend(Context context) throws IOException{
        dbhelper = new DBHelper(context);
    }

    public POI poi_create(POI poi){
        ContentValues values = new ContentValues();

        values.put(DBHelper.COLUMN_STATE, poi.getState());
        values.put(DBHelper.COLUMN_CITY, poi.getCity());
        values.put(DBHelper.COLUMN_CATEGORY, poi.getCategory());
        values.put(DBHelper.COLUMN_LATITUDE, poi.getLatitude());
        values.put(DBHelper.COLUMN_LONGITUDE, poi.getLongitude());
        values.put(DBHelper.COLUMN_PHONE1, poi.getPhone1());
        values.put(DBHelper.COLUMN_PHONE2, poi.getPhone2());
        values.put(DBHelper.COLUMN_PHONE3, poi.getPhone3());
        values.put(DBHelper.COLUMN_PHONE4, poi.getPhone4());
        long insertid = database.insert(DBHelper.TABLE_NAME,null,values);
        return poi;
    }

    public List<POI> findAll(){
        List<POI> pois = new ArrayList<POI>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,allcolumns,null, null, null, null, null, null);

        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                POI poi  = new POI();
                poi.setCity(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITY)));
                poi.setState(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_STATE)));
                poi.setCategory(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY)));
                poi.setLatitude(cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_LATITUDE)));
                poi.setLongitude(cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_LONGITUDE)));
                poi.setPhone1(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE1)));
                poi.setPhone2(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE2)));
                poi.setPhone3(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE3)));
                poi.setPhone4(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE4)));

                pois.add(poi);

                Log.i("ahmed", "english Returned "+ cursor.getCount() + " rows");
            }
        }

        return  pois;
    }


    public List<POI> findByCategory(String categoy){
        List<POI> pois = new ArrayList<POI>();

        Cursor cursor = database.query(DBHelper.TABLE_NAME,allcolumns,null, null, null, null, null, null);

        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");

        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                if(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY)).equalsIgnoreCase(categoy)) {
                    POI poi = new POI();
                    poi.setCity(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITY)));
                    poi.setState(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_STATE)));
                    poi.setCategory(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CATEGORY)));
                    poi.setLatitude(cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_LATITUDE)));
                    poi.setLongitude(cursor.getFloat(cursor.getColumnIndex(DBHelper.COLUMN_LONGITUDE)));
                    poi.setPhone1(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE1)));
                    poi.setPhone2(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE2)));
                    poi.setPhone3(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE3)));
                    poi.setPhone4(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_PHONE4)));

                    pois.add(poi);

                    Log.i("POI by category", "POI Returned " + cursor.getCount() + "rows");
                }
            }
        }

        return  pois;
    }

    public void open(){
        database = dbhelper.getWritableDatabase();
        Log.i("DATABASE","Database opened");
    }

    public void close(){
        Log.i("DATABASR","Database closed");
        dbhelper.close();
    }
}
