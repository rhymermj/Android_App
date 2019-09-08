package com.mjchoi.finalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mjchoi.finalapp.data.UserDbHelper;

public class RegistrationActivity extends AppCompatActivity {
    private EditText emailReg;
    private EditText passwordReg;
    private EditText confirmPwd;
    private Button regButton;
    private UserDbHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        db = new UserDbHelper(this);
        emailReg = findViewById(R.id.email_reg);
        passwordReg = findViewById(R.id.password_reg);
        confirmPwd = findViewById(R.id.password_confirm);
        regButton = findViewById(R.id.reg_button);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailReg.getText().toString();
                String password = passwordReg.getText().toString();
                String cpassword = confirmPwd.getText().toString();

                if(email.equals("") || password.equals("") || cpassword.equals("")){
                    Toast.makeText(getApplicationContext(), "Empty Fields Not Allowed", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.equals(cpassword)) {

                        Boolean echeck = db.checkEmail(email);
                        if (echeck == true) {
                            Boolean dcheck = db.addUser(email, password);
                            if (dcheck == true) {
                                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this, NavigationActivity.class));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
