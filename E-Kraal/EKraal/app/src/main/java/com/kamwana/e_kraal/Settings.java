package com.kamwana.e_kraal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Range;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Initialize TextView for range setting
        TextView range = findViewById(R.id.range);

        // Set OnClickListener for range TextView to start Rang activity
        range.setOnClickListener(v -> {
            startActivity(new Intent(Settings.this, Rang.class));
        });
    }
}
