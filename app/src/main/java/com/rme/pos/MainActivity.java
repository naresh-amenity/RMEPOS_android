package com.rme.pos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.rme.utils.MySession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void onStart(){
        super.onStart();
        if (!MySession.getPin(getApplicationContext()).isEmpty()&&MySession.get(getApplicationContext(),MySession.Share_AppLock).equals("1")) {
            Intent intent = new Intent(MainActivity.this, Lock_Screen.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
        overridePendingTransition(0, 0);
    }
}