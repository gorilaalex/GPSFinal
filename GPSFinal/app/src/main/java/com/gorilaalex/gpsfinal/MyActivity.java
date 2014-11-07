package com.gorilaalex.gpsfinal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MyActivity extends Activity {

    //request codes for alert and settings
    static final int PROVIDE_SETTINGS_REQUEST_CODE = 1000;
    static final int PROVIDE_ALERT_REQUEST_CODE = 1001;

    static TextView speedTextView ;
    static TextView altitudeTextView;
    private static String textForTextView = "";
    private static String textForAltitude = "";
    private String measureUnitSpeed = "km/h";

    //used to check if the gps is on or off
    GpsBroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        initializeApp();
        getCurrentFocus().setKeepScreenOn(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mReceiver.stop(this);
    }

   /* @Override
    protected void onResume() {
        this.mReceiver = new GpsBroadcastReceiver();
        mReceiver = new GpsBroadcastReceiver();
        mReceiver.start(this);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        switch (requestCode) {
            case PROVIDE_SETTINGS_REQUEST_CODE :
                handleSettings(resultCode, resultIntent);
                break;
            case PROVIDE_ALERT_REQUEST_CODE :
                handleAlert(resultCode, resultIntent);
                break;
        }
    }

    private void initializeApp(){

        speedTextView = (TextView)findViewById(R.id.speedTextView);
        altitudeTextView = (TextView)findViewById(R.id.altitudeTextView);

        mReceiver = new GpsBroadcastReceiver();
        mReceiver.start(this);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            mReceiver.showGPSDisabledDialog(this);
        }


        //hack to update the ui for
        TrackingService.context  = this;
        Intent intent = new Intent(this, TrackingService.class);
        intent.setAction(TrackingService.ACTION_START_MONITORING);
        startService(intent);

       /* findViewById(R.id.action_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivityForResult(intent,PROVIDE_SETTINGS_REQUEST_CODE);
            }
        });*/

    }

    public void UpdateUI(float speed,double altitude){
        final float fSpeed =  (speed/1000 ) * 3600;
        final double sAltitude = altitude;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textForTextView = " "+ fSpeed + " " + measureUnitSpeed;
                textForAltitude = " " +sAltitude +"m";
                speedTextView.setText(textForTextView);
                altitudeTextView.setText(textForAltitude);
            }
        });

    }

    private void handleSettings(int requestCode, Intent resultIntent) {
        if(requestCode == RESULT_OK) {
            measureUnitSpeed = resultIntent.getStringExtra(SettingsActivity.TYPE_EXTRA);


        }

    }

    private void handleAlert(int requestCode, Intent resultIntent) {

    }



}