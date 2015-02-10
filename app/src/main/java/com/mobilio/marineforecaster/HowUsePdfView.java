package com.mobilio.marineforecaster;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class HowUsePdfView extends Activity {
	
	int a=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 String listPrefs = prefs.getString("listpref", "English");
		 
		 if(listPrefs.equalsIgnoreCase("Greek"))
			 a=1;
		
		AssetManager assetManager = getAssets();

	    InputStream in = null;
	    OutputStream out = null;
	    File file = new File(getFilesDir(), "howuseenglish.pdf");
	    if(a==1)
	    	file = new File(getFilesDir(), "howusegreek.pdf");
	    try
	    {
	        in = assetManager.open("howuseenglish.pdf");
	        if(a==1)
	        	in = assetManager.open("howusegreek.pdf");
	        out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

	        copyFile(in, out);
	        in.close();
	        in = null;
	        out.flush();
	        out.close();
	        out = null;
	    } catch (Exception e)
	    {
	        Log.e("tag", e.getMessage());
	    }
	    
	    
	    
	    try {
	            final Intent intent = new Intent(HowUsePdfView.this, Second.class);
	            if(a==0)
	            intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, getFilesDir()+"/howuseenglish.pdf");
	            else if(a==1)
	            	intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME, getFilesDir()+"/howusegreek.pdf");
	    	    startActivity(intent);
	    	    /* 
	    	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    	    intent.setDataAndType(
	    	            Uri.parse("file://" + getFilesDir() + "/ABC.pdf"),
	    	            "application/pdf");

	    	    startActivity(intent);*/
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException
	{
	    byte[] buffer = new byte[1024];
	    int read;
	    while ((read = in.read(buffer)) != -1)
	    {
	        out.write(buffer, 0, read);
	    }
	}
	

}
