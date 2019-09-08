package com.mjchoi.finalapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mjchoi.finalapp.data.DatabaseHandler;
import com.mjchoi.finalapp.model.Habit;


public class HabitActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText habitItem;
    private EditText habitFrequency;
    private Button saveButton;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);

        databaseHandler = new DatabaseHandler(this, "habit_details");

        // Suppressing this activity will allow the user to go straight to the list
        byPassActivity();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Habits");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }

    private void createDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.add_habit_dialog, null);   // CHANGED JUNE 13TH

        habitItem = view.findViewById(R.id.habit_item);
        habitFrequency = view.findViewById(R.id.frequency_item);
        saveButton = view.findViewById(R.id.save_habit_button);


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!habitItem.getText().toString().isEmpty()
                        && !habitFrequency.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty Fields not Allowed", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }
        });
    }

    // Save item to database
    private void saveItem(View view) {
        Habit item = new Habit();

        String newName = habitItem.getText().toString().trim();
        int newFrequency = Integer.parseInt(habitFrequency.getText().toString().trim());

        if(newFrequency <= 7 && newFrequency != 0){
            item.setHabitName(newName);
            item.setHabitFrequency(newFrequency);

            // Save to database
            databaseHandler.addHabit(item);
            Snackbar.make(view, "Item Saved",Snackbar.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //code to be run
                    dialog.dismiss();
                    //Move to next screen
                    startActivity(new Intent(HabitActivity.this, HabitListActivity.class));
                    //   finish();

                }
            }, 1200);// 1sec

        }
        else{
            Toast.makeText(getApplicationContext(), "Invalid value for weekly target", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    // Handles action when the toolbar is pressed
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Direct user to the menu in navigation
        if (id == R.id.action_menu) {
            startActivity(new Intent(HabitActivity.this, NavigationActivity.class));
        }

        if (id == R.id.logout_menu) {
            startActivity(new Intent(HabitActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    private void byPassActivity() {
        //Checks if database is empty; if not, then we just
        //go to ListActivity and show all added items

        if (databaseHandler.getHabitsCount() > 0) {
            startActivity(new Intent(HabitActivity.this, HabitListActivity.class));
            finish();
        }
    }

}
