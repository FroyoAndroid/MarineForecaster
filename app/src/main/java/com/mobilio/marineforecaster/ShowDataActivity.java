package com.mobilio.marineforecaster;

import java.text.DecimalFormat;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDataActivity extends Activity implements OnClickListener{
	
	TextView dangerlevel,windspeed,humidity,watertemp,temperature,pressure,visiblity,significantwaveheight,swellheight,swellperiod,swelldir,winddir,weather;
	TextView dangerlevel_title,windspeed_title,humidity_title,watertemp_title,temperature_title,pressure_title,visiblity_title,weather_title,winddir_title;
	TextView significantwaveheight_title,swellheight_title,swellperiod_title,swelldir_title,date_title;
	
	TextView datetxt,maxtemp,mintemp,distancetxt,sunrise,sunset;
	TextView datetxt_title,maxtemp_title,mintemp_title,closestlocation_title,distancetxt_title,sunrise_title,sunset_title;
	
	Button b00,b03,b06,b09,b12,b15,b18,b21,btn_today,btn_tomorrow,btn_tomorrow2;
	
	int previousclicked=0;
	private InterstitialAd mInterstitialAd;
	public static int adcount=0;
	AdRequest.Builder adRequestBuilder;
	DecimalFormat df = new DecimalFormat("#.00"); 
	
	int a=0;
	private int day = 0;
	Boolean greek=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.show_data);
		
		 mInterstitialAd = new InterstitialAd(this);
         mInterstitialAd.setAdUnitId("ca-app-pub-3533227877792822/2689041953");
         
         adRequestBuilder = new AdRequest.Builder();

         adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
         mInterstitialAd.setAdListener(new AdListener() {
            

             @Override
             public void onAdClosed() {
                
            	 try{
            		 Intent m = new Intent(ShowDataActivity.this,Graph.class);
              	   	 m.putExtra("greek", a);        	   
              	   	 startActivity(m);
              	   	 }
                   catch(Exception ex)
                   {
                	   String message="This feature is not available for your device";
                	   if(a==1)
                		   message="Αυτή η επιλογή δεν είναι συμβατή με την συσκευή σας";
                	   Toast.makeText(ShowDataActivity.this, message, Toast.LENGTH_LONG).show();
                   }
             }
         });
         mInterstitialAd.loadAd(adRequestBuilder.build());
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		
//		datetxt.setText(MainActivity.date);
		
		maxtemp=(TextView) findViewById(R.id.maxtemptxt);
		mintemp=(TextView) findViewById(R.id.mintemptxt);
		sunrise=(TextView) findViewById(R.id.txt_sunrise);
		sunset=(TextView) findViewById(R.id.txt_sunset);
		
		maxtemp.setText(MainActivity.maxtempc.get(0)+"°C="+df.format((((Integer.parseInt(MainActivity.maxtempc.get(0)))*1.8)+32))+"°F");
		mintemp.setText(MainActivity.mintempc.get(0)+"°C="+df.format((((Integer.parseInt(MainActivity.mintempc.get(0)))*1.8)+32))+"°F");
		distancetxt=(TextView) findViewById(R.id.distancetxt);
		
		//TITLES TEXTVIEWS!!!
		
		maxtemp_title = (TextView) findViewById(R.id.textView2);
		mintemp_title = (TextView) findViewById(R.id.TextView01);
		distancetxt_title = (TextView) findViewById(R.id.textView6);
		closestlocation_title = (TextView) findViewById(R.id.textView5);
		date_title = (TextView) findViewById(R.id.date_title);
		
		dangerlevel_title = (TextView) findViewById(R.id.textView3);
		temperature_title = (TextView) findViewById(R.id.TextView003);
		windspeed_title = (TextView) findViewById(R.id.windsp);
		watertemp_title = (TextView) findViewById(R.id.TextView03);
		humidity_title = (TextView) findViewById(R.id.TextView0003);
		significantwaveheight_title = (TextView) findViewById(R.id.TextView0030);
		swellheight_title = (TextView) findViewById(R.id.TextView0031);
		swelldir_title = (TextView) findViewById(R.id.TextView0032);
		swellperiod_title = (TextView) findViewById(R.id.TextView0033);
		pressure_title = (TextView) findViewById(R.id.TextView0034);
		visiblity_title = (TextView) findViewById(R.id.Visiblity); 
		weather_title = (TextView) findViewById(R.id.PHONE4_CELL);
		sunrise_title= (TextView) findViewById(R.id.TextView06);
		sunset_title = (TextView) findViewById(R.id.TextView04);
		winddir_title = (TextView) findViewById(R.id.TextView0039);
		
		
		
		if(a==1){	
			maxtemp_title.setText("Μέγιστη Θερμοκρασία=");
			mintemp_title.setText("Ελάχιστη Θερμοκρασία=");
			date_title.setText("Ημ/νία:");
			
			distancetxt_title.setText("\nαπόσταση "+"\n"+"(μίλι): ");
			closestlocation_title.setText("Πλησιέστερη Μαρίνα\n(γεωγραφικό πλάτος,γεωγραφικό μήκος)");
			
			dangerlevel_title.setText("Επίπεδο Κινδύνου Ανέμου: ");
			temperature_title.setText("Θερμοκρασία: ");
			windspeed_title.setText("Διεύθυνση Ανέμου: ");
			watertemp_title.setText("Θερμοκρασία Θάλασσας:");
			significantwaveheight_title.setText("Ανώτατο Ύψος Κύματος: ");
			swellheight_title.setText("Μέσο Ύψος Κύματος: ");
			swelldir_title.setText("Κατεύθυνση Κύματος : ");
			swellperiod_title.setText("Περιοδικότητα Κύματος: ");
			humidity_title.setText("Υγρασία: ");
			pressure_title.setText("Ατμοσφαιρική Πίεση: ");
			visiblity_title.setText("Ορατότητα: ");
			weather_title.setText("Καιρός: ");
			sunrise_title.setText("Ανατολή Ηλίου= ");
			sunset_title.setText("Δύση Ηλίου= ");
			windspeed_title.setText("Ταχύτητα Ανέμου= ");
			winddir_title.setText("Διεύθυνση Ανέμου= ");
			
			
		}
		
		
