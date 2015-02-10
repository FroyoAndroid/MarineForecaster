package com.mobilio.marineforecaster;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HowuseActivity extends Activity {
	int a=0;
	
	TextView title,point1,point2,point3,point4,point5,point6,heading1,heading2,heading3,heading4,heading5,heading6,marineforecasting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_howuse);
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		 
		 title=(TextView) findViewById(R.id.txt_title);
		 point1=(TextView) findViewById(R.id.txt_point1);
		 point2=(TextView) findViewById(R.id.txt_point2);
		 point3=(TextView) findViewById(R.id.txt_point3);
		 point4=(TextView) findViewById(R.id.txt_point4);
		 point5=(TextView) findViewById(R.id.txt_point5);
		 point6=(TextView) findViewById(R.id.txt_point6);
		 
		 heading1=(TextView) findViewById(R.id.heading_1);
		 heading2=(TextView) findViewById(R.id.heading_2);
		 heading3=(TextView) findViewById(R.id.heading_3);
		 heading4=(TextView) findViewById(R.id.heading_4);
		 heading5=(TextView) findViewById(R.id.heading_5);
		 heading6=(TextView) findViewById(R.id.heading_6);
		 marineforecasting=(TextView) findViewById(R.id.title_marineforcasting);
		 
		 
		 if(a==0)
		 {
			 heading1.setText("Introduction");
			 heading2.setText("Selecting a Location");
			 heading3.setText("Search Gas Stations");
			 heading4.setText("Distance Calculation and Fuels");
			 heading5.setText("Developer’s Advise");
			 heading6.setText("PS From the Developer");
			 
			 point1.setText(Html.fromHtml(getString(R.string.english_pt1)));
			 point2.setText(Html.fromHtml(getString(R.string.english_pt2)));
			 point3.setText(Html.fromHtml(getString(R.string.english_pt3)));
			 point4.setText(Html.fromHtml(getString(R.string.english_pt4)));
			 point5.setText(Html.fromHtml(getString(R.string.english_pt5)));
			 point6.setText(Html.fromHtml(getString(R.string.english_pt6)));
			 
		 }
		 
		 else if(a==1)
		 {
			 title.setText("Πώς να χρησιμοποιήσετε την εφαρμογή © MarineForecaster");
			 heading1.setText("Πρόγνωση Καιρού στην Θάλασσα:");
			 heading2.setText("Αναζήτηση Βενζινάδικα:");
			 heading3.setText("Υπολογισμός Απόστασης  & Καυσίμων:");
			 heading4.setText("Συμβουλές του Developer:");
			 heading5.setVisibility(View.GONE);
			 heading6.setVisibility(View.GONE);
			 
			 point1.setText(Html.fromHtml(getString(R.string.greek_pt1)));
			 point2.setText(Html.fromHtml(getString(R.string.greek_pt2)));
			 point3.setText(Html.fromHtml(getString(R.string.greek_pt3)));
			 point4.setText(Html.fromHtml(getString(R.string.greek_pt4)));
			 point5.setVisibility(View.GONE);
			 point6.setVisibility(View.GONE);
			 marineforecasting.setVisibility(View.GONE);
			 
			 
		 }
		 
		 String TEST_DEVICE_ID = "3087ae7ac6faeca3";
	    	AdView adView = (AdView) this.findViewById(R.id.adView);
	       AdRequest adRequest = new AdRequest.Builder()
		        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		        .addTestDevice(TEST_DEVICE_ID)
		        .build();
		    adView.loadAd(adRequest);
	}

}
