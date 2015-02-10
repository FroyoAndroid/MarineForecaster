package com.mobilio.marineforecaster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainMenu extends Activity {
    int a = 0;
    Button forcast, distance, howuse, gasstations;
    private InterstitialAd mInterstitialAd;
    public static int adcount = 0;
    AdRequest.Builder adRequestBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


        setContentView(R.layout.main_menu);

        String TEST_DEVICE_ID = "3087ae7ac6faeca3";
        AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        adView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3533227877792822/2689041953");


        adRequestBuilder = new AdRequest.Builder();

        adRequestBuilder.addTestDevice(AdRequest.DEVICE_ID_EMULATOR);
        mInterstitialAd.setAdListener(new AdListener() {


            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                startActivity(new Intent(MainMenu.this, DistanceCalculate.class));
            }
        });
        mInterstitialAd.loadAd(adRequestBuilder.build());

        forcast = (Button) findViewById(R.id.button1);
        distance = (Button) findViewById(R.id.button2);
        howuse = (Button) findViewById(R.id.Button01);
        gasstations = (Button) findViewById(R.id.Button02);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String listPrefs = prefs.getString("listpref", "English");

        if (listPrefs.equalsIgnoreCase("Greek"))
            a = 1;


        if (a == 1) {
            forcast.setText("Πρόγνωση Καιρού στην Θάλασσα");
            distance.setText("Υπολογισμός Απόστασης");
            howuse.setText("Πως να χρησιμοποιήσετε την εφαρμογή");
            gasstations.setText("Αναζήτηση Βενζινάδικα");
        }

    }


    @Override
    protected void onResume() {
        mInterstitialAd.loadAd(adRequestBuilder.build());

        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String listPrefs = prefs.getString("listpref", "English");
        a = 0;
        if (listPrefs.equalsIgnoreCase("Greek"))
            a = 1;


        if (a == 1) {
            forcast.setText("Πρόγνωση Καιρού στην Θάλασσα");
            distance.setText("Υπολογισμός Απόστασης  & Καυσίμων");
            howuse.setText("Πως να χρησιμοποιήσετε την εφαρμογή");
            gasstations.setText("Αναζήτηση Βενζινάδικα");
        } else if (a == 0) {
            forcast.setText("Marine Weather Forecasting");
            distance.setText("Calculate Distance & Fuel");
            howuse.setText("How to use");
            gasstations.setText("Search Gas Stations");
        }
    }

    public void forcast_click(View v) {
        startActivity(new Intent(MainMenu.this, MainActivity.class));
    }

    public void gasstations_click(View v) {
        startActivity(new Intent(MainMenu.this, CountrySelectActivity.class));
    }

    public void howuse_click(View v) {
        startActivity(new Intent(MainMenu.this, HowuseActivity.class));
    }

    public void distance_click(View v) {

        if (mInterstitialAd.isLoaded() && adcount % 3 == 0) {
            mInterstitialAd.show();
        } else {

            if (networkStatus(this))
                startActivity(new Intent(MainMenu.this, DistanceCalculate.class));
            else {
                String message = "Make Sure Your Internet /GPS is connected";
                if (a == 1)
                    message = "Βεβαιωθείτε ότι σας στο Internet / GPS είναι συνδεδεμένο";
                Toast.makeText(MainMenu.this, message, Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        startActivity(new Intent(MainMenu.this, Preferences.class));
        return super.onOptionsItemSelected(item);
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
