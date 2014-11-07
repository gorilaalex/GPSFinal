package com.gorilaalex.gpsfinal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class TrackingService extends Service implements Handler.Callback{

    public final static String ACTION_START_MONITORING = "com.gorilaalex.gpsfinal.START_MONITORING";
    public final static String ACTION_STOP_MONITORING = "com.gorilaalex.gpsfinal.STOP_MONITORING";
    private final static String HANDLER_THREAD_NAME = "MyLocationThread";


    LocationListener mLocationListener;
    Looper mLooper;
    Handler mHandler;
    Location location;
    static Context context ;

    public TrackingService() {

    }

   /* public TrackingService(Context context) {
        this.context = context;
    } */

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        HandlerThread mHandlerThread = new HandlerThread(HANDLER_THREAD_NAME);
        mHandlerThread.start();

        mLooper = mHandlerThread.getLooper();
        mHandler = new Handler(mLooper,this);
        Log.d("tracking service", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doStopTracking();
        if(mLooper!=null) {
            mLooper.quit();
            mLooper = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.sendMessage(mHandler.obtainMessage(0, intent));
        Log.d("tracking service", "onStartCommand");
        return START_STICKY;
    }


    @Override
    public boolean handleMessage(Message message) {
        Intent intent = (Intent) message.obj;
        if(intent!=null){
        String action = intent.getAction();
        Log.d("tracking service", "handle message");

        if(action!=null){
        if(action.equals(ACTION_START_MONITORING)){
            doStartTracking();
        }
        else if(action.equals(ACTION_STOP_MONITORING)){
            doStopTracking();
            stopSelf();
        }
        }
        }
        return true;
    }

    private void doStartTracking()
    {
        doStopTracking();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new MyLocationListener(context);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, mLocationListener,mLooper);
        if(lm!=null) {
            location = lm
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location!=null) {

                ((MyActivity)context).UpdateUI(location.getSpeed(), location.getAltitude());
                Log.d("tracking service", "doStartTracking");
            }
        }
    }

    private void doStopTracking(){

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(mLocationListener !=null) {
            lm.removeUpdates(mLocationListener);
            mLocationListener = null;
            Log.d("tracking service", "doStop");
        }
    }
}
