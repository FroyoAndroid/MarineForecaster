package com.mobilio.marineforecaster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class greekdatasource {


	public static final String LOGTAG = "AHMED";
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allcolumns= {
		
		OpenHelper.COLUMN_STATE,
		OpenHelper.COLUMN_CITY,
		OpenHelper.COLUMN_PHONE1,
		OpenHelper.COLUMN_PHONE2,		
		OpenHelper.COLUMN_PHONE3,
		OpenHelper.COLUMN_PHONE4,
				
	};
	
	public greekdatasource(Context context) throws IOException{
		
		dbhelper = new OpenHelper(context);
		
	}
	
	public GreekStation greek_create(GreekStation greek){

		ContentValues values= new ContentValues();
		
		values.put(OpenHelper.COLUMN_STATE, greek.getState());
		values.put(OpenHelper.COLUMN_CITY, greek.getCity());
		values.put(OpenHelper.COLUMN_PHONE1, greek.getPhone1());
		values.put(OpenHelper.COLUMN_PHONE2, greek.getPhone2());
		values.put(OpenHelper.COLUMN_PHONE3, greek.getPhone3());
		values.put(OpenHelper.COLUMN_PHONE4, greek.getPhone4());
		long inserid= database.insert(OpenHelper.TABLE_GREEK_STATIONS, null, values);
		
		
		return greek;
	}
	

	
	public List<GreekStation> findall(){
		
		List<GreekStation> greeks= new ArrayList<GreekStation>();
		
		Cursor cursor= database.query(OpenHelper.TABLE_GREEK_STATIONS, allcolumns, null, null, null, null, null, null); 
		
		Log.i(LOGTAG, "Returned "+ cursor.getCount() + " rows");
			
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				
				GreekStation greek= new GreekStation();
				greek.setState(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)));
				greek.setCity(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_CITY)));
				greek.setPhone1(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE1))); 
				greek.setPhone2(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE2))); 
				greek.setPhone3(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE3))); 
				greek.setPhone4(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE4))); 
				greeks.add(greek);
				
				Log.i("ahmed", "greek Returned "+ cursor.getCount() + " rows");
			}
			
		}
		
		return greeks;
	}
	
	public List<GreekStation> findbyState(String state)
	{
			List<GreekStation> greeks= new ArrayList<GreekStation>();
			
			Cursor cursor= database.query(OpenHelper.TABLE_GREEK_STATIONS, allcolumns, null, null, null, null, null, null); 
			
			if(cursor.getCount()>0){
				while(cursor.moveToNext()){
					if(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)).equalsIgnoreCase(state)){//when id is matched, entries will be saved into mynote
					
						GreekStation greek= new GreekStation();
						greek.setState(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)));
						greek.setCity(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_CITY)));
						greek.setPhone1(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE1))); 
						greek.setPhone2(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE2))); 
						greek.setPhone3(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE3))); 
						greek.setPhone4(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE4))); 
						greeks.add(greek);
				
					}
				}
				
			}
			
			return greeks;
		
	}

	public List<GreekStation> findbyCity(String city)
	{
			List<GreekStation> greeks= new ArrayList<GreekStation>();
			
			Cursor cursor= database.query(OpenHelper.TABLE_GREEK_STATIONS, allcolumns, null, null, null, null, null, null); 
			
			if(cursor.getCount()>0){
				while(cursor.moveToNext()){
					if(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)).trim().equalsIgnoreCase(city)){//when id is matched, entries will be saved into mynote
					
						GreekStation greek= new GreekStation();
						greek.setState(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)));
						greek.setCity(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_CITY)));
						greek.setPhone1(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE1))); 
						greek.setPhone2(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE2))); 
						greek.setPhone3(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE3))); 
						greek.setPhone4(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE4))); 
						greeks.add(greek);
				
					}
				}
				
			}
			
			return greeks;
		
	}
	
	public List<String> findDistinctStates(){
		
		List<String> states = new ArrayList<String>();
		Cursor cursor1 = database.rawQuery("select distinct State from GreekStations",null);
		
		if(cursor1.getCount()>0){
			while(cursor1.moveToNext()){
				
					states.add(cursor1.getString(cursor1.getColumnIndex(OpenHelper.COLUMN_STATE)));			
			}
			
		}
		
		Log.i("pk11", String.valueOf(states.size()));
		return states;
	}
	
	public boolean Removenull(){
		
		String where = "State=null";
		String where1 = "State=''";
		int result = database.delete(OpenHelper.TABLE_GREEK_STATIONS, where, null);
		int result1 = database.delete(OpenHelper.TABLE_GREEK_STATIONS, where1, null);
		
		return (result==1);
	}
	
	
	public void open(){
		
		database = dbhelper.getWritableDatabase();
		Log.i(LOGTAG, "Database opened");
	}
	
	public void close(){
		
		Log.i(LOGTAG, "Database closed");
		dbhelper.close();
	}
	
}

