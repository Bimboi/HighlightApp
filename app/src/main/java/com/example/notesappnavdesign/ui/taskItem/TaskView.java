package com.example.notesappnavdesign.ui.taskItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.MainActivity;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.TasksDatabase;
import com.example.notesappnavdesign.ui.tasks.AllTaskAdapter;
import com.example.notesappnavdesign.ui.tasks.TasksFragment;
import com.example.notesappnavdesign.ui.tasks.TasksViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskView extends AppCompatActivity {

    private TasksViewModel tasksViewModel;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        tasksViewModel =
                ViewModelProviders.of(this).get(TasksViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbarBackHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Toolbar toolbar2 = findViewById(R.id.toolbarDelete);
        toolbar2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.navigation_delete) {
                    deleteTask();
                }
                return false;
            }
        });

        SharedPreferences preferences = getSharedPreferences("My prefs", MODE_PRIVATE);

        String name = preferences.getString("Task Name", "");
        String desc = preferences.getString("Task Description", "");
        String date = preferences.getString("Task Date","");
        id = preferences.getInt("Task ID", 0);

        TextView textTaskName = findViewById(R.id.textName);
        TextView textDescription = findViewById(R.id.textDescription);
        TextView textDate = findViewById(R.id.textDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        try {
            Date d = dateFormat.parse(date);
            Log.d("New Date: ",d.toString());
            dateFormat.applyPattern("EEEE, MMM dd, yyyy");
            String strDate = dateFormat.format(d);
            textDate.setText(strDate);

        } catch (ParseException e) {
            Log.d("Date Error: ",e.toString());
        }

        textTaskName.setText(name);
        textDescription.setText(desc);

    }

    public void updateTask() {
//        Intent intent = new Intent(this, TaskEdit.class);
//        intent.putExtra("Task ID", getIntent().getExtras().getInt("Task ID"));
//        intent.putExtra("Task Name", name);
//        intent.putExtra("Task Description", desc);
        startActivity(new Intent(this, TaskEdit.class));
    }

    public void deleteTask() {
        confirmDelete().show();
    }

    public AlertDialog confirmDelete() {
        return new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("You are about to permanently delete this.")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tasksViewModel.deleteTaskById(id);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_update) {
            updateTask();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}