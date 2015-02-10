package com.mobilio.marineforecaster;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DistanceCalculate extends FragmentActivity 
{

	
	GoogleMap  googlelMap ;
	Marker mk1,mk2;
	Location locA,locB;
	LinearLayout pinlinear;
	Button removesingle,removeall;
	DecimalFormat df = new DecimalFormat("#.00"); 
	int a=0;
	
	List<Marker> markerlist;
	int markernumber=0;
	double distance=0.0;
	List<Location> locationlist;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		markerlist=new ArrayList<Marker>();
		locationlist=new ArrayList<Location>();
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		
		setContentView(R.layout.distance_layout);
		pinlinear=(LinearLayout) findViewById(R.id.removepinlinear);
		
		locA=new Location("A");
		locB=new Location("B");
		
		removesingle=(Button) findViewById(R.id.button1);
		removeall=(Button) findViewById(R.id.button2);
		pinlinear.setVisibility(View.GONE);
		removesingle.setVisibility(View.INVISIBLE);
		removeall.setVisibility(View.INVISIBLE);
		
		if(a==1)
		{
			removesingle.setText("Κατάργηση Τελευταία");
			removeall.setText("Κατάργηση όλων");
		}
		
		removesingle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int i=markerlist.size()-1;
				
				markerlist.get(i).remove();
				markerlist.remove(i);
				locationlist.remove(i);
				if(markerlist.size()==0)
				{
					removesingle.setVisibility(View.INVISIBLE);
				
				
					pinlinear.setVisibility(View.GONE);
				}
			}
		});
		
		removeall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i=markerlist.size()-1;i>=0;i--)
				{	markerlist.get(i).remove();
					markerlist.remove(i);
					
					locationlist.remove(i);
			}
			
				pinlinear.setVisibility(View.GONE);
				
			}
		});
		 
		if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS){
			SupportMapFragment mapfragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
			googlelMap=mapfragment.getMap();
		}
		
		
		googlelMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
			
			@Override
			public void onMapLongClick(LatLng point) {
				
				Marker newmarker;
				Location newlocation=new Location("Location"+markernumber+1);
				
				if(markerlist.size()<=10)
				{
					 newmarker = googlelMap.addMarker(new MarkerOptions().position(point).snippet("Lattitude : "+point.latitude +"Longitude : "+point.longitude).title("Location "+markernumber+1)); 
					 newlocation.setLatitude(point.latitude);
					 newlocation.setLongitude(point.longitude);
					 
					 pinlinear.setVisibility(View.VISIBLE);
			    	removesingle.setVisibility(View.VISIBLE);
			    	removeall.setVisibility(View.VISIBLE);
			    	
			    	
			    	markerlist.add(newmarker);
			    	locationlist.add(newlocation);
			    	markernumber++;
			    	
			    	Log.i("marker",String.valueOf(markerlist.size()));
				}
				
//		        if(mk1 == null){
//		    		
//				 mk1 = googlelMap.addMarker(new MarkerOptions().position(point).snippet("Lattitude : "+point.latitude +"Longitude : "+point.longitude).title("Location1")); 
//				locA.setLatitude(point.latitude);
//				locA.setLongitude(point.longitude);
//
//				pinlinear.setVisibility(View.VISIBLE);
//	    		removesingle.setVisibility(View.VISIBLE);
//		        }
	    	
//	    		if(mk2!=null)
//	    		{
//	    			float[] results = new float[1];
//	    			//double distance=0;
//	    			Location.distanceBetween(mk1.getPosition().latitude, mk1.getPosition().longitude, mk2.getPosition().latitude, mk2.getPosition().longitude, results);
//	    			
//	    			String message="Distance from Location B = "+df.format(results[0]/1852)+" Nautical Miles";
//	    			if(a==1)
//	    				message="Απόσταση από Θέση A = "+df.format(results[0]/1852)+" ναυτικών μιλίων";
//	    			
//	    			Showdialog(message);
//	    		}
//
//		        }
//		        else if(mk2 == null){
//		        	
//		        	 mk2 = googlelMap.addMarker(new MarkerOptions().position(point).snippet("Lattitude : "+point.latitude +"Longitude : "+point.longitude).title("Location2")); 
//						locA.setLatitude(point.latitude);
//						locA.setLongitude(point.longitude);
//						
//						pinlinear.setVisibility(View.VISIBLE);
//			    		removeall.setVisibility(View.VISIBLE);
//			    		
//			    		if(mk1!=null)
//			    		{
//			    			float[] results = new float[1];
//			    			//double distance=0;
//			    			Location.distanceBetween(mk1.getPosition().latitude, mk1.getPosition().longitude, mk2.getPosition().latitude, mk2.getPosition().longitude, results);
//			    			String message="Distance from Location A = "+df.format(results[0]/1852)+" Nautical Miles";
//			    			if(a==1)
//			    				message="Απόσταση από Θέση B = "+df.format(results[0]/1852)+" ναυτικών μιλίων";
//			    			Showdialog(message);
//			    		}
//
//		        }
//		        
//		        else {
//		        	String message="Please remove a marker before adding a new one";
//		        	if(a==1)
//		        		message="Παρακαλώ να καταργήσετε ένα δείκτη πριν από την προσθήκη ενός νέου";
//		        	Toast.makeText(DistanceCalculate.this, message, Toast.LENGTH_SHORT).show();
//		        }
//			
			
		        }
		});
			
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_calculate, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    menu.clear();
	    getMenuInflater().inflate(R.menu.menu_calculate, menu);
	    if(a==1)
	    {	MenuItem item1 = menu.findItem(R.id.item_calculate);
	    	item1.setTitle("Υπολογισμός Απόστασης");
	    	MenuItem item2 = menu.findItem(R.id.item_calculatefuel);
	    	item2.setTitle("Υπολογισμός Καυσίμων");
	    }
	    return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==R.id.item_calculate)
		{
			
			if(markerlist.size()>=2)
			{
				for(int i=0;i<markerlist.size()-1;i++)
					{
					float[] results = new float[1];
					
					Location.distanceBetween(markerlist.get(i).getPosition().latitude, markerlist.get(i).getPosition().longitude, markerlist.get(i+1).getPosition().latitude, markerlist.get(i+1).getPosition().longitude, results);
					distance+=results[0];
					}
				
				String message="Total Route Distance = "+df.format(distance/1852)+" Nautical Miles";
    			if(a==1)
    				message="Σύνολο διαδρομής Απόσταση = "+df.format(distance/1852)+" ναυτικών μιλίων";
    			distance=0;
    			Showdialog(message);
			}
			
			else
			{
				String message="Please Select at least 2 locations to calculate distance";
    			if(a==1)
    				message="Επιλέξτε τουλάχιστον 2 θέσεις για τον υπολογισμό της απόστασης";
    			
    			Showdialog(message);
			}
		}
		
		else if(item.getItemId()==R.id.item_calculatefuel)
		{
			if(markerlist.size()>=2)
			{
				for(int i=0;i<markerlist.size()-1;i++)
					{
					float[] results = new float[1];
					
					Location.distanceBetween(markerlist.get(i).getPosition().latitude, markerlist.get(i).getPosition().longitude, markerlist.get(i+1).getPosition().latitude, markerlist.get(i+1).getPosition().longitude, results);
					distance+=results[0];
					}
				
				Double distancenautical=distance/1852;
				distance=0;
				startActivity(new Intent(DistanceCalculate.this, FuelCalculate.class).putExtra("distance", distancenautical));
				
				
			}
			
			else
			{
				String message="Please Select at least 2 locations to calculate fuel";
    			if(a==1)
    				message="Επιλέξτε τουλάχιστον 2 θέσεις για τον υπολογισμό της καυσίμων";
    			
    			Showdialog(message);
			}
		}
		return super.onOptionsItemSelected(item);
		
		
	}

	protected void Showdialog(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(message)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   dialog.dismiss();
		               
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	
	
	
	
}
