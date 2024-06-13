package com.kamwana.e_kraal;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    View morelayout;
    ImageView more;
    RelativeLayout cancel;

    LinearLayout profile, settings, help, about, logOut;
    ImageView back;
    TextView tempValue, humidityValue, airValue;

    boolean b=false;
    boolean f=false;
    Switch humidifier;


    //this is the code for initialization of firebase

    FirebaseDatabase database;
    DatabaseReference hum, AllBulbs, Bulb1, Bulb2, Bulb3, Bulb4, Allfans, Fan1, Fan2;
    //this is for the states of the bulbs and fans each set to false
    boolean bulb1State=false;
    boolean bulb2State=false;
    boolean bulb3State=false;
    boolean bulb4State=false;
    boolean fan1State=false;
    boolean fan2State=false;
    boolean humidifierState=false;
    boolean allBulbsState=false;
    boolean allFansState=false;

    RelativeLayout temperatureControl;
    View temperatureLayout;
    TextView temperature;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        //initialize the firebase code
        database = FirebaseDatabase.getInstance();
        DatabaseReference bf= database.getReference("bulb");
        DatabaseReference bb= database.getReference("fan");
        hum=database.getReference("hum");
        AllBulbs=database.getReference("all_bulbs");
        Bulb1=database.getReference("bulb1");
        Bulb2=database.getReference("bulb2");
        Bulb3=database.getReference("bulb3");
        Bulb4=database.getReference("bulb4");
        Allfans=database.getReference("all_fans");
        Fan1=database.getReference("fan1");
        Fan2=database.getReference("fan2");



        //the code for a side layout
        morelayout=findViewById(R.id.more_layout);
        more=findViewById(R.id.more);
        cancel=morelayout.findViewById(R.id.cancel);

        //the views for setting the values on textView
        tempValue=findViewById(R.id.temp_value);
        humidityValue=findViewById(R.id.humidity_value);
        airValue=findViewById(R.id.air_value);
         temperatureControl=findViewById(R.id.temperature_control);

        profile=morelayout.findViewById(R.id.profile);
        settings=morelayout.findViewById(R.id.settings);
        help=morelayout.findViewById(R.id.help);
        about=morelayout.findViewById(R.id.about);
        logOut=morelayout.findViewById(R.id.log_out);

        back=morelayout.findViewById(R.id.back);
        temperatureLayout=findViewById(R.id.temperature_layouts);
        temperature=temperatureLayout.findViewById(R.id.temp_value);

        SendCommands();



        temperatureControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                temperatureLayout.setVisibility(View.VISIBLE);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morelayout.setVisibility(View.GONE);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PersonalProfile.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morelayout.setVisibility(View.GONE);
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morelayout.setVisibility(View.VISIBLE);
            }
        });


        Firebase();
    }


    //these are the code for sending the commands to the firebase so that they can be sent to the esp32

    private void SendCommands() {

        humidifier=findViewById(R.id.humidifier);
        Switch allBulb, bulb1, bulb2, bulb3, bulb4, allFans, fan1, fan2;

        allBulb=temperatureLayout.findViewById(R.id.all_bulbs);
        bulb1=temperatureLayout.findViewById(R.id.bulb1);
        bulb2=temperatureLayout.findViewById(R.id.bulb2);
        bulb3=temperatureLayout.findViewById(R.id.bulb3);
        bulb4=temperatureLayout.findViewById(R.id.bulb4);

        allFans=temperatureLayout.findViewById(R.id.all_fans);
        fan1=temperatureLayout.findViewById(R.id.fan1);
        fan2=temperatureLayout.findViewById(R.id.fan2);


        allFans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks the state of all fans
                if(allFansState){
                    Allfans.setValue("off");
                    allFansState=false;
                }else {
                    Allfans.setValue("on");
                }
            }
        });


        fan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //


                if(fan1State){
                    Fan1.setValue("off");
                    fan1State=false;
                }else {
                    Fan1.setValue("on");
                    fan1State = true;
                }

            }
        });

        fan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks thes state of fan2
                if(fan2State){
                    Fan2.setValue("off");
                    fan2State=false;
                }else {
                    Fan2.setValue("on");
                    fan2State = true;
                }

            }
        });
        allBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks the state of all bulbs

                if(allBulbsState){
                    AllBulbs.setValue("off");
                    allBulbsState=false;
                }else {
                    AllBulbs.setValue("on");
                    allBulbsState = true;
                }

            }
        });

        bulb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks the state of bulb1
                if(bulb1State){
                    Bulb1.setValue("off");
                    bulb1State=false;
                }else {
                    Bulb1.setValue("on");
                    bulb1State = true;
                }
            }
        });

        bulb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks the state of bulb2
                if(bulb2State){
                    Bulb2.setValue("off");
                    bulb2State=false;
                }else {
                    Bulb2.setValue("on");
                    bulb2State = true;
                }
            }
        });

        bulb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks the state of bulb3
                if(bulb3State){
                    Bulb3.setValue("off");
                    bulb3State=false;
                }else {
                    Bulb3.setValue("on");
                    bulb3State = true;
                }
            }
        });

        bulb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //this checks the state of bulb4
                if(bulb4State){
                    Bulb4.setValue("off");
                    bulb4State=false;
                }else {
                    Bulb4.setValue("on");
                    bulb4State = true;
                }
            }
        });



        humidifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b){
                    hum.setValue("off");
                    b=false;
                }else {

                    hum.setValue("on");
                    b=true;
                }

            }
        });

    }

    public void Firebase(){
        //this is the code for firebase
        DatabaseReference myRef = database.getReference("temperature");
        DatabaseReference myRef2= database.getReference("humidity");
        DatabaseReference myRef3= database.getReference("air");




        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                tempValue.setText(value);
                temperature.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                humidityValue.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);

                airValue.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public void onBackPressed() {

        morelayout.setVisibility(View.GONE);
        temperatureLayout.setVisibility(View.GONE);
    }


}
