package com.mjchoi.finalapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mjchoi.finalapp.DoListActivity;
import com.mjchoi.finalapp.R;
import com.mjchoi.finalapp.data.DatabaseHandler;
import com.mjchoi.finalapp.model.Item;

import java.text.MessageFormat;
import java.util.List;




public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Item> todoItems;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    // Called inside DoListActivity
    public RecyclerViewAdapter(Context context, List<Item> todoItems){
        this.context = context;
        this.todoItems = todoItems;
    }

    @Override
    // Call ViewHolder class and create the view
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create view and inflate list row
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);

        // Pass the view created
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // Create object items
        Item item = todoItems.get(position);

        viewHolder.taskName.setText(item.getTaskName());
        viewHolder.taskDescription.setText(MessageFormat.format("Description: {0}", item.getTaskDescription()));
        viewHolder.taskPriority.setText(MessageFormat.format("Priority: {0}", String.valueOf(item.getTaskPriority())));
        viewHolder.dateAdded.setText(MessageFormat.format("Added on: {0}", item.getDateItemAdded()));
    }

    @Override
    public int getItemCount() {

        return todoItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Create instance variables pertaining to the list row
        public TextView taskName;
        public TextView taskDescription;
        public TextView taskPriority;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id; // id of each item



        public ViewHolder(@NonNull View view, Context ctx) {
            super(view);
            context = ctx;

            taskName = view.findViewById(R.id.item_name);
            taskDescription = itemView.findViewById(R.id.item_description);
            taskPriority = itemView.findViewById(R.id.item_priority);
            dateAdded = itemView.findViewById(R.id.item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Item item = todoItems.get(position);

            switch(v.getId()){
                case R.id.editButton:
                    // Edit item
                    editItem(item);
                    break;

                case R.id.deleteButton:
                    // Delete item
                    deleteItem(item.getId());
                    break;
            }
        }

        // Deletes task item when user confirms to delete
        private void deleteItem(final int id){
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
                    DatabaseHandler db1 = new DatabaseHandler(context, "task_details"); // MODIFIED JUNE 5TH

                    // Delete the task from database
                    db1.deleteTask(id);
                    todoItems.remove(getAdapterPosition());
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

        private void editItem(final Item newItem) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.to_do_dialog, null);

            final EditText todoItem;
            final EditText itemDescription;
            final EditText itemPriority;

            todoItem = view.findViewById(R.id.to_do_item);
            itemDescription = view.findViewById(R.id.desc_item);
            itemPriority = view.findViewById(R.id.priority_item);
            Button saveButton = view.findViewById(R.id.save_button);
            saveButton.setText("Update");

            // Populate the dialog with current data when editing
            todoItem.setText(newItem.getTaskName());
            itemDescription.setText(newItem.getTaskDescription());
            itemPriority.setText(String.valueOf(newItem.getTaskPriority()));

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db1 = new DatabaseHandler(context, "task_details");

                    // Update items

                    newItem.setTaskName(todoItem.getText().toString());
                    newItem.setTaskDescription(itemDescription.getText().toString());
                    newItem.setTaskPriority(Integer.parseInt(itemPriority.getText().toString()));           // MODIFIED JUNE 14TH

                    if(!todoItem.getText().toString().isEmpty()
                            && !itemDescription.getText().toString().isEmpty()
                            && !itemPriority.getText().toString().isEmpty()){

                        db1.updateTask(newItem);
                        notifyItemChanged(getAdapterPosition(),newItem);
                        context.startActivity(new Intent(context, DoListActivity.class));
                    }
                    else{
                        Snackbar.make(view, "Fields Empty",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }
                    dialog.dismiss();
                }

            });

        }

    }
}

