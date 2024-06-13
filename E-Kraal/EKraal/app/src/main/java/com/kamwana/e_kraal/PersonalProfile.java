package com.kamwana.e_kraal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PersonalProfile extends AppCompatActivity {


    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    TextView name, email;

    String passwordStore="";
    String nameStore="";

    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    String emailStore="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_profile);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        emailStore = sharedPreferences.getString(KEY_EMAIL, "");
        nameStore = ((SharedPreferences) sharedPreferences).getString(KEY_NAME, "");

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        name.setText(nameStore);
        email.setText(emailStore);



    }
}
