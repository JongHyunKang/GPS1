package com.example.user.a20170525;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    String image_url = "http://k.kakaocdn.net/dn/IAfpN/btqiLcoFI3n/wJV2jqjiGWKyIjuEyB5dB1/profile_640x640s.jpg";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i<appWidgetIds.length; i++) {

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
            Intent mapintent = new Intent(context, MainActivity.class);

            PendingIntent mapPIntent = PendingIntent.getActivity(context, 0, mapintent, 0);

            views.setOnClickPendingIntent(R.id.btn1, mapPIntent);

            Intent cacaointent = new Intent(context, Cacao.class);
            PendingIntent cacaoPIntent = PendingIntent.getActivity(context, 1, cacaointent, 0);


            views.setOnClickPendingIntent(R.id.btn2, cacaoPIntent);

            Intent voiceintent = new Intent(context, Voice.class);
            PendingIntent voicePIntent = PendingIntent.getActivity(context, 2, voiceintent, 0);

            views.setOnClickPendingIntent(R.id.btn3, voicePIntent);

            Intent settingintent = new Intent(context, Setting.class);
            PendingIntent settingPIntent = PendingIntent.getActivity(context, 3, settingintent, 0);

            views.setOnClickPendingIntent(R.id.btn4, settingPIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], views);



        }
        RemoteViews updateViews = new RemoteViews(context.getPackageName(),R.id.btn2);
        new ILT(image_url,updateViews).execute();
        //updateViews.setBitmap(image_url);
        //updateViews.setImageViewBitmap(R.id.btn2,);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