//			distancetxt.setText(MainActivity.nearest_distance_mile);
		
		
		
		
		dangerlevel=(TextView) findViewById(R.id.dangerleveltxt);
		windspeed=(TextView) findViewById(R.id.txtwindspeed);
		humidity=(TextView) findViewById(R.id.txthumidity);
		watertemp=(TextView) findViewById(R.id.txtwatertemp);
		temperature=(TextView) findViewById(R.id.txttemp);
		pressure=(TextView) findViewById(R.id.txtpressure);
		visiblity=(TextView) findViewById(R.id.txtvisiblity);
		significantwaveheight=(TextView) findViewById(R.id.txtwaveheight);
		swellheight=(TextView) findViewById(R.id.txtswellheight);
		swellperiod=(TextView) findViewById(R.id.txtswellperiod);
		swelldir=(TextView) findViewById(R.id.txtswelldir);
		winddir = (TextView) findViewById(R.id.txtwinddir);
		weather = (TextView) findViewById(R.id.txtweather); 
		
		b00=(Button) findViewById(R.id.bttn00);
		b03=(Button) findViewById(R.id.Button03);
		b06=(Button) findViewById(R.id.Button06);
		b09=(Button) findViewById(R.id.Button08);
		b12=(Button) findViewById(R.id.Button02);
		b15=(Button) findViewById(R.id.Button04);
		b18=(Button) findViewById(R.id.Button05);
		b21=(Button) findViewById(R.id.Button07);
		
		btn_today=(Button) findViewById(R.id.bttn_today);
		btn_tomorrow=(Button) findViewById(R.id.bttn_tomorrow);
		btn_tomorrow2=(Button) findViewById(R.id.bttn_dayafter);
		
		btn_today.setBackgroundColor(Color.parseColor("#C8C9CB"));
		btn_today.setTextColor(Color.parseColor("#000000"));
		
		maxtemp.setText(MainActivity.maxtempc.get(0)+"°C="+df.format((((Integer.parseInt(MainActivity.maxtempc.get(0)))*1.8)+32))+"°F");
		mintemp.setText(MainActivity.mintempc.get(0)+"°C="+df.format((((Integer.parseInt(MainActivity.mintempc.get(0)))*1.8)+32))+"°F");

