package com.example.user.a20170525;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by user on 2017-12-14.
 */

public class GetData extends AsyncTask<Void, Void, String> {
    private String url;
    private String result;
    public GetData(String url){
        this.url = url;
    }
    public String getResult() { return result; }


    @Override
    protected String doInBackground(Void... voids) {

        try{
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setRequestMethod("GET");
            conn.getInputStream();

            BufferedReader reader = null;
            InputStreamReader isr= null;

            isr = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(isr);
            final StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
