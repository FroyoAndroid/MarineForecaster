package com.mobilio.marineforecaster;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Spinner;

import com.mobilio.marineforecaster.Adapters.POIAdapter;
import com.mobilio.marineforecaster.POJO.Backend;
import com.mobilio.marineforecaster.POJO.POI;

import java.io.IOException;
import java.util.List;

/**
 * Created by LENOVO PC on 11-02-2015.
 */
public class CategorySelectActivity  extends Activity{

    public static final String LOGTAG = "CATEGORY SELECT ACTIVITY";
    ListView poiList;
    Spinner category;
    List<POI> pois;
    Backend DB;
    POIAdapter adapter;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        setContentView(R.layout.activity_poi);
        poiList = (ListView) findViewById(R.id.poiList);
        category = (Spinner) findViewById(R.id.categorySpinner);
        try {
            DB = new Backend(CategorySelectActivity.this);
            DB.open();
        }catch (IOException e){
            e.printStackTrace();
            DB.close();
        }
        pois = DB.findAll();
        DB.close();
        Log.d(LOGTAG, pois.get(0).getCity());
        adapter = new POIAdapter(mContext,pois);
        poiList.setAdapter(adapter);
    }
}
