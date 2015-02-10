package com.mobilio.marineforecaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GasStationsActivity extends Activity {
	
	int a=0;

	ListView list;
	greekdatasource gdatasource;
	englishdatasource edatasource;
	List<String> states;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_gasstations);
		
		list=(ListView) findViewById(R.id.MYLIST);
		states  = new ArrayList<String>();
		try {
			
			gdatasource = new greekdatasource(GasStationsActivity.this);
			edatasource = new englishdatasource(GasStationsActivity.this);
			gdatasource.open();
			edatasource.open();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		 
		 if(a==0)
		 {
			 
			 states = edatasource.findDistinctStates();
			 Log.i("pk11", String.valueOf(states.size()));
		 }
		
		 else if (a==1)
		 {
			 gdatasource.Removenull();
			 states = gdatasource.findDistinctStates();
			 Log.i("pk11", String.valueOf(states.size()));
		 }
		 states.remove(states.size()-1);
		 list.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,states));
		 list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				Intent m = new Intent(GasStationsActivity.this,ShowStations.class);
				m.putExtra("state", states.get(pos));
				startActivity(m);
			}
		});
	}

}
