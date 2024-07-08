package com.kamwana.e_kraal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PersonalProfile extends AppCompatActivity {

    // Constants for SharedPreferences keys
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";

    // TextViews to display name and email
    TextView name, email;

    // String variables to store retrieved data
    String nameStore = "";
    String emailStore = "";

    // SharedPreferences instance
    SharedPreferences sharedPreferences;

    // Name for the SharedPreferences file
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_profile);

        // Initialize SharedPreferences instance
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Retrieve stored name and email from SharedPreferences
        emailStore = sharedPreferences.getString(KEY_EMAIL, "");
        nameStore = sharedPreferences.getString(KEY_NAME, "");

        // Initialize TextViews
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);

        // Set TextViews to display stored name and email
        name.setText(nameStore);
        email.setText(emailStore);
    }
}
