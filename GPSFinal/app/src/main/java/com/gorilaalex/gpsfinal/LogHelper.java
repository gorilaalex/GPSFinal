package com.gorilaalex.gpsfinal;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Alex on 08.07.2014.
 */
public class LogHelper {
    static final String _timeStampFormat = "yyyy-MM-dd :'T' HH:mm:ss";
    static final String _timeZone = "UTC";

    public static long threadId()
    {
        return Thread.currentThread().getId();
    }

    public static String FormatLocationInfo(String provider, double lat, double longitude,float accuracy,long time)
    {
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat(_timeStampFormat);
        timeStampFormatter.setTimeZone(TimeZone.getTimeZone(_timeZone));

        String timeStamp = timeStampFormatter.format(time);

        String logMessage = String.format("%s | lat/long-%f/%f | accuracy %f | Time %s",
                provider, lat, longitude, accuracy, timeStamp);

        return logMessage;

    }

    public static String FormatLocationInfo(Location location)
    {
        String provider = location.getProvider();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        float accuracy = location.getAccuracy();
        long time = location.getTime();

        return LogHelper.FormatLocationInfo(provider,latitude,longitude,accuracy,time);
    }

    public static String FormatLocationProvider(Context context,LocationProvider provider){
        String name = provider.getName();
        int horizontalAccuracy = provider.getAccuracy();
        int powerRequirements = provider.getPowerRequirement();
        boolean hasMonetaryCost = provider.hasMonetaryCost();
        boolean requiresCell = provider.requiresCell();
        boolean requiresNetwork = provider.requiresNetwork();
        boolean requiresSatellite = provider.requiresSatellite();
        boolean supportsAltitude = provider.supportsAltitude();
        boolean supportsBearing = provider.supportsBearing();
        boolean supportSpeed = provider.supportsSpeed();

        String enabledMessage = "UNKNOWN";

        if(context !=null) {
            LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            enabledMessage = yOrN(lm.isProviderEnabled(name));
        }

        String horizontalAccuracyDisplay = translateAccuracyFineCourse(horizontalAccuracy);
        String powerRequirementsDisplay = translatePower(powerRequirements);

        String logMessage = String.format("%s | enabled: %s | horizontal accuracy: %s | power: %s | cost: %s | uses cell:%s" +
                        "| uses network:%s | uses satellite: %s | has altitude: %s | has bearing: %s | has speed: %s", name, enabledMessage,
                horizontalAccuracyDisplay, powerRequirementsDisplay, yOrN(hasMonetaryCost), yOrN(requiresCell), yOrN(requiresNetwork),
                yOrN(requiresSatellite), yOrN(supportsAltitude), yOrN(supportsBearing), yOrN(supportSpeed)
        );

        return logMessage;
    }

    private static String yOrN(boolean yesOrNo) {
        if(yesOrNo == true) return "yes";
        else if (yesOrNo == false) return "no";
                else return null;
    }

    private static String translateAccuracyFineCourse(int horizontalAccuracy) {
        return null;
    }

    private static String translatePower(int powerRequirements) {
        return null;
    }


    public static void logThreadId(String messageText) {


        long threadId = Thread.currentThread().getId();
        long processId = android.os.Process.myPid();
        String msg = messageText + " - Thread id : " + threadId + "->  Process Id" + processId;
        Log.d("apploghelper", msg);
    }


}
