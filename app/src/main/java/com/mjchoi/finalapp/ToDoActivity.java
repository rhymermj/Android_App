package com.mjchoi.finalapp;

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
import com.mjchoi.finalapp.model.Item;


public class ToDoActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText todoItem;
    private EditText description;
    private EditText priority;
    private Button saveButton;
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        dbHandler = new DatabaseHandler(this, "task_details");

        // Suppressing this activity will allow the user to go straight to the to-do list
        byPassActivity();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("To Do");


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
        View view = getLayoutInflater().inflate(R.layout.to_do_dialog, null);

        todoItem = view.findViewById(R.id.to_do_item);
        description = view.findViewById(R.id.desc_item);
        priority = view.findViewById(R.id.priority_item);
        saveButton = view.findViewById(R.id.save_button);


        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!todoItem.getText().toString().isEmpty()
                        && !description.getText().toString().isEmpty()
                        && !priority.getText().toString().isEmpty()) {

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
        Item item = new Item();

        String newName = todoItem.getText().toString().trim();
        String newDescription = description.getText().toString().trim();
        int newPriority = Integer.parseInt(priority.getText().toString().trim());

        if(newPriority <= 3 && newPriority != 0){
            item.setTaskName(newName);
            item.setTaskDescription(newDescription);
            item.setTaskPriority(newPriority);

            // Save to database
            dbHandler.addTask(item);

            Snackbar.make(view, "Item Saved",Snackbar.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //code to be run
                    dialog.dismiss();
                    //Move to next screen
                    startActivity(new Intent(ToDoActivity.this, DoListActivity.class));
                    //   finish();

                }
            }, 1200);// 1sec
        }
        else{
            Toast.makeText(getApplicationContext(), "Invalid value for priority", Toast.LENGTH_LONG).show();
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
            startActivity(new Intent(ToDoActivity.this, NavigationActivity.class));
        }

        if (id == R.id.logout_menu) {
            startActivity(new Intent(ToDoActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    private void byPassActivity() {
        //Checks if database is empty; if not, then we just
        //go to ListActivity and show all added items

        if (dbHandler.getItemsCount() > 0) {
            startActivity(new Intent(ToDoActivity.this, DoListActivity.class));
            finish();
        }
    }


}
