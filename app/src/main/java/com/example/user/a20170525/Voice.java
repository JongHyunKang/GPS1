package com.example.user.a20170525;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Voice extends AppCompatActivity {

    Button btn;
    TextView tv1, tv2;

    String name;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        btn = findViewById(R.id.btn);
        tv1 = findViewById(R.id.name);
        tv2 = findViewById(R.id.number);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,null);
            cursor.moveToFirst();
            name = cursor.getString(0);
            number = cursor.getString(1);

            tv1.setText(name);
            tv2.setText(number);

            Intent intent1 = new Intent(Voice.this, MainActivity.class);
            intent1.putExtra("name", name);
            intent1.putExtra("number", number);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getnumber(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);

        startActivityForResult(intent, 0);
    }
}
