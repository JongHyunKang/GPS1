package com.example.user.a20170525;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.widget.TextView;

public class MainService extends Service {

    private String My_GPS_Provider = LocationManager.GPS_PROVIDER;
    private String My_Network_Provider = LocationManager.NETWORK_PROVIDER;
    LocationManager lm;
    String ad;
    double getLati, getLongi;
    double long_lat;
    TextView latiView;
    String wa;
    Thread t;
    private boolean isRunning;
    private Context context;
    // private static Bundle bundle=new Bundle();

    Location location;
    @Override
    public void onCreate() {

        this.context = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return startId;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
