package com.example.user.a20170525;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.RemoteViews;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2017-12-14.
 */

public class ILT extends AsyncTask<Void, Void, Bitmap> {
    private String url;
    private RemoteViews remoteViews;

    public ILT(String url, RemoteViews remoteViews) {
        this.url = url;
        this.remoteViews = remoteViews;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {

        super.onPostExecute(bitmap);
        //imageButton.setImageBitmap(bitmap);
        remoteViews.setImageViewBitmap(R.id.btn2,bitmap);
    }
}
