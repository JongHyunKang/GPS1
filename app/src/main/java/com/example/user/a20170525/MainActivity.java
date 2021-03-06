package com.example.user.a20170525;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    static final int REQ_PERMISSION = 1000;
    MapFragment mapFr;
    GoogleMap map;
    Geocoder gc;

    ArrayList<LatLng> m_latlan;
    GoogleApiClient googleApiClient = null;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;

    String latitude;
    String longitude;

    String radstr, datstr;

    Double Lat, Lon;
    Double Lat2, Lon2;
    String distance;

    String name1, number1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initMap();

    }

    void initMap() {
        mapFr = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFr.getMapAsync(this);
    }

    void init() {

        m_latlan = new ArrayList<>();

        gc = new Geocoder(this, Locale.KOREAN);

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(5000)
                .setSmallestDisplacement(30);
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(this);
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQ_PERMISSION
            );
        }

        UiSettings uiSettings = map.getUiSettings();

        uiSettings.setZoomControlsEnabled(true);

        updateMap();

    }


    public void updateMap() {

    }

    public boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (checkPermission()) {
                    map.setMyLocationEnabled(true);
                } else {
                    //Permission was denied. Display an error message
                }
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Intent intent = getIntent();
        radstr = intent.getExtras().getString("spinner_rad");
        datstr = intent.getExtras().getString("spinner_day");
        latitude = intent.getExtras().getString("lat");
        longitude = intent.getExtras().getString("lon");

        name1 = Voice.name1;
        number1 = Voice.number1;

        Log.d("Lat", latitude);
        Log.d("Lon", longitude);
        Log.d("num", number1);
        Log.d("name", name1);

        Lat = Double.parseDouble(latitude);
        Lon = Double.parseDouble(longitude);
        Lat2 = mLastLocation.getLatitude();
        Lon2 = mLastLocation.getLongitude();

        Double Radstr = Double.parseDouble(radstr);
        Double Datstr = Double.parseDouble(datstr);

        Location locationA = new Location("pointA");
        locationA.setLatitude(Lat);
        locationA.setLongitude(Lon);

        Location locationB = new Location("pointB");
        locationB.setLatitude(Lat2);
        locationB.setLongitude(Lon2);

        distance = Double.toString(locationA.distanceTo(locationB));

        Float Distance = locationA.distanceTo(locationB);

        Log.i("거리: ", String.valueOf(distance));
        if(Distance>Radstr){
            Messenger messenger = new Messenger(getApplicationContext());
            messenger.sendMessageTo(number1,"범위이탈");
        }

        map.addCircle(new CircleOptions().center(new LatLng(Lat, Lon)).radius(Radstr).strokeColor(Color.rgb(0, 50, 100)).fillColor(Color.argb(20, 50, 0, 255)));
        if (mLastLocation != null) {
            Log.i("Location", (String.valueOf("LastLocation" +
                    Lat2 + "::" + Lon2)));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();

    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {

    }
}

