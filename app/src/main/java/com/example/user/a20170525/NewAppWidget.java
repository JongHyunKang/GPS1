package com.example.user.a20170525;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
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

