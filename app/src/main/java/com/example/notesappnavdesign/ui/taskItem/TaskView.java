package com.example.notesappnavdesign.ui.taskItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.MainActivity;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.AllViewModel;
import com.example.notesappnavdesign.ui.tasks.TasksFragment;
import com.google.android.material.appbar.AppBarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskView extends AppCompatActivity {
    public static final int EDIT_TASK_REQUEST = 2;

    private AllViewModel allViewModel;
    private SharedPreferences preferences;
    private String name, desc, date, color;
    private Integer id, importance;
    private TextView textTaskName, textDescription, textDate;
    private SimpleDateFormat dateFormat;
    private AppBarLayout appBarLayout;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        allViewModel =
                ViewModelProviders.of(this).get(AllViewModel.class);

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

        preferences = getSharedPreferences("My prefs", MODE_PRIVATE);

        name = preferences.getString("Task Name", "");
        desc = preferences.getString("Task Description", "");
        date = preferences.getString("Task Date", "");
        id = preferences.getInt("Task ID", 0);
        importance = preferences.getInt("Task Importance", 0);
        color = preferences.getString("Task Color", "");

        textTaskName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);
        textDate = findViewById(R.id.textDate);

        textTaskName.setText(name);
        textDescription.setText(desc);
        textDate.setText(reformatDate(date));

        appBarLayout = findViewById(R.id.appBarView);
        scrollView = findViewById(R.id.taskScrollView);

        appBarLayout.setBackgroundColor(Color.parseColor(color));
        scrollView.setBackgroundColor(Color.parseColor(color));

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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            String taskName = data.getStringExtra(TaskEdit.EXTRA_NAME_EDIT);
            String description = data.getStringExtra(TaskEdit.EXTRA_DESCRIPTION_EDIT);
            String taskDate = data.getStringExtra(TaskEdit.EXTRA_DATE_EDIT);
            int importance = data.getIntExtra(TaskEdit.EXTRA_FLAG_EDIT, 0);
            String color = data.getStringExtra(TaskEdit.EXTRA_COLOR_EDIT);

            ItemTask itemTask = new ItemTask(taskName, description, taskDate, importance, color);
            itemTask.settId(id);
            allViewModel.updateTask(itemTask);

            textTaskName.setText(taskName);
            textDescription.setText(description);
            textDate.setText(reformatDate(taskDate));

            updateActivityData(taskName, description, taskDate, importance, color);
            appBarLayout.setBackgroundColor(Color.parseColor(color));
            scrollView.setBackgroundColor(Color.parseColor(color));

            Toast.makeText(getApplicationContext(), "Successfully updated task", Toast.LENGTH_LONG).show();
        }
    }

    public void updateTask() {
        Intent i = new Intent(getApplicationContext(), TaskEdit.class);
        i.putExtra("name", name);
        i.putExtra("desc", desc);
        i.putExtra("date", date);
        i.putExtra("importance", importance);
        i.putExtra("color", color);
        startActivityForResult(i, EDIT_TASK_REQUEST);
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
                        allViewModel.deleteTaskById(id);
                        onBackPressed();
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

    public String reformatDate(String date) {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date d = dateFormat.parse(date);
            Log.d("New Date: ", d.toString());
            dateFormat.applyPattern("EEEE, MMM dd, yyyy");
            String strDate = dateFormat.format(d);
            return strDate;

        } catch (ParseException e) {
            Log.d("Date Error: ", e.toString());
            return "Error string";
        }
    }

    public void updateActivityData(String newName, String newDesc, String newDate, int newFlag,
                                   String color) {
        this.name = newName;
        this.desc = newDesc;
        this.date = newDate;
        this.importance = newFlag;
        this.color = color;
    }

    public String getLightColor(@NonNull String origColor) {
        String equivalent = "";
        switch (origColor) {
            case "#1A1A1A":
                equivalent = "#494949";
                break;
            case "#12A4D9":
                equivalent = "#8EC5D9";
                break;
            case "#D9138A":
                equivalent = "#F181C3";
                break;
            case "#E2D810":
                equivalent = "#E2E165";
                break;
        }
        return equivalent;
    }
}