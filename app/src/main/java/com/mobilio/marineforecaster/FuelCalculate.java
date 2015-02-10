package com.mobilio.marineforecaster;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FuelCalculate extends Activity {
	TextView fuel_title,txt_result;
	EditText input_mialage;
	Button bttn_calculate;
	int a=0;
	Double distance;
	DecimalFormat df = new DecimalFormat("#.00");
	
	String message="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		 
		 setContentView(R.layout.acticity_calculatefuel);
		 
		 
		 distance=getIntent().getDoubleExtra("distance", 0);
		 
		 fuel_title=(TextView) findViewById(R.id.fuel_title);
		 txt_result=(TextView) findViewById(R.id.txt_result);
		 input_mialage=(EditText) findViewById(R.id.et_avgmialage);
		 bttn_calculate=(Button) findViewById(R.id.bttn_calculate);
		 
		 if(a==1)
			 {
			 fuel_title.setText("Εισάγετε την Μέση Κατανάλωση ανά Ναυτικό Μίλι");
			 bttn_calculate.setText("Υπολογίστε");
			 }
		 
		 bttn_calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!input_mialage.getText().equals(null)||!input_mialage.getText().toString().trim().equals("")||Double.parseDouble(input_mialage.getText().toString())!=0)
				{
					double fuelconsumption=0;
					fuelconsumption=distance*Double.parseDouble(input_mialage.getText().toString());
					message="Total Consumption in liters/Gallons  = "+df.format(fuelconsumption);
	    			if(a==1)
	    				message="Τελική Κατανάλωση σε Λίτρα / Γαλόνια = "+df.format(fuelconsumption);
	    			txt_result.setVisibility(View.VISIBLE);
	    			txt_result.setText(message);
	    			
				}
			}
		});
		
	}

}
