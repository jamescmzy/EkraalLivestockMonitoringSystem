package com.kamwana.e_kraal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {


                    Thread.sleep(5000);

                    startActivity(new Intent(MainActivity.this, Registration.class));
                }catch (Exception e){

                }
            }
        }).start();
    }
}