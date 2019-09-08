package com.mjchoi.finalapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mjchoi.finalapp.model.Habit;
import com.mjchoi.finalapp.model.Item;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;




public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
//    private static final String DB_NAME = "task";
    private static final String TABLE_TASKS = "task_details";
    private static final String KEY_TASK_ID = "task_id";
    private static final String KEY_TASK_NAME = "task_name";
    private static final String KEY_TASK_DESC = "task_description";
    private static final String KEY_TASK_PRIORITY = "task_priority";
    private static final String KEY_TASK_DATE = "task_date";

    private static final String TABLE_HABITS = "habit_details";
    private static final String KEY_HABIT_ID = "habit_id";
    private static final String KEY_HABIT_NAME = "habit_name";
    private static final String KEY_HABIT_FREQUENCY = "habit_frequency";


//    private static final String KEY_HABIT_PROGRESS = "habit_progress";                                      // JUNE 13TH



    private static final String KEY_HABIT_DATE = "habit_date";




    //    private final Context context;        // COMMENTED OUT JUNE 5TH
    public String databaseName;

    // Constructor
    // ADDED JUNE 5TH
    public DatabaseHandler(Context context, String databaseName) {
        super(context, databaseName, null, DB_VERSION);
        this.databaseName = databaseName;
    }


    @Override
    // Create database; creates a table and the initial population of the table happens
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TASK_ID + " INTEGER PRIMARY KEY,"
                + KEY_TASK_NAME + " TEXT,"
                + KEY_TASK_DESC + " TEXT,"
                + KEY_TASK_PRIORITY + " INTEGER,"                                                      // JUNE 14TH
                // Dates to be taken as the timestamp of the system and then converted into data
                + KEY_TASK_DATE + " LONG);";

        String CREATE_HABIT_TABLE = "CREATE TABLE " + TABLE_HABITS + "("
                + KEY_HABIT_ID + " INTEGER PRIMARY KEY,"
                + KEY_HABIT_NAME + " TEXT,"
                + KEY_HABIT_FREQUENCY + " INTEGER,"


