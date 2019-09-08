package com.mjchoi.finalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mjchoi.finalapp.data.UserDbHelper;

public class LoginActivity extends AppCompatActivity{

    private EditText emailLog;
    private EditText passwordLog;
    private Button loginButton;
    private TextView signUp;
    private Button navButton;
    private UserDbHelper userDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login();

        navButton = findViewById(R.id.nav_button);
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
            }
        });
    }

    private void login(){
        emailLog = findViewById(R.id.email_login);
        passwordLog = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.login_button);
        signUp = findViewById(R.id.sign_up);
        userDbHelper = new UserDbHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLog.getText().toString().trim();
                String password = passwordLog.getText().toString().trim();

                Boolean credentials = userDbHelper.checkCredentials(email, password);
                if(credentials == true){
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), NavigationActivity.class));
                }

                else{
                    Toast.makeText(getApplicationContext(), "Wrong Email or Password. Try Again", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
   }
}
