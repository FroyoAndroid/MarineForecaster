package com.mobilio.marineforecaster;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {


    private String urll;
    public TextView xmlShow;
    private EditText lattitudeEditText;
    private EditText longitudeEditText;
    private Button getData;
    public Intent dataShowIntent;
    public TextView succcess;
    public TextView appTitle;
    public TextView lat;
    public TextView lon;
    GoogleMap googlelMap;
    Marker mk;
    Button fav;
    TextView error;
    Intent vj;
    public static List<String> Time;
    public static List<String> CTemperature;
    public static List<String> FTemperature;
    public static List<String> Wind_Speed_Miles;
    public static List<String> Wind_Speed_Kmph;
    public static List<String> Water_Temperature_C;
    public static List<String> Water_Temperature_F;
    public static List<String> Humidity;
    public static List<String> Significant_Wave_Height;
    public static List<String> Swell_Height;
    public static List<String> Swell_Direction;
    public static List<String> Swell_Period;
    public static List<String> Pressure;
    public static List<String> Visibility;
    public static List<String> Weather_Condition;
    public static List<String> Warning_level;
    public static List<Double> Wind_Speed_Kmph_Num;
    public static List<Double> Wind_Speed_mps_Num;
    public static List<String> Wind_Dir_Degree;
    public static List<Double> Beaufort;
    public static List<String> nearest_longitude;
    public static List<String> nearest_latitude;
    public static List<String> nearest_distance_mile;
    public static List<String> date;
    public static List<String> maxtempc;
    public static List<String> mintempc;
    public static List<String> nearest_location_name;
    public static List<String> sunrise;
    public static List<String> sunset;

    int a = 0;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3533227877792822/2689041953");


        AdRequest.Builder adRequestBuilder = new AdRequest.Builder();

        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        mInterstitialAd.setAdListener(new AdListener() {


            @Override
            public void onAdClosed() {
                Intent m = new Intent(MainActivity.this, ShowDataActivity.class);
                m.putExtra("greek", a);
                startActivity(m);
            }
        });
        mInterstitialAd.loadAd(adRequestBuilder.build());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String listPrefs = prefs.getString("listpref", "English");

        if (listPrefs.equalsIgnoreCase("Greek"))
            a = 1;

        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS) {
            SupportMapFragment mapfragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            googlelMap = mapfragment.getMap();
            lattitudeEditText = (EditText) findViewById(R.id.latEditText);
            longitudeEditText = (EditText) findViewById(R.id.longEditText);
            lat = (TextView) findViewById(R.id.lattitude);
            lon = (TextView) findViewById(R.id.longitude);
            getData = (Button) findViewById(R.id.GetData);


            lattitudeEditText.clearFocus();

            longitudeEditText.clearFocus();


            if (a == 1) {

                lat.setText("Γεωγραφικό Πλάτος");
                lon.setText("Γεωγραφικό Μήκος");
                getData.setText("Πληροφορίες");
            }

            Time = new ArrayList<String>();
            CTemperature = new ArrayList<String>();
            FTemperature = new ArrayList<String>();
            Wind_Speed_Miles = new ArrayList<String>();
            Wind_Speed_Kmph = new ArrayList<String>();
            Water_Temperature_C = new ArrayList<String>();
            Water_Temperature_F = new ArrayList<String>();
            Humidity = new ArrayList<String>();
            ;
            Significant_Wave_Height = new ArrayList<String>();
            Swell_Height = new ArrayList<String>();
            Swell_Direction = new ArrayList<String>();
            Swell_Period = new ArrayList<String>();
            Pressure = new ArrayList<String>();
            Visibility = new ArrayList<String>();
            Warning_level = new ArrayList<String>();
            Wind_Dir_Degree = new ArrayList<String>();
            sunrise = new ArrayList<String>();
            sunset = new ArrayList<String>();
            Wind_Speed_Kmph_Num = new ArrayList<Double>();
            Wind_Speed_mps_Num = new ArrayList<Double>();
            Beaufort = new ArrayList<Double>();
            Weather_Condition = new ArrayList<String>();

            nearest_longitude = new ArrayList<String>();
            nearest_latitude = new ArrayList<String>();
            nearest_distance_mile = new ArrayList<String>();
            date = new ArrayList<String>();
            maxtempc = new ArrayList<String>();
            mintempc = new ArrayList<String>();
            nearest_location_name = new ArrayList<String>();


            String message = "Long Press Touch on Map to Select a location";
            if (a == 1) {
                message = "Επιλέξτε Θέση στον Χάρτη κρατώντας το δάκτυλό σας για τουλάχιστον 2 δευτερόλεπτα στην οθόνη";
            }
            Toast t = Toast.makeText(this, message, Toast.LENGTH_LONG);
            t.show();
            googlelMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                @Override
                public void onMapLongClick(LatLng point) {
                    if (mk == null) {

                        mk = googlelMap.addMarker(new MarkerOptions().position(point).snippet("Lattitude : " + point.latitude + "Longitude : " + point.longitude).title("Coordinates"));
                        lattitudeEditText.setText(("" + point.latitude).substring(0, 9));
                        longitudeEditText.setText(("" + point.longitude).substring(0, 9));
                        //   fav.setVisibility(View.VISIBLE);
                    } else {

                        mk.remove();
                        mk = googlelMap.addMarker(new MarkerOptions().position(point).snippet("Lattitude : " + point.latitude + "Longitude : " + point.longitude).title("Coordinates"));
                        lattitudeEditText.setText("" + point.latitude);
                        longitudeEditText.setText("" + point.longitude);

                    }

                }
            });

        } else {
            String message = "Google Play services are not available";
            if (a == 1)
                message = "Υπηρεσίες του Google Play δεν είναι διαθέσιμη";
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            //GooglePlayServicesUtil.getErrorDialog(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this),this,1001).show();
        }
    }


    public void clickHandler(View v) {


        if (lattitudeEditText.getText().toString().isEmpty() && longitudeEditText.getText().toString().isEmpty()) {
            String message = "Please Select a Location";
            if (a == 1)
                message = "παρακαλούμε επιλέξτε μια θέση";
            Toast.makeText(MainActivity.this, "Please Select a Location", Toast.LENGTH_SHORT).show();
        } else if (networkStatus(MainActivity.this)) {
            urll = "http://api.worldweatheronline.com/premium/v1/marine.ashx?q=" + lattitudeEditText.getText().toString() + "%2C" + longitudeEditText.getText().toString() + "&format=xml&key=2251cc1f05bf1237504980def3f9567e7af8e746";
            new LoadAssync().execute();

        } else {
            String message = "No Active Internet Connection Found";
            if (a == 1)
                message = "βρέθηκε καμία ενεργή σύνδεση στο Internet";
            Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
            toast.setGravity(0, 0, 300);
            toast.show();

        }

    }

    private class LoadAssync extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            dialog.setMessage("Contacting Weather Service...");
            dialog.show();
        }

        protected Void doInBackground(final String... args) {

            try {
                getdata();

                updateDateandTemperature();
                //updateNearestArea();
                updatedirection();
                updateAstronomy();
                updateweather();
                Log.i("ahmed2222", String.valueOf(date.size()) + "  " + String.valueOf(Wind_Dir_Degree.size()) + "  " + String.valueOf(sunrise.size()));


            } catch (Exception ex) {
                String message = "An error occured";
                if (a == 1)
                    message = "Παρουσιάστηκε σφάλμα";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            return null;

        }

        protected void onPostExecute(final Void unused) {

            if (dialog.isShowing()) {
                dialog.dismiss();

                if (Time.size() == 0) {
                    nearest_distance_mile.clear();
                    nearest_latitude.clear();
                    nearest_location_name.clear();
                    nearest_longitude.clear();
                    maxtempc.clear();
                    mintempc.clear();
                    date.clear();
                    sunrise.clear();
                    sunset.clear();


                    String message = "No Data received for this area";
                    if (a == 1)
                        message = "δεν λαμβάνονται δεδομένα για αυτή την περιοχή";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Intent m = new Intent(MainActivity.this, ShowDataActivity.class);
                        m.putExtra("greek", a);
                        startActivity(m);
                    }
                }


                //xmlShow.setText(result);
            }


        }


        private void updatefeeds() {
            try {
                Time.clear();
                CTemperature.clear();
                FTemperature.clear();
                Wind_Speed_Miles.clear();
                Wind_Speed_Kmph.clear();
                Wind_Speed_Kmph_Num = new ArrayList<Double>();
                Wind_Speed_mps_Num = new ArrayList<Double>();
                Water_Temperature_C.clear();
                Water_Temperature_F.clear();
                Humidity.clear();
                ;
                Significant_Wave_Height.clear();
                Swell_Height.clear();
                Swell_Direction.clear();
                Swell_Period.clear();
                Pressure.clear();
                Visibility.clear();
                Warning_level.clear();
                Beaufort.clear();
                Wind_Dir_Degree.clear();
                Weather_Condition.clear();

                URL url = new URL(urll);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();


                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;


                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("hourly")) {

                            insideItem = true;

                        }

//		            else if (xpp.getName().equalsIgnoreCase("hourly")) {
//		
//		                if (insideItem)
//		
//		                   Log.i("ahmed",(xpp.nextText())); //title
//		
//		            }
//		            
                        else if (xpp.getName().equalsIgnoreCase("time")) {

                            if (insideItem) {
                                Time.add(xpp.nextText());

                            }
                        } else if (xpp.getName().equalsIgnoreCase("tempc")) {

                            if (insideItem) {
                                CTemperature.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("tempf")) {

                            if (insideItem) {
                                FTemperature.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("windspeedMiles")) {

                            if (insideItem) {
                                Wind_Speed_Miles.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("windspeedKmph")) {

                            if (insideItem) {
                                Wind_Speed_Kmph.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("winddirdegree")) {

                            if (insideItem) {
                                Wind_Dir_Degree.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("waterTemp_C")) {

                            if (insideItem) {
                                Water_Temperature_C.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("waterTemp_F")) {

                            if (insideItem) {
                                Water_Temperature_F.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("humidity")) {

                            if (insideItem) {
                                Humidity.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("sigHeight_m")) {

                            if (insideItem) {
                                Significant_Wave_Height.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("swellHeight_m")) {

                            if (insideItem) {
                                Swell_Height.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("swellDir")) {

                            if (insideItem) {
                                Swell_Direction.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("swellPeriod_secs")) {

                            if (insideItem) {
                                Swell_Period.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("visibility")) {

                            if (insideItem) {
                                Visibility.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("pressure")) {

                            if (insideItem) {
                                Pressure.add(xpp.nextText());


                            }
                        } else if (xpp.getName().equalsIgnoreCase("weatherDesc")) {

                            if (insideItem) {
                                Weather_Condition.add(xpp.nextText());
                                Log.i("lulu", Weather_Condition.get(Weather_Condition.size() - 1));

                            }
                        }


                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("hourly")) {

                        insideItem = false;

                    }


                    eventType = xpp.next();

                }


            } catch (Exception e) {

                Log.i("anas", e.toString());
            }

        }

        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                return null;
            }
        }

        public void updateDateandTemperature() {
            try {

                URL url = new URL(urll);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                date.clear();
                maxtempc.clear();
                mintempc.clear();

                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;


                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("weather")) {

                            insideItem = true;

                        } else if (xpp.getName().equalsIgnoreCase("date")) {

                            if (insideItem) {
                                date.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("maxtempc")) {

                            if (insideItem) {
                                maxtempc.add(xpp.nextText());
                            }
                        } else if (xpp.getName().equalsIgnoreCase("mintempc")) {

                            if (insideItem) {
                                mintempc.add(xpp.nextText());
                            }
                        }


                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("weather")) {

                        insideItem = false;

                    }


                    eventType = xpp.next();

                }


            } catch (Exception e) {


            }

        }

        public void updateAstronomy() {
            try {

                URL url = new URL(urll);

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                sunrise.clear();
                sunset.clear();

                xpp.setInput(getInputStream(url), "UTF_8");

                boolean insideItem = false;


                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {

                        if (xpp.getName().equalsIgnoreCase("astronomy")) {

                            insideItem = true;

                        } else if (xpp.getName().equalsIgnoreCase("sunrise")) {

                            if (insideItem) {
                                sunrise.add(xpp.nextText());

                            }
                        } else if (xpp.getName().equalsIgnoreCase("sunset")) {

                            if (insideItem) {
                                sunset.add(xpp.nextText());
                            }
                        }


                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("weather")) {

                        insideItem = false;

                    }


                    eventType = xpp.next();

                }


            } catch (Exception e) {


            }

        }

	/*
    public void updateNearestArea()
	{
try {
				URL url = new URL(urll);
		
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		    factory.setNamespaceAware(false);
		    XmlPullParser xpp = factory.newPullParser();
		
		    
		    xpp.setInput(getInputStream(url), "UTF_8");
		        
		    boolean insideItem = false;
		
		
		    int eventType = xpp.getEventType();
			
		    while (eventType != XmlPullParser.END_DOCUMENT) {
		
		        if (eventType == XmlPullParser.START_TAG) {
	
		            if (xpp.getName().equalsIgnoreCase("nearest_area")) {
		
		                insideItem = true;
		
		            }
		         
		            else if (xpp.getName().equalsIgnoreCase("latitude")) {
		
		                if (insideItem)
		                {
		                	nearest_latitude=xpp.nextText();  	
		                	
		                }
		            } else if (xpp.getName().equalsIgnoreCase("longitude")) {
		
		                if (insideItem)
		                {
		                	nearest_longitude=xpp.nextText();
		                }
		            } else if (xpp.getName().equalsIgnoreCase("distance_miles")) {
		
		                if (insideItem)
		                {
		                	nearest_distance_mile=xpp.nextText();   	
		                }
		            } 

		
		        }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("nearest_area")){
		
		            insideItem=false;
		
		        }
		
		 
		
		        eventType = xpp.next(); 
		
		    }
		    
		    Geocoder geocoder = new Geocoder(MainActivity.this);
		    
		    List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(nearest_latitude), Double.parseDouble(nearest_longitude), 1);
		    nearest_location_name="";
		    if(addresses.get(0).getAddressLine(0)!=null&&addresses.get(0).getAddressLine(2)!=null)
		    	nearest_location_name = addresses.get(0).getAddressLine(0)+" "+addresses.get(0).getAddressLine(2);
		    else if(addresses.get(0).getAddressLine(0)!=null&&addresses.get(0).getAddressLine(2)==null)
		    	nearest_location_name = addresses.get(0).getAddressLine(0);
		    else if(addresses.get(0).getAddressLine(0)==null&&addresses.get(0).getAddressLine(2)!=null)
		    	nearest_location_name = addresses.get(0).getAddressLine(2);
		    else	
		    { nearest_location_name = "";
		    	
		    }
		    
		   
		   
		
		
		}catch (Exception e){
			
			Log.i("error", e.toString());
			Log.i("error", e.getMessage());
				
		}
	}*/


        public void getdata() {
            updatefeeds();

            for (int i = 0; i <= Wind_Speed_Kmph.size() - 1; i++)
                Wind_Speed_Kmph_Num.add(Double.parseDouble(Wind_Speed_Kmph.get(i)));

            for (int i = 0; i <= Wind_Speed_Kmph.size() - 1; i++)
                Wind_Speed_mps_Num.add(Wind_Speed_Kmph_Num.get(i) * 1000.0 / 3600.0);

            for (int i = 0; i <= Wind_Speed_mps_Num.size() - 1; i++)
                Beaufort.add(Math.cbrt((Wind_Speed_mps_Num.get(i) / 0.837) * (Wind_Speed_mps_Num.get(i) / 0.837)));

            updatewarninglevels();

        }


        public void updatedirection() {
            for (int i = 0; i < Swell_Direction.size(); i++) {
                if (Double.parseDouble(Swell_Direction.get(i)) >= 337.5 || Double.parseDouble(Swell_Direction.get(i)) <= 22.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°N");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 67.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°NE");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 112.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°E");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 157.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°SE");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 202.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°S");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 247.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°SW");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 292.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°W");
                else if (Double.parseDouble(Swell_Direction.get(i)) <= 337.5)
                    Swell_Direction.set(i, Swell_Direction.get(i) + "°NW");
            }

            for (int i = 0; i < Wind_Dir_Degree.size(); i++) {
                if (Double.parseDouble(Wind_Dir_Degree.get(i)) >= 337.5 || Double.parseDouble(Wind_Dir_Degree.get(i)) <= 22.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°N");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 67.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°NE");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 112.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°E");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 157.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°SE");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 202.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°S");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 247.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°SW");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 292.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°W");
                else if (Double.parseDouble(Wind_Dir_Degree.get(i)) <= 337.5)
                    Wind_Dir_Degree.set(i, Wind_Dir_Degree.get(i) + "°NW");
            }


        }

        public void updatewarninglevels() {
            for (int i = 0; i <= Beaufort.size() - 1; i++) {
                if (a == 0) {
                    if (Beaufort.get(i) > 0 && Beaufort.get(i) <= 3.5)
                        Warning_level.add("Calm 0 - 3.5 of Beaufort scale");
                    else if (Beaufort.get(i) <= 5.6)
                        Warning_level.add("Windy 3.5- 5.5 of Beaufort scale");
                    else if (Beaufort.get(i) <= 9.5)
                        Warning_level.add("Stormy 5.6- 9.5 of Beaufort scale");
                    else
                        Warning_level.add("Hurricane 9.5 - up of Beaufort scale");
                } else {
                    if (Beaufort.get(i) > 0 && Beaufort.get(i) <= 3.5)
                        Warning_level.add("Άπνοια έως Ασθενής 0 - 3.5 Μποφόρ");
                    else if (Beaufort.get(i) <= 5.6)
                        Warning_level.add("Ασθενής έως Μέτριος 3.5- 5.5 Μποφόρ");
                    else if (Beaufort.get(i) <= 9.5)
                        Warning_level.add("Ισχυρός έως Πολύ Θυελλώδης 5.6- 9.5 Μποφόρ");
                    else
                        Warning_level.add("Θυελλώδης έως Κατηγορία Τυφώνα 9.5 - 12 Μποφόρ");
                }
            }

        }

        public void updateweather() {
//		Log.i("pppp","updating weather");
//		Log.i("pppp","a= "+ String.valueOf(a));
//		Log.i("ppp",Weather_Condition.get(0)+"size = "+String.valueOf(Weather_Condition.get(0).length()));
//		String ahg="oooo";
//		 

            for (int i = 0; i <= Weather_Condition.size() - 1; i++) {
                if (a == 1) {

                    if (Weather_Condition.get(i).trim().equalsIgnoreCase("Clear")) {
                        Log.i("ppp", Weather_Condition.get(i));
                        Weather_Condition.set(i, "Συννεφιά");
                    } else if (Weather_Condition.get(i).trim().equalsIgnoreCase("sunny"))
                        Weather_Condition.set(i, "Ηλιοφάνεια");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("patchy light drizzle"))
                        Weather_Condition.set(i, "Ψιχαλίζει ελαφρά");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("light rain shower"))
                        Weather_Condition.set(i, "Ελαφριά βροχόπτωση");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Patchy light area with thunder"))
                        Weather_Condition.set(i, "Τοπική ελαφριά βροχόπτωση με κεραυνούς");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Patchy rain nearby"))
                        Weather_Condition.set(i, "Τοπική βροχόπτωση");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Moderate or heavy rain shower"))
                        Weather_Condition.set(i, "Μέτρια έως ισχυρή βροχὀπτωση");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Partly cloudy"))
                        Weather_Condition.set(i, "Μερικώς συννεφιασμένος");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Light rain"))
                        Weather_Condition.set(i, "Ασθενής Βροχή");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Light drizzle"))
                        Weather_Condition.set(i, "Ψιλόβροχο");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Cloudy"))
                        Weather_Condition.set(i, "Συνεφιά");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Blowing snow"))
                        Weather_Condition.set(i, "Χιονοθύελλα");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Heavy rain"))
                        Weather_Condition.set(i, "Δυνατή βροχόπτωση");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Patchy snow nearby"))
                        Weather_Condition.set(i, "Τοπικές χιονοπτώσεις");

                    else if (Weather_Condition.get(i).trim().equalsIgnoreCase("Τοπικές χιονοπτώσεις"))
                        Weather_Condition.set(i, "Ελαφριά χιονόπτωση");


                }

            }

        }
    }

    public static boolean networkStatus(Context context) {

        return (isWifiAvailable(context) || isMobileNetworkAvailable(context));

    }

    public static boolean isMobileNetworkAvailable(Context ctx) {

        ConnectivityManager connecManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo myNetworkInfo = connecManager

                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (myNetworkInfo.isConnected()) {

            return true;

        } else {

            return false;

        }

    }

    public static boolean isWifiAvailable(Context ctx) {

        ConnectivityManager myConnManager = (ConnectivityManager) ctx

                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo myNetworkInfo = myConnManager

                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (myNetworkInfo.isConnected())

            return true;

        else

            return false;

    }

}
