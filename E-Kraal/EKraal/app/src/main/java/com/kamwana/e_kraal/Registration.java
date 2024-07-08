package com.kamwana.e_kraal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Registration extends AppCompatActivity {

    // Constants for saved instance state keys
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    // EditText fields for email and password
    EditText email2, password3;

    // TextView for sign-in link
    TextView signIn;

    // EditText fields for sign-up form
    EditText name, password1, password2, email;

    // TextView for sign-up button and View for sign-up layout
    TextView signupButton;
    View signUp;

    // Variables to store retrieved data
    String emailStore = "";
    String passwordStore = "";
    String nameStore = "";

    // Bundle to store saved instance state
    private Bundle savedStateBundle;

    // SharedPreferences instance
    SharedPreferences sharedPreferences;

    // Name for the SharedPreferences file
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        // Initialize SharedPreferences instance
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Retrieve stored email and password from SharedPreferences
        emailStore = sharedPreferences.getString(KEY_EMAIL, "");
        passwordStore = sharedPreferences.getString(KEY_PASSWORD, "");

        // Initialize UI elements
        signIn = findViewById(R.id.sign_in);
        email2 = findViewById(R.id.email);
        password3 = findViewById(R.id.password);

        // Call SignUp method to initialize sign-up functionality
        SignUp();
        // Call Checking method to handle sign-up visibility
        Checking();

        // Set OnClickListener for sign-in TextView
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if entered email and password match stored credentials
                if (password3.getText().toString().equals(passwordStore) && email2.getText().toString().equals(emailStore)) {
                    // Start Home activity if credentials match
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    // Display error message if credentials do not match
                    Toast.makeText(Registration.this, "Email or password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to check if sign-up is required and adjust visibility accordingly
    private void Checking() {
        if (passwordStore.equals("") || passwordStore == null) {
            signUp.setVisibility(View.VISIBLE);
        } else {
            signUp.setVisibility(View.GONE);
        }
    }

    // Method to handle sign-up functionality
    public void SignUp() {
        // Initialize signUp View and EditText fields for sign-up form
        signUp = findViewById(R.id.sign_up);
        name = signUp.findViewById(R.id.name);
        password1 = signUp.findViewById(R.id.password1);
        password2 = signUp.findViewById(R.id.password2);
        email = signUp.findViewById(R.id.email);
        signupButton = signUp.findViewById(R.id.sign_up1);

        // Set OnClickListener for sign-up button
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate input fields for sign-up
                if (!name.getText().toString().equals("") && !email.getText().toString().equals("") && !password1.getText().toString().equals("")) {
                    // Check if passwords match
                    if (password1.getText().toString().equals(password2.getText().toString())) {
                        // Check if password length is sufficient
                        if (password1.getText().toString().length() < 4) {
                            Toast.makeText(Registration.this, "Password too short", Toast.LENGTH_SHORT).show();
                        } else {
                            // Validate email format
                            if (email.getText().toString().split("@").length == 2) {
                                // Save data and start Home activity
                                saveData();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            } else {
                                Toast.makeText(Registration.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(Registration.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Registration.this, "Enter all required data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to save sign-up data to SharedPreferences
    private void saveData() {
        String email2 = email.getText().toString();
        String password2 = password1.getText().toString();
        String names = name.getText().toString();

        // Save data to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email2);
        editor.putString(KEY_PASSWORD, password2);
        editor.putString(KEY_NAME, names);
        editor.apply();
        // Show toast message indicating data is saved
        Toast.makeText(getApplicationContext(), "Data is saved", Toast.LENGTH_SHORT).show();
    }

    // Method to restore instance state
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore data from saved instance state if available
        if (savedInstanceState != null) {
            nameStore = savedInstanceState.getString(KEY_NAME);
            emailStore = savedInstanceState.getString(KEY_EMAIL);
            passwordStore = savedInstanceState.getString(KEY_PASSWORD);

            // Set name and email EditTexts with restored data
            name.setText(nameStore);
            email.setText(emailStore);

            // Adjust sign-up visibility based on restored password
            if (!passwordStore.equals("")) {
                signUp.setVisibility(View.GONE);
            }
        }
    }

    // Method to handle sign-in visibility
    public void SignIn(View view) {
        signUp.setVisibility(View.GONE);
    }

    // Method to handle sign-up visibility
    public void SignUp(View view) {
        signUp.setVisibility(View.VISIBLE);
    }
}
