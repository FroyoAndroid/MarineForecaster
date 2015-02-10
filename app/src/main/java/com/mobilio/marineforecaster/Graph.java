package com.mobilio.marineforecaster;

import java.util.ArrayList;
import java.util.List;

import pl.polidea.view.ZoomView;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

@SuppressLint("NewApi")
public class Graph extends Activity implements OnClickListener {

	LinearLayout layout,layout2;
	GraphViewSeries graphSeries = null;
	GraphViewData graphData[];
	GraphView graphView;
	Double d;
	Spinner quantity_spinner;
	List<String> quantities;
	Button today,tomorrow,tomorrow2;
	int a=0;
	private int day=0;
	protected int indexa=0;
	
	private ZoomView zoomView;
	private LinearLayout main_container;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try{
		setContentView(R.layout.graph);
		layout = (LinearLayout) findViewById(R.id.linear);
		layout2 = (LinearLayout) findViewById(R.id.linearlayouttop);
		today = (Button) findViewById(R.id.bttn_today);
		tomorrow = (Button) findViewById(R.id.bttn_tomorrow);
		tomorrow2 = (Button) findViewById(R.id.bttn_dayafter);
		
		today.setOnClickListener(this);
		tomorrow.setOnClickListener(this);
		tomorrow2.setOnClickListener(this);
		
		today.setText(MainActivity.date.get(0));
		tomorrow.setText(MainActivity.date.get(1));
		tomorrow2.setText(MainActivity.date.get(2));
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		
		quantities = new ArrayList<String>();
		
		if(a==0){
		quantities.add("Temperature (°C)");
		quantities.add("Temperature (°F)");
		quantities.add("Wind Speed (Mph)");
		quantities.add("Wind Speed (Kmph)");
		quantities.add("Wind Direction (°)");
		quantities.add("Water Temperature (°C)");
		quantities.add("Water Temperature (°F)");
		quantities.add("Humidity");
		quantities.add("Significant Wave Height");
		quantities.add("Swell Height");
		quantities.add("Swell Direction");
		quantities.add("Swell Period");
		quantities.add("Pressure");
		quantities.add("Visibility");
		quantities.add("Beaufort");
		}
		
		else {
		
			quantities.add("Θερμοκρασία (°C)");
			quantities.add("Θερμοκρασία (°F)");
			quantities.add("Ταχύτητα Ανέμου (Mph)");
			quantities.add("Ταχύτητα Ανέμου (Kmph)");
			quantities.add("Κατεύθυνση ανέμου (°)");
			quantities.add("Θερμοκρασία Θάλασσας (°C)");
			quantities.add("Θερμοκρασία Θάλασσας (°F)");
			quantities.add("Υγρασία (%)");
			quantities.add("Ανώτατο Ύψος Κύματος (m/ft)");
			quantities.add("Μέσο Ύψος Κύματος (m/ft)");
			quantities.add("Κατεύθυνση Κύματος (°)");
			quantities.add("Περιοδικότητα Κύματος (sec)");
			quantities.add("Ατμοσφαιρική Πίεση (pa)");
			quantities.add("Ορατότητα (km/nmi)");
			quantities.add("Μποφόρ");
			
			
		}
		
		quantity_spinner = (Spinner) findViewById(R.id.spinner1);
		quantity_spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,quantities));
		
		quantity_spinner.setSelection(0);
	
		graphData = new GraphViewData[] {
			new GraphViewData(0, (double) Double.parseDouble(MainActivity.CTemperature.get(0))),
			new GraphViewData(1, (double) Double.parseDouble(MainActivity.CTemperature.get(1))),
			new GraphViewData(2, (double) Double.parseDouble(MainActivity.CTemperature.get(2))),
			new GraphViewData(3, (double) Double.parseDouble(MainActivity.CTemperature.get(3))),
			new GraphViewData(4, (double) Double.parseDouble(MainActivity.CTemperature.get(4))),
			new GraphViewData(5, (double) Double.parseDouble(MainActivity.CTemperature.get(5))),
			new GraphViewData(6, (double) Double.parseDouble(MainActivity.CTemperature.get(6))),
			new GraphViewData(7, (double) Double.parseDouble(MainActivity.CTemperature.get(7))),
				 };
		graphSeries = new GraphViewSeries(graphData);
		graphView = new LineGraphView(this,
				"Humidity");

		// graphView = new BarGraphView(container.getContext(), "Temperature");

		graphView.addSeries(graphSeries);

	
		graphView.setHorizontalLabels(new String[] { "12 AM", "3 AM", "6 AM",
				"9 AM", "12 PM", "3 PM", "6 PM", "9 PM" });
		
			
		graphView.getGraphViewStyle().setGridColor(Color.WHITE);
		graphView.getGraphViewStyle().setTextSize(15.0f);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		//graphView.setScaleY(1);
		graphView.setViewPort(0, 40);
		graphView.setScalable(true);
		graphView.setScrollable(true);
		layout.addView(graphView);
		
		quantity_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				indexa = quantity_spinner.getSelectedItemPosition();
				updategraph(quantity_spinner.getSelectedItemPosition());
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		}
		catch(Exception ex)
		{	
			 String message="This feature is not available for your device";
      	   if(a==1)
      		   message="Αυτή η επιλογή δεν είναι συμβατή με την συσκευή σας";
      	   
      	
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   dialog.dismiss();
			               Graph.this.finish();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
		
	}
	protected void updategraph(int index) {
		
		if(index==0){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.CTemperature.get((day*8)+7))),
						 };
			
		}
		
		else if(index==1){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.FTemperature.get((day*8)+7))),
						 };
			
		}
		
		else if(index==2){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Wind_Speed_Miles.get((day*8)+7))),
						 };
			
		}
		
		else if(index==3){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Wind_Speed_Kmph.get((day*8)+7))),
						 };
			
		}
		
		else if(index==4){
			
			for (int i=0;i<MainActivity.Wind_Dir_Degree.size();i++) {
				String[] temp=MainActivity.Wind_Dir_Degree.get(i).split("°");
				MainActivity.Wind_Dir_Degree.set(i, temp[0]);
				
			}
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Wind_Dir_Degree.get((day*8)+7))),
						 };
			
		}
		
		else if(index==5){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Water_Temperature_C.get((day*8)+7))),
						 };
			
		}
		
		else if(index==6){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Water_Temperature_F.get((day*8)+7))),
						 };
			
		}
		
		else if(index==7){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Humidity.get((day*8)+7))),
						 };
			
		}
		
		else if(index==8){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Significant_Wave_Height.get((day*8)+7))),
						 };
			
		}
		
		else if(index==9){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Swell_Height.get((day*8)+7))),
						 };
			
		}
		
		else if(index==10){
			
			for (int i=0;i<MainActivity.Swell_Direction.size();i++) {
				String[] temp=MainActivity.Swell_Direction.get(i).split("°");
				MainActivity.Swell_Direction.set(i, temp[0]);
				
			}
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Swell_Direction.get((day*8)+7))),
						 };
			
		}
		
		
		
		else if(index==11){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Swell_Period.get(0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Swell_Period.get(1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Swell_Period.get(2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Swell_Period.get(3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Swell_Period.get(4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Swell_Period.get(5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Swell_Period.get(6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Swell_Period.get(7))),
						 };
			
		}
		
		else if(index==12){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Pressure.get(0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Pressure.get(1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Pressure.get(2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Pressure.get(3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Pressure.get(4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Pressure.get(5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Pressure.get(6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Pressure.get(7))),
						 };
			
		}
		
		else if(index==13){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) Double.parseDouble(MainActivity.Visibility.get(0))),
					new GraphViewData(1, (double) Double.parseDouble(MainActivity.Visibility.get(1))),
					new GraphViewData(2, (double) Double.parseDouble(MainActivity.Visibility.get(2))),
					new GraphViewData(3, (double) Double.parseDouble(MainActivity.Visibility.get(3))),
					new GraphViewData(4, (double) Double.parseDouble(MainActivity.Visibility.get(4))),
					new GraphViewData(5, (double) Double.parseDouble(MainActivity.Visibility.get(5))),
					new GraphViewData(6, (double) Double.parseDouble(MainActivity.Visibility.get(6))),
					new GraphViewData(7, (double) Double.parseDouble(MainActivity.Visibility.get(7))),
						 };
			
		}
		
		else if(index==14){
			
			graphData = new GraphViewData[] {
					new GraphViewData(0, (double) MainActivity.Beaufort.get(0)),
					new GraphViewData(1, (double) MainActivity.Beaufort.get(1)),
					new GraphViewData(2, (double) MainActivity.Beaufort.get(2)),
					new GraphViewData(3, (double) MainActivity.Beaufort.get(3)),
					new GraphViewData(4, (double) MainActivity.Beaufort.get(4)),
					new GraphViewData(5, (double) MainActivity.Beaufort.get(5)),
					new GraphViewData(6, (double) MainActivity.Beaufort.get(6)),
					new GraphViewData(7, (double) MainActivity.Beaufort.get(7)),
						 };
			
		}
		
		zoomView = new ZoomView(this);
		

		main_container = (LinearLayout) findViewById(R.id.linear);
		main_container.addView(zoomView);      
		
		layout.removeAllViewsInLayout();	
		layout.addView(layout2);
		layout.addView(quantity_spinner);
		
		quantity_spinner.setSelection(index);
		graphSeries = new GraphViewSeries(graphData);
		graphView = new LineGraphView(this,
				quantities.get(index));

		// graphView = new BarGraphView(container.getContext(), "Temperature");

		graphView.addSeries(graphSeries);
		// graphView.getGraphViewStyle().setGridStyle(GridStyle.HORIZZONTAL);
		graphView.setHorizontalLabels(new String[] { "12 AM", "3 AM", "6 AM",
				"9 AM", "12 PM", "3 PM", "6 PM", "9 PM" });
		
		if(a==1)
			graphView.setHorizontalLabels(new String[] { "00 π.μ.", "3 π.μ.", "6 π.μ.",
					"9 π.μ.", "12 μ.μ.", "15 μ.μ.", "18 μ.μ.", "21 μ.μ." });
		graphView.getGraphViewStyle().setGridColor(Color.WHITE);
		graphView.getGraphViewStyle().setTextSize(15.0f);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		
		graphView.setScaleY(1);
		graphView.setScalable(true);
		graphView.setScrollable(true);
		zoomView.addView(graphView);
		layout.addView(zoomView);

		
	
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getId()==R.id.bttn_today){
			
			day = 0;
			
			today.setBackgroundColor(Color.parseColor("#7C838B"));
			today.setTextColor(Color.parseColor("#FEFEFE"));
			
			tomorrow.setBackgroundColor(Color.parseColor("#C8C9CB"));
			tomorrow.setTextColor(Color.parseColor("#000000"));
			
			tomorrow2.setBackgroundColor(Color.parseColor("#C8C9CB"));
			tomorrow2.setTextColor(Color.parseColor("#000000"));
					
		}
		
		else if(v.getId()==R.id.bttn_tomorrow){	
			
			day=1;
			
			tomorrow.setBackgroundColor(Color.parseColor("#7C838B"));
			tomorrow.setTextColor(Color.parseColor("#FEFEFE"));
			
			today.setBackgroundColor(Color.parseColor("#C8C9CB"));
			today.setTextColor(Color.parseColor("#000000"));
		
			tomorrow2.setBackgroundColor(Color.parseColor("#C8C9CB"));
			tomorrow2.setTextColor(Color.parseColor("#000000"));
			
		}
		
		else if(v.getId()==R.id.bttn_dayafter){	
			
			day=2;
			
			tomorrow2.setBackgroundColor(Color.parseColor("#7C838B"));
			tomorrow2.setTextColor(Color.parseColor("#FEFEFE"));

			tomorrow.setBackgroundColor(Color.parseColor("#C8C9CB"));
			tomorrow.setTextColor(Color.parseColor("#000000"));
			
			today.setBackgroundColor(Color.parseColor("#C8C9CB"));
			today.setTextColor(Color.parseColor("#000000"));
			
		}

		
		updategraph(indexa);
	}
	
	
	
	
}