//		maxtemp.setText(MainActivity.maxtempc.get(0)+"ºC");
//		mintemp.setText(MainActivity.mintempc.get(0)+"ºC");
		
		if(a==1)
		{
			b00.setText("00:00 π.μ.");
			b03.setText("3:00 π.μ.");
			b06.setText("6:00 π.μ.");
			b09.setText("9:00 π.μ.");
			b12.setText("12:00 μ.μ.");
			b15.setText("15:00 μ.μ.");
			b18.setText("18:00 μ.μ.");
			b21.setText("21:00 μ.μ.");
		}
		
		b00.setOnClickListener(this);
		b03.setOnClickListener(this);
		b06.setOnClickListener(this);
		b09.setOnClickListener(this);
		b12.setOnClickListener(this);
		b15.setOnClickListener(this);
		b18.setOnClickListener(this);
		b21.setOnClickListener(this);
		btn_today.setOnClickListener(this);
		btn_tomorrow.setOnClickListener(this);
		btn_tomorrow2.setOnClickListener(this);
		
		btn_today.setText(MainActivity.date.get(0));
		btn_tomorrow.setText(MainActivity.date.get(1));
		btn_tomorrow2.setText(MainActivity.date.get(2));
		
		
		
		sunrise.setText(MainActivity.sunrise.get(0));
		sunset.setText(MainActivity.sunset.get(0));
		
		if(a==1)
		{
			StringBuilder build=new StringBuilder();
			String[] temp=MainActivity.sunrise.get(0).split(" ");
			build.append(temp[0]);
			if(temp[1].equalsIgnoreCase("AM"))
				build.append(" π.μ");
			else
				build.append(" μ.μ.");
			sunrise.setText(build.toString());
			
			StringBuilder build2=new StringBuilder();
			String[] temp2=MainActivity.sunset.get(0).split(" ");
			build2.append(temp2[0]);
			if(temp2[1].equalsIgnoreCase("AM"))
				build2.append(" π.μ");
			else
				build2.append(" μ.μ.");
			sunset.setText(build2.toString());
			greek=true;
			
		}
		
		clicked(b00);
		
	}

	private void clicked(Button clickedbttn) 
	{
		if(previousclicked!=0)
		{
			Button mytempbutton=(Button) findViewById(previousclicked);
			mytempbutton.setBackgroundColor(Color.parseColor("#C8C9CB"));
			mytempbutton.setTextColor(Color.parseColor("#000000"));
		}
		
		Button id = b00;
		int a=200;
		if(clickedbttn.getId()==R.id.bttn_today){
		
			day = 0;
			a=0;
			
			id = b00;
			
			btn_today.setBackgroundColor(Color.parseColor("#7C838B"));
			btn_today.setTextColor(Color.parseColor("#FEFEFE"));
			
			id.setBackgroundColor(Color.parseColor("#7C838B"));
			id.setTextColor(Color.parseColor("#FEFEFE"));
			
			btn_tomorrow.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow.setTextColor(Color.parseColor("#000000"));
			
			btn_tomorrow2.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow2.setTextColor(Color.parseColor("#000000"));
		
			maxtemp.setText(MainActivity.maxtempc.get(0)+"°C="+df.format((((Integer.parseInt(MainActivity.maxtempc.get(0)))*1.8)+32))+"°F");
			mintemp.setText(MainActivity.mintempc.get(0)+"°C="+df.format((((Integer.parseInt(MainActivity.mintempc.get(0)))*1.8)+32))+"°F");

			
			sunrise.setText(MainActivity.sunrise.get(0));
			sunset.setText(MainActivity.sunset.get(0));
			
			if(greek)
			{
				StringBuilder build=new StringBuilder();
				String[] temp=MainActivity.sunrise.get(0).split(" ");
				build.append(temp[0]);
				if(temp[1].equalsIgnoreCase("AM"))
					build.append(" π.μ");
				else
					build.append(" μ.μ.");
				sunrise.setText(build.toString());
				
				StringBuilder build2=new StringBuilder();
				String[] temp2=MainActivity.sunset.get(0).split(" ");
				build2.append(temp2[0]);
				if(temp2[1].equalsIgnoreCase("AM"))
					build2.append(" π.μ");
				else
					build2.append(" μ.μ.");
				sunset.setText(build2.toString());
				greek=true;
				
			}
			
		}
		
		else if(clickedbttn.getId()==R.id.bttn_tomorrow){	
			
			day = 1;
			a=0;
			id = b00;
			
			btn_tomorrow.setBackgroundColor(Color.parseColor("#7C838B"));
			btn_tomorrow.setTextColor(Color.parseColor("#FEFEFE"));
		
			id.setBackgroundColor(Color.parseColor("#7C838B"));
			id.setTextColor(Color.parseColor("#FEFEFE"));
			
			btn_today.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_today.setTextColor(Color.parseColor("#000000"));
		
			btn_tomorrow2.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow2.setTextColor(Color.parseColor("#000000"));
			
			maxtemp.setText(MainActivity.maxtempc.get(1)+"°C="+df.format((((Integer.parseInt(MainActivity.maxtempc.get(1)))*1.8)+32))+"°F");
			mintemp.setText(MainActivity.mintempc.get(1)+"°C="+df.format((((Integer.parseInt(MainActivity.mintempc.get(1)))*1.8)+32))+"°F");

			sunrise.setText(MainActivity.sunrise.get(1));
			sunset.setText(MainActivity.sunset.get(1));
			
			if(greek)
			{
				StringBuilder build=new StringBuilder();
				String[] temp=MainActivity.sunrise.get(1).split(" ");
				build.append(temp[0]);
				if(temp[1].equalsIgnoreCase("AM"))
					build.append(" π.μ");
				else
					build.append(" μ.μ.");
				sunrise.setText(build.toString());
				
				StringBuilder build2=new StringBuilder();
				String[] temp2=MainActivity.sunset.get(1).split(" ");
				build2.append(temp2[0]);
				if(temp2[1].equalsIgnoreCase("AM"))
					build2.append(" π.μ");
				else
					build2.append(" μ.μ.");
				sunset.setText(build2.toString());
				greek=true;
				
			}
		}
		
		else if(clickedbttn.getId()==R.id.bttn_dayafter){	
			day = 2;
			a=0;
			id = b00;
			
			btn_tomorrow2.setBackgroundColor(Color.parseColor("#7C838B"));
			btn_tomorrow2.setTextColor(Color.parseColor("#FEFEFE"));

			id.setBackgroundColor(Color.parseColor("#7C838B"));
			id.setTextColor(Color.parseColor("#FEFEFE"));
			
			btn_tomorrow.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow.setTextColor(Color.parseColor("#000000"));
			
			btn_today.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_today.setTextColor(Color.parseColor("#000000"));
			
//			maxtemp.setText(MainActivity.maxtempc.get(2)+"ºC");
//			mintemp.setText(MainActivity.mintempc.get(2)+"ºC");
//			
			
			maxtemp.setText(MainActivity.maxtempc.get(2)+"°C="+df.format((((Integer.parseInt(MainActivity.maxtempc.get(2)))*1.8)+32))+"°F");
			mintemp.setText(MainActivity.mintempc.get(2)+"°C="+df.format((((Integer.parseInt(MainActivity.mintempc.get(2)))*1.8)+32))+"°F");

			sunrise.setText(MainActivity.sunrise.get(2));
			sunset.setText(MainActivity.sunset.get(2));
			
			if(greek)
			{
				StringBuilder build=new StringBuilder();
				String[] temp=MainActivity.sunrise.get(2).split(" ");
				build.append(temp[0]);
				if(temp[1].equalsIgnoreCase("AM"))
					build.append(" π.μ");
				else
					build.append(" μ.μ.");
				sunrise.setText(build.toString());
				
				StringBuilder build2=new StringBuilder();
				String[] temp2=MainActivity.sunset.get(2).split(" ");
				build2.append(temp2[0]);
				if(temp2[1].equalsIgnoreCase("AM"))
					build2.append(" π.μ");
				else
					build2.append(" μ.μ.");
				sunset.setText(build2.toString());
				greek=true;
				
			}
		}
		
		else {
			id = clickedbttn;
			if(id.getId()!=b00.getId())
				b00.setBackgroundColor(Color.parseColor("#C8C9CB"));
				b00.setTextColor(Color.parseColor("#000000"));
			
		}
	
		clickedbttn.setBackgroundColor(Color.parseColor("#7C838B"));
		clickedbttn.setTextColor(Color.parseColor("#FEFEFE"));
		
		previousclicked=clickedbttn.getId();

		dangerlevel.setText(MainActivity.Warning_level.get((day*8)+Integer.parseInt(id.getTag().toString())));
		humidity.setText(MainActivity.Humidity.get((day*8)+Integer.parseInt(id.getTag().toString()))+"%");
		pressure.setText(MainActivity.Pressure.get((day*8)+Integer.parseInt(id.getTag().toString()))+" pa");
		temperature.setText(MainActivity.CTemperature.get((day*8)+Integer.parseInt(id.getTag().toString()))+"ºC = "+MainActivity.FTemperature.get(Integer.parseInt(id.getTag().toString()))+"ºF");
		watertemp.setText(MainActivity.Water_Temperature_C.get((day*8)+Integer.parseInt(id.getTag().toString()))+"ºC = "+MainActivity.Water_Temperature_F.get(Integer.parseInt(id.getTag().toString()))+"ºF");
		visiblity.setText(MainActivity.Visibility.get((day*8)+Integer.parseInt(id.getTag().toString()))+" km= "+df.format(Double.parseDouble(MainActivity.Visibility.get(Integer.parseInt(id.getTag().toString())))*0.539957)+" nmi");
		significantwaveheight.setText(MainActivity.Significant_Wave_Height.get((day*8)+Integer.parseInt(id.getTag().toString()))+"m="+df.format(Double.parseDouble(MainActivity.Significant_Wave_Height.get(Integer.parseInt(id.getTag().toString())))*3.28084)+"ft");
		swelldir.setText(MainActivity.Swell_Direction.get((day*8)+Integer.parseInt(id.getTag().toString())));
		winddir.setText(MainActivity.Wind_Dir_Degree.get((day*8)+Integer.parseInt(id.getTag().toString())));		
		swellperiod.setText(MainActivity.Swell_Period.get((day*8)+Integer.parseInt(id.getTag().toString()))+" sec");
		swellheight.setText(MainActivity.Swell_Height.get((day*8)+Integer.parseInt(id.getTag().toString()))+" m="+df.format(Double.parseDouble(MainActivity.Swell_Height.get(Integer.parseInt(id.getTag().toString())))*3.28084)+"ft");
		windspeed.setText(MainActivity.Wind_Speed_Miles.get((day*8)+Integer.parseInt(id.getTag().toString()))+"mph = "+MainActivity.Wind_Speed_Kmph.get((day*8)+Integer.parseInt(id.getTag().toString()))+"kph = "+String.valueOf(df.format(0.539957*Integer.parseInt(MainActivity.Wind_Speed_Kmph.get((day*8)+Integer.parseInt(id.getTag().toString())))))+" knots");
		weather.setText(MainActivity.Weather_Condition.get((day*8)+Integer.parseInt(id.getTag().toString())));
		
		if(day==0){
			btn_today.setBackgroundColor(Color.parseColor("#7C838B"));
			btn_today.setTextColor(Color.parseColor("#FEFEFE"));
			
			btn_tomorrow.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow.setTextColor(Color.parseColor("#000000"));
			
			btn_tomorrow2.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow2.setTextColor(Color.parseColor("#000000"));
		}

		else if(day==1){
			btn_tomorrow.setBackgroundColor(Color.parseColor("#7C838B"));
			btn_tomorrow.setTextColor(Color.parseColor("#FEFEFE"));
		
			btn_today.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_today.setTextColor(Color.parseColor("#000000"));
		
			btn_tomorrow2.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow2.setTextColor(Color.parseColor("#000000"));
			
		}
		
		else if(day==2){
			btn_tomorrow2.setBackgroundColor(Color.parseColor("#7C838B"));
			btn_tomorrow2.setTextColor(Color.parseColor("#FEFEFE"));
			
			btn_tomorrow.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_tomorrow.setTextColor(Color.parseColor("#000000"));
			
			btn_today.setBackgroundColor(Color.parseColor("#C8C9CB"));
			btn_today.setTextColor(Color.parseColor("#000000"));
		}
		
	}

	@Override
	public void onClick(View v) {
		
		clicked((Button) v);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.currency_menu, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		adcount++;
		if (mInterstitialAd.isLoaded()&&adcount%3==0) {
            mInterstitialAd.show();
        } 
		 else {
       try{
		 Intent m = new Intent(ShowDataActivity.this,Graph.class);
  	   	 m.putExtra("greek", a);        	   
  	   	 startActivity(m);
  	   	 }
       catch(Exception ex)
       {
    	   String message="This feature is not available for your device";
    	   if(a==1)
    		   message="Αυτή η επιλογή δεν είναι συμβατή με την συσκευή σας";
    	   Toast.makeText(ShowDataActivity.this, message, Toast.LENGTH_LONG).show();
       }
		 }
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		   mInterstitialAd.loadAd(adRequestBuilder.build());
		super.onResume();
	}

}
