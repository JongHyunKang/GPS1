package com.example.user.a20170525;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.user.a20170525.MainActivity.REQ_PERMISSION;

public class Setting extends AppCompatActivity implements OnMapReadyCallback,ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    static final int REQ_PERMISSION = 1000;
    MapFragment mapFr;
    GoogleMap map;
    Geocoder gc;

    ArrayList<LatLng> m_latlan;
    GoogleApiClient googleApiClient = null;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;

    String lat, lon;
    String name, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
        initMap();

        Button btn = findViewById(R.id.save);
        final Spinner spin1 = findViewById(R.id.rad);
        final Spinner spin2 = findViewById(R.id.day);

//        Intent intent = getIntent();
//        name = intent.getExtras().getString("name");
//        number = intent.getExtras().getString("number");
//
//        Log.d("번호", number);
//        Log.d("이름", name);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setting.this, MainActivity.class);
                intent.putExtra("spinner_rad", spin1.getSelectedItem().toString());
                intent.putExtra("spinner_day", spin2.getSelectedItem().toString());
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
//                intent.putExtra("name1", name);
//                intent.putExtra("nummber1", number);
                startActivity(intent);
            }
        });

    }

    void initMap() {
        mapFr = (MapFragment) getFragmentManager().findFragmentById(R.id.map1);
        mapFr.getMapAsync(this);
    }

    void init(){
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


    public void updateMap(){

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

        map.clear();

        Point screenPt = map.getProjection().toScreenLocation(latLng);
        LatLng mlatlng = map.getProjection().fromScreenLocation(screenPt);

        MarkerOptions options = new MarkerOptions();
        options.position(mlatlng);
        options.title("ProtectArea(center)");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        map.addMarker(options);

        lat = String.valueOf(mlatlng.latitude);
        lon = String.valueOf(mlatlng.longitude);

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
        if (mLastLocation != null) {
            Log.i("Location", (String.valueOf("LastLocation" +
                    mLastLocation.getLatitude() + "::" + mLastLocation.getLongitude())));
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

    public void saveclick(View view) {

    }

}
