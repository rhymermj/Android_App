package com.mjchoi.finalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.mjchoi.finalapp.data.DatabaseHandler;
import com.mjchoi.finalapp.model.Habit;
import com.mjchoi.finalapp.ui.HabitAdapter;

import java.util.ArrayList;
import java.util.List;

public class HabitListActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private HabitAdapter habitAdapter;
    private List<Habit> habitList;
    private DatabaseHandler dbHandler;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText habitItem;
    private EditText frequency;
    private Button saveButton;

    private NotificationManagerCompat notificationManager;

    public static final String CHANNEL_ID = "CHANNEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        notificationManager = NotificationManagerCompat.from(this);

        // Set the title of the toolbar
        getSupportActionBar().setTitle("Habit List");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a dialog when floating action button is pressed
                createDialog();
            }
        });

        dbHandler = new DatabaseHandler(this, "habit_details");

        recyclerView = findViewById(R.id.recyclerViewHabit);
        recyclerView.setHasFixedSize(true); // All the items are fixed correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sets up to do list and instantiate it to new ArrayList
        habitList = new ArrayList<>();

        // Get items from the database
        habitList = dbHandler.getAllHabits();


        // Instantiate RecyclerViewAdapter and pass the list of items
        habitAdapter = new HabitAdapter(this, habitList);      // MODIFIED JUNE 12TH
        recyclerView.setAdapter(habitAdapter);

        // Update recyclerViewAdapter whenever data has been changed
        habitAdapter.notifyDataSetChanged();

    }   // end of onCreate

    // Create a dialog that prompts to add an item to the list
    private void createDialog() {

        // Instantiate AlertDialog builder
        dialogBuilder = new AlertDialog.Builder(this);

        // Inflate the dialog to the view
        View view = getLayoutInflater().inflate(R.layout.add_habit_dialog, null);   // ADDED JUNE 13TH

        // Go through the view that has been created
        habitItem = view.findViewById(R.id.habit_item);
        frequency = view.findViewById(R.id.frequency_item);
        saveButton = view.findViewById(R.id.save_habit_button);

        // Invoke the builder and set view
        dialogBuilder.setView(view);

        // Create Alert Dialog object
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !habitItem.getText().toString().isEmpty()
                        && !frequency.getText().toString().isEmpty()) {

                    // Call saveItem method to save items to database when the save button is pressed
                    saveItem(v);
                } else {
                    Snackbar.make(v, "Please enter details", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }   // end of createDialog

    // Save each item to database
    private void saveItem(View view) {
        Habit habit = new Habit();

        String newHabitName = habitItem.getText().toString().trim();
        int newFrequency = Integer.parseInt(frequency.getText().toString().trim());

        if(newFrequency <= 7 && newFrequency != 0){
            habit.setHabitName(newHabitName);
            habit.setHabitFrequency(newFrequency);

            // Save to database
            dbHandler.addHabit(habit);

 //           createNotification();

            // Use snackbar to show to user that the items has been saved
            Snackbar.make(view, "Item Saved",Snackbar.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //code to be run
                    dialog.dismiss();




                    // Start a NEW activity
                    startActivity(new Intent(HabitListActivity.this, HabitListActivity.class));
                    finish();

                }
            }, 1200);// 1sec
        }
        else{
            Toast.makeText(getApplicationContext(), "Invalid value for weekly target", Toast.LENGTH_LONG).show();
        }
    }   // end of saveItem
/*
    private void createNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title")
                .setContentText("message")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(1, notification);



    }*/


    @Override
    // Inflate the menu; this adds an option (back to menu) to the toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    // Handles action when the toolbar is pressed
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Direct user to the menu in navigation
        if (id == R.id.action_menu) {
            startActivity(new Intent(HabitListActivity.this, NavigationActivity.class));
        }

        if (id == R.id.logout_menu) {
            startActivity(new Intent(HabitListActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}

