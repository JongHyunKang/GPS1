package com.example.user.a20170525;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Basic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        Button bt1 = (Button)findViewById(R.id.viewmap);
        Button bt2 = (Button)findViewById(R.id.kakao);
        Button bt3 = (Button)findViewById(R.id.chat);
        Button bt4 = (Button)findViewById(R.id.setting);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        getApplicationContext(),MainActivity.class
                );
                startActivity(intent);
            }
        });
    }
}
