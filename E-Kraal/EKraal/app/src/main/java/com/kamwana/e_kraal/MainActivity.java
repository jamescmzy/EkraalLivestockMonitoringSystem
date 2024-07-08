package com.kamwana.e_kraal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new thread to perform background tasks
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Simulate a delay of 5 seconds (5000 milliseconds)
                    Thread.sleep(5000);

                    // Start the Registration activity after the delay
                    startActivity(new Intent(MainActivity.this, Registration.class));
                } catch (Exception e) {
                    // Handle any exceptions that might occur during the thread execution
                    e.printStackTrace(); // This would typically log the exception
                }
            }
        }).start(); // Start the thread
    }
}
