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

    //this is the code for saved instant state
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    EditText email2, password3;

    TextView signIn;
    EditText name, password1, password2, email;
    TextView signupButton;
    View signUp;

    String emailStore="";
    String passwordStore="";
    String nameStore="";

    private Bundle savedStateBundle;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        emailStore = sharedPreferences.getString(KEY_EMAIL, "");
        passwordStore = ((SharedPreferences) sharedPreferences).getString(KEY_PASSWORD, "");


        signIn=findViewById(R.id.sign_in);
        email2=findViewById(R.id.email);
        password3=findViewById(R.id.password);


        SignUp();
        Checking();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password3.getText().toString().equals(passwordStore) && email2.getText().toString().equals(emailStore)){

                    startActivity(new Intent(getApplicationContext(), Home.class));
                }else {

                    Toast.makeText(Registration.this, "Email or password is wrong", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void Checking() {


        if (passwordStore.equals("") || passwordStore==null){
            signUp.setVisibility(View.VISIBLE);
        }else{

            signUp.setVisibility(View.GONE);
        }
    }

    //these are all the codes from the signUp
    public void SignUp(){

        signUp=findViewById(R.id.sign_up);
        name=signUp.findViewById(R.id.name);
        password1=signUp.findViewById(R.id.password1);
        password2=signUp.findViewById(R.id.password2);
        email=signUp.findViewById(R.id.email);
        signupButton=signUp.findViewById(R.id.sign_up1);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!name.getText().toString().equals("") && !email.getText().toString().equals("") && !password1.getText().toString().equals("")){
                    if(password1.getText().toString().equals(password2.getText().toString())){

                        if(password1.getText().toString().length()<4){
                            Toast.makeText(Registration.this, "password too short", Toast.LENGTH_SHORT).show();
                        }else {

                            if(email.getText().toString().split("@").length==2){

                                //here is the code for entering and storing the password
                                saveData();
                                startActivity(new Intent(getApplicationContext(), Home.class));
                            }else {
                                Toast.makeText(Registration.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                            }

                        }

                    }else{

                        Toast.makeText(Registration.this, "password miss match", Toast.LENGTH_SHORT).show();

                    }
                }else {

                    Toast.makeText(Registration.this, "Enter all data required", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void saveData(){
        String email2 = email.getText().toString();
        String password2 = password1.getText().toString();
        String names=name.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email2);
        editor.putString(KEY_PASSWORD, password2);
        editor.putString(KEY_NAME, names);
        editor.apply();
        Toast.makeText(getApplicationContext(), "data is saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            nameStore=savedInstanceState.getString(KEY_NAME);
            emailStore=savedInstanceState.getString(KEY_EMAIL);
            passwordStore=savedInstanceState.getString(KEY_PASSWORD);

            name.setText(nameStore+"hello family and friends");
            email.setText(emailStore);
            if(!passwordStore.equals("")){

                signUp.setVisibility(View.GONE);

            }
        }
    }


    public void SignIn(View view){

        signUp.setVisibility(View.GONE);
    }

    public void SignUp(View view) {

        signUp.setVisibility(View.VISIBLE);
    }
}