//                + KEY_HABIT_PROGRESS + "INTEGER,"                                                    // JUNE 13TH


                + KEY_HABIT_DATE + " LONG);";


        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_HABIT_TABLE);

    }   // end of onCreate

    @Override
    // Upgrades the database
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drops the table if already exists, go ahead and create a new table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS);
        onCreate(db);

    }   // end of onUpgrade


    /* ------------------- */
    //   CRUD operations   //
    /* ------------------- */

    // Insert tasks to the table
    public void addTask(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        // Create an instance of ContentValues and store data as key-value pairs
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, item.getTaskName());
        values.put(KEY_TASK_DESC, item.getTaskDescription());
        values.put(KEY_TASK_PRIORITY, item.getTaskPriority());

        // Gives current timestamp of the system
        values.put(KEY_TASK_DATE, java.lang.System.currentTimeMillis());

        // Insert the row
        db.insert(TABLE_TASKS, null, values);

        // Check if task was added for debugging purpose
        Log.d("DBHandler", "added item: " );

    }   // end of addTask



    // Insert tasks to the table
    public void addHabit(Habit habit){
        SQLiteDatabase db = this.getWritableDatabase();

        // Create an instance of ContentValues and store data as key-value pairs
        ContentValues values = new ContentValues();
        values.put(KEY_HABIT_NAME, habit.getHabitName());
        values.put(KEY_HABIT_FREQUENCY, habit.getHabitFrequency());

//        values.put(KEY_HABIT_PROGRESS, habit.getHabitProgress());                                  // ADDED JUNE 13TH


        // Gives current timestamp of the system
        values.put(KEY_HABIT_DATE, java.lang.System.currentTimeMillis());

        // Insert the row
        db.insert(TABLE_HABITS, null, values);

        // Check if task was added for debugging purpose
        Log.d("DBHandler", "added item: " );

    }   // end of addHabit

    // Get all items
    public List<Item> getAllTasks(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_TASKS,
                new String[]{KEY_TASK_ID,
                        KEY_TASK_NAME,
                        KEY_TASK_DESC,
                        KEY_TASK_PRIORITY,
                        KEY_TASK_DATE},
                null, null, null, null,
                KEY_TASK_PRIORITY + " ASC");

        // Use do-while loop to get all items and save them to the ArrayList
        if (cursor.moveToFirst()) {
            do{
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_TASK_ID))));
                item.setTaskName(cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME)));
                item.setTaskDescription(cursor.getString(cursor.getColumnIndex(KEY_TASK_DESC)));
                item.setTaskPriority(cursor.getInt(cursor.getColumnIndex(KEY_TASK_PRIORITY)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(KEY_TASK_DATE))).getTime());

                item.setDateItemAdded(formattedDate);

                // Add to ArrayList
                itemList.add(item);

            }while(cursor.moveToNext());

        } //cursor.close();  // ADDED 06/12

        return itemList;
    }


    // Get all items
    public List<Habit> getAllHabits(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Habit> habitList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_HABITS,
                new String[]{KEY_HABIT_ID,
                        KEY_HABIT_NAME,
                        KEY_HABIT_FREQUENCY,

//                        KEY_HABIT_PROGRESS,                                                         // ADDED JUNE 13TH

                        KEY_HABIT_DATE},
                null, null, null, null,
                KEY_HABIT_DATE + " DESC");

        // Use do-while loop to get all items and save them to the ArrayList
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Habit habit = new Habit();
                    habit.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_HABIT_ID))));
                    habit.setHabitName(cursor.getString(cursor.getColumnIndex(KEY_HABIT_NAME)));
                    habit.setHabitFrequency(cursor.getInt(cursor.getColumnIndex(KEY_HABIT_FREQUENCY)));

                    DateFormat dateFormat = DateFormat.getDateInstance();
                    String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(KEY_HABIT_DATE))).getTime());

                    habit.setDateHabitAdded(formattedDate);

                    // Add to ArrayList
                    habitList.add(habit);

                } while (cursor.moveToNext());

            }
            //cursor.close();// ADDED WEDend of while loop
        }
        return habitList;

    }



    // Update habit
    public int updateHabit(Habit habit) {
        SQLiteDatabase db1 = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HABIT_NAME, habit.getHabitName());
        values.put(KEY_HABIT_FREQUENCY, habit.getHabitFrequency());



//        values.put(KEY_HABIT_PROGRESS, habit.getHabitProgress());                                  // ADDED JUNE 13TH


        values.put(KEY_HABIT_DATE, java.lang.System.currentTimeMillis());

        // Update row using id
        return db1.update(TABLE_HABITS, values,
                KEY_HABIT_ID + "=?",
                new String[]{String.valueOf(habit.getId())}); // Convert int id to String

    }   // end of updateHabit


    // Update task
    public int updateTask (Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, item.getTaskName());
        values.put(KEY_TASK_DESC, item.getTaskDescription());
        values.put(KEY_TASK_PRIORITY, item.getTaskPriority());
        values.put(KEY_TASK_DATE, java.lang.System.currentTimeMillis());

        // Update row using id
        return db.update(TABLE_TASKS, values,
                KEY_TASK_ID + "=?",
                new String[]{String.valueOf(item.getId())}); // Convert int id to String
    }   // end of updateTask

    // Delete Item
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TASKS,
                KEY_TASK_ID + "=?",
                new String[]{String.valueOf(id)});

        //close database
        db.close();

    }   // end of deleteTask

    // Delete Habit
    public void deleteHabit(int id) {
        SQLiteDatabase db1 = this.getWritableDatabase();

        db1.delete(TABLE_HABITS,
                KEY_HABIT_ID + "=?",
                new String[]{String.valueOf(id)});

        //close database
        db1.close();

    }   // end of deleteHabit

    // Get item count
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + TABLE_TASKS;

//        String countQuery2 = "SELECT * FROM " + TABLE_HABITS;


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }

    // Get item count
    public int getHabitsCount() {
        String countQuery = "SELECT * FROM " + TABLE_HABITS;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        return cursor.getCount();
    }
}

