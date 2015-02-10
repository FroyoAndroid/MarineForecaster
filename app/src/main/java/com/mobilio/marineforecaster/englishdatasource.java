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

public class englishdatasource {


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
	
	public englishdatasource(Context context) throws IOException{
		
		dbhelper = new OpenHelper(context);
		
	}
	
	public EnglishStation english_create(EnglishStation english){

		ContentValues values= new ContentValues();
		
		values.put(OpenHelper.COLUMN_STATE, english.getState());
		values.put(OpenHelper.COLUMN_CITY, english.getCity());
		values.put(OpenHelper.COLUMN_PHONE1, english.getPhone1());
		values.put(OpenHelper.COLUMN_PHONE2, english.getPhone2());
		values.put(OpenHelper.COLUMN_PHONE3, english.getPhone3());
		values.put(OpenHelper.COLUMN_PHONE4, english.getPhone4());
		long inserid= database.insert(OpenHelper.TABLE_ENGLISH_STATIONS, null, values);
		
		
		return english;
	}
	

	
	public List<EnglishStation> findall(){
		
		List<EnglishStation> englishs= new ArrayList<EnglishStation>();
		
		Cursor cursor= database.query(OpenHelper.TABLE_ENGLISH_STATIONS, allcolumns, null, null, null, null, null, null); 
		
		Log.i(LOGTAG, "Returned "+ cursor.getCount() + " rows");
			
		if(cursor.getCount()>0){
			while(cursor.moveToNext()){
				
				EnglishStation english= new EnglishStation();
				english.setState(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)));
				english.setCity(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_CITY)));
				english.setPhone1(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE1))); 
				english.setPhone2(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE2))); 
				english.setPhone3(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE3))); 
				english.setPhone4(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE4))); 
				englishs.add(english);
				
				Log.i("ahmed", "english Returned "+ cursor.getCount() + " rows");
			}
			
		}
		
		return englishs;
	}
	
	public List<EnglishStation> findbyState(String state)
	{
			List<EnglishStation> englishs= new ArrayList<EnglishStation>();
			
			Cursor cursor= database.query(OpenHelper.TABLE_ENGLISH_STATIONS, allcolumns, null, null, null, null, null, null); 
			int i = 0;
			if(cursor.getCount()>0){
				while(cursor.moveToNext()){
					if(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)).equalsIgnoreCase(state)){//when id is matched, entries will be saved into mynote
					
						EnglishStation english= new EnglishStation();
						english.setState(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)));
						english.setCity(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_CITY)));
						english.setPhone1(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE1))); 
						english.setPhone2(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE2))); 
						english.setPhone3(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE3))); 
						english.setPhone4(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE4))); 
						
						englishs.add(english);
						i++;
						Log.i("pkkkk", "i="+String.valueOf(i));
					}
				}
				
			}
			
			return englishs;
		
	}

	public List<EnglishStation> findbyCity(String city)
	{
			List<EnglishStation> englishs= new ArrayList<EnglishStation>();
			
			Cursor cursor= database.query(OpenHelper.TABLE_ENGLISH_STATIONS, allcolumns, null, null, null, null, null, null); 
			
			if(cursor.getCount()>0){
				while(cursor.moveToNext()){
					if(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)).equalsIgnoreCase(city)){//when id is matched, entries will be saved into mynote
					
						EnglishStation english= new EnglishStation();
						english.setState(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_STATE)));
						english.setCity(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_CITY)));
						english.setPhone1(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE1))); 
						english.setPhone2(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE2))); 
						english.setPhone3(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE3))); 
						english.setPhone4(cursor.getString(cursor.getColumnIndex(OpenHelper.COLUMN_PHONE4))); 
						englishs.add(english);
				
					}
				}
				
			}
			
			return englishs;
		
	}
	
	public List<String> findDistinctStates(){
		
		List<String> states = new ArrayList<String>();
		Cursor cursor1 = database.rawQuery("select distinct State from EnglishStations",null);
		
		if(cursor1.getCount()>0){
			while(cursor1.moveToNext()){
				
					states.add(cursor1.getString(cursor1.getColumnIndex(OpenHelper.COLUMN_STATE)));			
			}
			
		}
		
		Log.i("pk11", String.valueOf(states.size()));
		return states;
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

