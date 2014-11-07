package com.gorilaalex.gpsfinal;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Alex on 15.07.2014.
 */
public class MyLocationListener implements LocationListener {

    Context context;

    public MyLocationListener(Context context) {
        this.context = context;

    }

    @Override
    public void onLocationChanged(Location location) {
        /*String provider = location.getProvider();
        float accuracy = location.getAccuracy();
        float speed = location.getSpeed();
        double altitude = location.getAltitude(); */
        LogHelper.FormatLocationInfo(location);

        ((MyActivity)context).UpdateUI(location.getSpeed(), location.getAltitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

}
