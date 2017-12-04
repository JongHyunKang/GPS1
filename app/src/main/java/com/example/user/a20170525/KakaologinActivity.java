package com.example.user.a20170525;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class KakaologinActivity extends AppCompatActivity {


    private WebView mWebView;
    private WebSettings mWebSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kakaologin);


        mWebView = findViewById(R.id.webview);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://kauth.kakao.com/oauth/authorize?client_id=c03758c161b681dd97c2bab13376ce41&redirect_uri=http://localhost:8080/WearisMomServer&response_type=code");

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

}
