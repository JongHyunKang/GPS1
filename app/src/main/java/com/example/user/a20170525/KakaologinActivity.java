package com.example.user.a20170525;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class KakaologinActivity extends AppCompatActivity {


    private WebView mWebView;
    private WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.d("서버",getData("server"));
        //getData("server");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakaologin);


        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://kauth.kakao.com/oauth/authorize?client_id=c03758c161b681dd97c2bab13376ce41&redirect_uri=http://"+":8080/WearisMomServer&response_type=code");

        Log.e("URL", mWebView.getUrl());
        if(mWebView.getUrl()=="https://localhost"){
            Intent intent = new Intent(
                    getApplicationContext(),Basic.class
            );
            startActivity(intent);
        }
        //parse();
    }
    public void parse(){
        mWebView.setWebViewClient(new WebViewClient(){

            public boolean shouldOverrideUrlLoading(WebView view, String url){
                if(url.startsWith("https://localhost")){

                    Intent intent = new Intent(
                            getApplicationContext(),Basic.class
                    );
                    startActivity(intent);
                }

                return true;
            }
        });
    }
    public static final String firebaseURL = "https://wearismom.firebaseio.com/";
    public String getData(String suburl) {
        final String apiUrl = firebaseURL + suburl+".json?print=pretty";

        // 로그인 과정중 얻은 authorization code 값
        HttpsURLConnection conn = null;
        OutputStreamWriter writer = null;
        BufferedReader reader = null;
        InputStreamReader isr= null;
        String json = "";

        try {

            final URL url = new URL(apiUrl);

            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            final int responseCode = conn.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + apiUrl);
            System.out.println("Response Code : " + responseCode);

            isr = new InputStreamReader(conn.getInputStream());
            reader = new BufferedReader(isr);
            final StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            System.out.println(buffer.toString());
            json = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // clear resources
            if (writer != null) {
                try {
                    writer.close();
                } catch(Exception ignore) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch(Exception ignore) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch(Exception ignore) {
                }
            }

        }
        return json;

    }

}
