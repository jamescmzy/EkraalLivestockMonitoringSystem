package com.kamwana.e_kraal;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Rang extends AppCompatActivity {

    // Firebase Database references
    FirebaseDatabase database;
    DatabaseReference maxRange, minRange;

    // UI elements
    EditText max, min;
    TextView enter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rang);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();
        maxRange = database.getReference("maxRange");
        minRange = database.getReference("minRange");

        // Initialize UI elements
        max = findViewById(R.id.max);
        min = findViewById(R.id.min);
        enter = findViewById(R.id.enter);

        // Set OnClickListener for enter TextView to update max and min range in Firebase
        enter.setOnClickListener(v -> {
            // Update max and min range values in Firebase with EditText values
            maxRange.setValue(max.getText().toString());
            minRange.setValue(min.getText().toString());

            // Show a toast message indicating data upload
            Toast.makeText(this, "Data is uploaded", Toast.LENGTH_SHORT).show();
        });

        // Call method to retrieve max and min range values from Firebase
        getRange();
    }

    // Method to retrieve max and min range from Firebase
    public void getRange() {
        // Re-initialize Firebase Database references (in case they were not initialized in onCreate)
        maxRange = database.getReference("maxRange");
        minRange = database.getReference("minRange");

        // Add ValueEventListener for maxRange to update max EditText
        maxRange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the value from Firebase
                String value = snapshot.getValue(String.class);
                // Set max EditText to display the retrieved value
                max.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database read error (if any)
                // Typically log the error or show an error message
            }
        });

        // Add ValueEventListener for minRange to update min EditText
        minRange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Get the value from Firebase
                String value = snapshot.getValue(String.class);
                // Set min EditText to display the retrieved value
                min.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database read error (if any)
                // Typically log the error or show an error message
            }
        });
    }
}
