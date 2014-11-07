package com.gorilaalex.gpsfinal;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GpsBroadcastReceiver extends BroadcastReceiver {
    public GpsBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "GPS is enabled in your device. Please wait to calibrate.", Toast.LENGTH_LONG).show();
            Log.d("broadcast", "done");
            context.startService(new Intent(TrackingService.ACTION_START_MONITORING));
        }
        else
        {
            showGPSDisabledDialog(context);
        }
    }

    public void start(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);
        context.registerReceiver(this,filter);
    }

    public void stop(Context context) {
        context.unregisterReceiver(this);
    }

    public void showGPSDisabledDialog(final Context context) {

        final Context mContext = context;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("GPS is disabled in your device.Would you like to enable ?")
                .setCancelable(false)
                .setPositiveButton("Go to Settings",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intentGPSSettings = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                mContext.startActivity(intentGPSSettings);
                            }
                        });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        Toast.makeText(mContext, "You need to enable GPS in order to use this app.", Toast.LENGTH_LONG).show();


                    }
                });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

}
