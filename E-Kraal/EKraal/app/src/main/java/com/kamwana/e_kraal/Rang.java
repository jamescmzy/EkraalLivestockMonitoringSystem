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

public class Rang extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference maxRange, minRange;


    EditText max, min;
    TextView enter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rang);

        database = FirebaseDatabase.getInstance();
        maxRange=database.getReference("maxRange");
        minRange=database.getReference("minRange");

        max=findViewById(R.id.max);
        min=findViewById(R.id.min);
        enter=findViewById(R.id.enter);

        enter.setOnClickListener(v -> {

            maxRange.setValue(max.getText().toString());
            minRange.setValue(min.getText().toString());
            Toast.makeText(this, "data is uploaded", Toast.LENGTH_SHORT).show();
        });

    }

    //here is the method for getting max range and min range from firebase
    public void getRange() {
        maxRange=database.getReference("maxRange");
        minRange=database.getReference("minRange");

        maxRange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                max.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        minRange.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String value = snapshot.getValue(String.class);
                min.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
