package com.mjchoi.finalapp;


import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mjchoi.finalapp.model.Item;
import com.mjchoi.finalapp.ui.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;



public class DoListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<Item> todoList;
    private DatabaseHandler dbHandler1;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText taskItem;
    private EditText description;
    private EditText priority;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set the title of the toolbar
        getSupportActionBar().setTitle("To Do List");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a dialog when floating action button is pressed
                createDialog();
            }
        });

//        databaseHandler = new DatabaseHandler(this);  // COMMENTED OUT JUNE 5TH
        dbHandler1 = new DatabaseHandler(this, "task_details");


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true); // All the items are fixed correctly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Sets up to do list and instantiate it to new ArrayList
        todoList = new ArrayList<>();

        // Get items from the database
//        todoList = databaseHandler.getAllTasks(); COMMENTED OUT JUNE 5TH
        todoList = dbHandler1.getAllTasks();

        // Instantiate RecyclerViewAdapter and pass the list of items
        recyclerViewAdapter = new RecyclerViewAdapter(this, todoList);
        recyclerView.setAdapter(recyclerViewAdapter);

        // Update recyclerViewAdapter whenever data has been changed
        recyclerViewAdapter.notifyDataSetChanged();

    }   // end of onCreate

    // Create a dialog that prompts to add an item to the list
    private void createDialog() {

        // Instantiate AlertDialog builder
        dialogBuilder = new AlertDialog.Builder(this);

        // Inflate the dialog to the view
        View view = getLayoutInflater().inflate(R.layout.to_do_dialog, null);

        // Go through the view that has been created
        taskItem = view.findViewById(R.id.to_do_item);
        description = view.findViewById(R.id.desc_item);
        priority = view.findViewById(R.id.priority_item);
        saveButton = view.findViewById(R.id.save_button);

        // Invoke the builder and set view
        dialogBuilder.setView(view);

        // Create Alert Dialog object
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !taskItem.getText().toString().isEmpty()
                        && !description.getText().toString().isEmpty()
                        && !priority.getText().toString().isEmpty()) {

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
        Item item = new Item();

        String newItemName = taskItem.getText().toString().trim();
        String newDescription = description.getText().toString().trim();
        int newPriority = Integer.parseInt(priority.getText().toString().trim());

        if (newPriority <= 3 && newPriority != 0) {
            item.setTaskName(newItemName);
            item.setTaskDescription(newDescription);
            item.setTaskPriority(newPriority);

            // Save to database
//            databaseHandler.addTask(item); COMMENTED OUT JUNE 5TH
            dbHandler1.addTask(item);

            // Use snackbar to show to user that the items has been saved
            Snackbar.make(view, "Item Saved", Snackbar.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //code to be run
                    dialog.dismiss();

                    // Start a NEW activity
                    startActivity(new Intent(DoListActivity.this, DoListActivity.class));
                    finish();
                }
            }, 1200);// 1sec

        } else {
            Toast.makeText(getApplicationContext(), "Invalid value for priority", Toast.LENGTH_LONG).show();

        }

    }


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
            startActivity(new Intent(DoListActivity.this, NavigationActivity.class));
        }
        if (id == R.id.logout_menu) {
            startActivity(new Intent(DoListActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
