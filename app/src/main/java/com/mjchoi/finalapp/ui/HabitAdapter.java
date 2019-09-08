package com.mjchoi.finalapp.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.mjchoi.finalapp.R;
import com.mjchoi.finalapp.data.DatabaseHandler;
import com.mjchoi.finalapp.model.Habit;

import java.text.BreakIterator;
import java.text.MessageFormat;
import java.util.List;


public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitHolder> {
    private Context context;
    private List<Habit> habitItems;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    public HabitAdapter(Context context, List<Habit> habitItems){
        this.context = context;
        this.habitItems = habitItems;
    }

    @Override
    // Call ViewHolder class and create the view
    public HabitAdapter.HabitHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.habit_row, viewGroup, false);

        // Pass the view created
        return new HabitHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.HabitHolder habitHolder, int position) {
        // Create object items
        Habit habit = habitItems.get(position);

        habitHolder.habitName.setText(habit.getHabitName());
        habitHolder.habitFrequency.setText(MessageFormat.format("Weekly Target: {0}", habit.getHabitFrequency()));
        habitHolder.dateAdded.setText(MessageFormat.format("Added on: {0}", habit.getDateHabitAdded()));
    }


    @Override
    public int getItemCount() {

        return habitItems.size();
    }

    public class HabitHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView habitName;
        public TextView habitFrequency;

        public int habitProgress;



        public TextView dateAdded;
        public ProgressBar progressBar;
        public Button editHabitButton;
        public Button deleteHabitButton;
        public int id; // id of each item


        // ADDED JUNE 12TH
        public Button checkInButton;

        final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / 7);


        public String result;
        public String habitResult;


        public HabitHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;


            habitName = view.findViewById(R.id.create_habit);
            habitFrequency = itemView.findViewById(R.id.habit_frequency);


            progressBar = itemView.findViewById(R.id.progress_bar);
            dateAdded = itemView.findViewById(R.id.habit_date);
            editHabitButton = itemView.findViewById(R.id.edit_Habit_Button);
            deleteHabitButton = itemView.findViewById(R.id.delete_Habit_Button);


//            checkInButton = itemView.findViewById(R.id.checkIn_Button);


            editHabitButton.setOnClickListener(this);
            deleteHabitButton.setOnClickListener(this);


//            checkInButton.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Habit habit = habitItems.get(position);

            switch(v.getId()){


//                case R.id.checkIn_Button:
//                    // Increment
//                    boolean selection = true;
//                    incrementScore(selection);
//                    break;


                case R.id.edit_Habit_Button:
                    // Edit item
                    editItem(habit);
                    break;

                case R.id.delete_Habit_Button:
                    // Delete item
                    deleteItem(habit.getId());
                    break;
            }

        }

//        private void incrementScore(boolean userSelection){
//            boolean positive = true;
//
//            if(userSelection == positive){
//                progressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
//
//            }
//        }

        private void editItem(final Habit newHabit) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.habit_dialog, null);

            final EditText habitItem;
            final EditText itemFrequency;
            final ImageView checkIn;

            habitItem = view.findViewById(R.id.habit_item);
            itemFrequency = view.findViewById(R.id.frequency_item);



            Button saveButton = view.findViewById(R.id.save_habit_button);
            saveButton.setText("Update");

            checkIn = view.findViewById(R.id.check_in); // ADDED JUNE 12TH
            checkIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean answer = true;
//                    int result = 0;                                     // ADDED JUNE 13TH
                    incrementScore(answer);

                }
            });


            // Populate the dialog with current data when editing
            habitItem.setText(newHabit.getHabitName());
            itemFrequency.setText(String.valueOf(newHabit.getHabitFrequency()));

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DatabaseHandler
                    DatabaseHandler db2 = new DatabaseHandler(context, "habit_details");

                    // Update items

                    newHabit.setHabitName(habitItem.getText().toString());
                    newHabit.setHabitFrequency(Integer.parseInt(itemFrequency.getText().toString()));

                    if(!habitItem.getText().toString().isEmpty()
                            && !itemFrequency.getText().toString().isEmpty()){

                        db2.updateHabit(newHabit);
                        notifyItemChanged(getAdapterPosition(),newHabit);
                    }else{
                        Snackbar.make(view, "Fields Empty",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    dialog.dismiss();
                }

            });

        }


        private void incrementScore(boolean userSelection){
            boolean positive = true;
            int progress = 0;

            if(userSelection == positive){
//                habitProgress = habitProgress + 1;                                            // ADDED 0613
                progressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);

            }
        }


        private void deleteItem(final int id) {
            // Instantiate AlertDialog builder
            builder = new AlertDialog.Builder(context);

            // Inflate the confirmation dialog
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_popup, null);

            // Instantiate buttons
            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();


            // When user confirms to delete, delete the task item and clear the dialog
            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // DatabaseHandler
                    DatabaseHandler db2 = new DatabaseHandler(context, "habit_details"); // MODIFIED JUNE 5TH

                    // Delete the task from database
                    db2.deleteHabit(id);
                    habitItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());


                    dialog.dismiss();
                }
            });

            // When user confirms not to delete, just get rid of the dialog
            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }
}
