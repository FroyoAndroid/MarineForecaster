package com.mobilio.marineforecaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowStations extends Activity {

	ArrayList<HashMap<String, String>> mylist;
	HashMap<String, String> map;
	private ArrayList<String> states;
	private greekdatasource gdatasource;
	private englishdatasource edatasource;
	private int a;
	List<GreekStation> greeks;
	List<EnglishStation> englishs;
	String state = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gasstations);
		ListView list = (ListView) findViewById(R.id.MYLIST);

		Intent n = getIntent();
		state = n.getStringExtra("state");
		Log.i("pkkkk", state);

		states = new ArrayList<String>();
		try {

			gdatasource = new greekdatasource(ShowStations.this);
			edatasource = new englishdatasource(ShowStations.this);
			gdatasource.open();
			edatasource.open();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		String listPrefs = prefs.getString("listpref", "English");

		if (listPrefs.equalsIgnoreCase("Greek"))
			a = 1;

		mylist = new ArrayList<HashMap<String, String>>();
		map = new HashMap<String, String>();
		Log.i("pkkkk", "a = " + String.valueOf(a));

		if (a == 0) {

			englishs = new ArrayList<EnglishStation>();
			englishs = edatasource.findbyState(state);

			for (int i = 0; i <= englishs.size() - 1; i++) {

				map.put("city", englishs.get(i).getCity());

				if (englishs.get(i).getPhone1() == null)
					map.put("phone1", "Phone 1: " + "not available");
				else {
					String Phone;
					

//					if (englishs.get(i).getPhone1().contains("."))
//						Phone = englishs.get(i).getPhone1().split(".")[0];
//					else
						Phone = englishs.get(i).getPhone1().substring(0, 9);
					map.put("phone1", "Phone 1: " + Phone);
				}
				if (englishs.get(i).getPhone2() == null)
					map.put("phone2", "Phone 2: " + "not available");
				else {
					String Phone;

//					if (englishs.get(i).getPhone2().contains("."))
//						Phone = englishs.get(i).getPhone2().split(".")[0];
//					else
						Phone = englishs.get(i).getPhone2().substring(0, 9);
					map.put("phone2", "Phone 2: " + Phone);
				}

				if (englishs.get(i).getPhone3() == null)
					map.put("phone3", "Phone 3: " + "not available");
				else {
					String Phone;

//					if (englishs.get(i).getPhone3().contains("."))
//						Phone = englishs.get(i).getPhone3().split(".")[0];
//					else
						Phone = englishs.get(i).getPhone3().substring(0, 9);
					map.put("phone3", "Phone 3: " + Phone);
				}

				if (englishs.get(i).getPhone4() == null)
					map.put("phone4", "Phone 4: " + "not available");
				else {
					String Phone;

//					if (englishs.get(i).getPhone4().contains("."))
//						Phone = englishs.get(i).getPhone4().split(".")[0];
//					else
						Phone = englishs.get(i).getPhone4().substring(0, 9);
					map.put("phone4", "Phone 4: " + Phone);
				}

				mylist.add(map);
				map = new HashMap<String, String>();
			}

		}

		else if (a == 1) {
			greeks = new ArrayList<GreekStation>();
			greeks = gdatasource.findbyState(state);

			for (int i = 0; i <= greeks.size() - 1; i++) {

				map.put("city", greeks.get(i).getCity());

				if (greeks.get(i).getPhone1() == null)
					map.put("phone1", "Phone 1: " + "δεν είναι διαθέσιμο");

					else {
						String Phone;

//						if (greeks.get(i).getPhone1().contains("."))
//							Phone = greeks.get(i).getPhone1().split(".")[0];
//						else
							Phone = greeks.get(i).getPhone1().substring(0, 9);
						map.put("phone1", "Phone 1: " + Phone);
					}

				if (greeks.get(i).getPhone2() == null)
					map.put("phone2", "Phone 2: " + "δεν είναι διαθέσιμο");
				
					else {
						String Phone;
//
//						if (greeks.get(i).getPhone2().contains("."))
//							Phone = greeks.get(i).getPhone2().split(".")[0];
//						else
							Phone = greeks.get(i).getPhone2().substring(0, 9);
						map.put("phone2", "Phone 2: " + Phone);
					}

				if (greeks.get(i).getPhone3() == null)
					map.put("phone3", "Phone 3: " + "δεν είναι διαθέσιμο");
				else {
					String Phone;

//					if (greeks.get(i).getPhone3().contains("."))
//						Phone = greeks.get(i).getPhone3().split(".")[0];
//					else
						Phone = greeks.get(i).getPhone3().substring(0, 9);
					map.put("phone3", "Phone 3: " + Phone);
				}
				if (greeks.get(i).getPhone4() == null)
					map.put("phone4", "Phone 4: " + "δεν είναι διαθέσιμο");
				else {
					String Phone;
//
//					if (greeks.get(i).getPhone4().contains("."))
//						Phone = greeks.get(i).getPhone4().split(".")[0];
//					else
						Phone = greeks.get(i).getPhone4().substring(0, 9);
					map.put("phone4", "Phone 4: " + Phone);
				}
				mylist.add(map);
				map = new HashMap<String, String>();
			}

		}

		SimpleAdapter myAd = new SimpleAdapter(
				this,
				mylist,
				R.layout.listitem,
				new String[] { "city", "phone1", "phone2", "phone3", "phone4" },
				new int[] { R.id.CITY_CELL, R.id.PHONE1_CELL, R.id.PHONE2_CELL,
						R.id.PHONE3_CELL, R.id.PHONE4_CELL });

		list.setAdapter(myAd);

	}

}
