package com.mobilio.marineforecaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CountrySelectActivity extends Activity {
	
	ListView list;
	int a=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_gasstations);
		
		list=(ListView) findViewById(R.id.MYLIST);
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		 
		 
		 if(a==0)
			 list.setAdapter(new ArrayAdapter<String>(CountrySelectActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.countrylistenglish)));
		 else if(a==1)
			 list.setAdapter(new ArrayAdapter<String>(CountrySelectActivity.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.countrylistgreek)));
			
		 list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				startActivity(new Intent(CountrySelectActivity.this,GasStationsActivity.class).putExtra("index", arg2));
				
			}
		});
	}

}
