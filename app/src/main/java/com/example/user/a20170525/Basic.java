package com.example.user.a20170525;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Basic extends AppCompatActivity {

    private ImageButton bt2;
    static boolean calledAlready = false;
    String imageURL;

    TelephonyManager telephonyManager = null;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url();
        //Log.d("url : ",imageURL);

        setContentView(R.layout.activity_basic);
        ImageButton bt1 = findViewById(R.id.viewmap);
        bt2 = findViewById(R.id.kakao);
        ImageButton bt3 = findViewById(R.id.chat);
        ImageButton bt4 = findViewById(R.id.setting);

        final String image_url = "http://k.kakaocdn.net/dn/IAfpN/btqiLcoFI3n/wJV2jqjiGWKyIjuEyB5dB1/profile_640x640s.jpg";

        new ImageLoadTask(image_url,bt2).execute();

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        getApplicationContext(),MainActivity.class
                );
                startActivity(intent);
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),Cacao.class
                );
                startActivity(intent);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),Voice.class
                );
                startActivity(intent);
            }
        });
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),Setting.class
                );
                startActivity(intent);
            }
        });
        String myNumber = null;
        TelephonyManager mgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        try{
            myNumber = mgr.getLine1Number();
            myNumber = myNumber.replace("+82", "0");

        }catch(Exception e){}
        Log.d("번호 : ",myNumber);
    }

    public void url(){
        if(!calledAlready){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready = true;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("Uesr");

        databaseReference.child("556276469").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()){
                    imageURL = fileSnapshot.child("profile_image").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
