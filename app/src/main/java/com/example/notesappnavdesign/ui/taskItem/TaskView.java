package com.example.notesappnavdesign.ui.taskItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.AllViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class TaskView extends AppCompatActivity {
    public static final int EDIT_TASK_REQUEST = 2;

    private AllViewModel allViewModel;
    private String name, desc, date, color, tempDesc = "No description";
    private Integer id, importance;
    private TextView textTaskName, textDescription, textDate, textTitleDesc, textTitleDate;
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
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
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

        name = preferences.getString("Task Name", "");
        desc = preferences.getString("Task Description", "");
        date = preferences.getString("Task Date", "");
        id = preferences.getInt("Task ID", 0);
        importance = preferences.getInt("Task Importance", 0);
        color = preferences.getString("Task Color", "");

        textTaskName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);
        textDate = findViewById(R.id.textDate);
        textTitleDesc = findViewById(R.id.textTitleDescription);
        textTitleDate = findViewById(R.id.textTitleDate);

        if(desc.equals("")){ textDescription.setText(tempDesc); }
        else textDescription.setText(desc);

        textTaskName.setText(name);
        textDate.setText(reformatDate(date));

        appBarLayout = findViewById(R.id.appBarView);
        scrollView = findViewById(R.id.taskScrollView);

        applyColor(color);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (color.equals("#494949")) {
            inflater.inflate(R.menu.edit_nav_menu_white, menu);
        } else {
            inflater.inflate(R.menu.edit_nav_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_update || item.getItemId() == R.id.navigation_update_white) {
            updateTask();
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            assert data != null;
            String taskName = data.getStringExtra(TaskEdit.EXTRA_NAME_EDIT);
            String description = data.getStringExtra(TaskEdit.EXTRA_DESCRIPTION_EDIT);
            String taskDate = data.getStringExtra(TaskEdit.EXTRA_DATE_EDIT);
            int importance = data.getIntExtra(TaskEdit.EXTRA_FLAG_EDIT, 0);
            String color = data.getStringExtra(TaskEdit.EXTRA_COLOR_EDIT);

            ItemTask itemTask = new ItemTask(taskName, description, taskDate, importance, color);
            itemTask.settId(id);
            allViewModel.updateTask(itemTask);

            assert description != null;
            if(description.equals("")){ textDescription.setText(tempDesc); }
            else textDescription.setText(description);

            textTaskName.setText(taskName);
            textDate.setText(reformatDate(taskDate));

            updateActivityData(taskName, description, taskDate, importance, color);
            applyColor(color);
            invalidateOptionsMenu();

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date d = dateFormat.parse(date);
            assert d != null;
            Log.d("New Date: ", d.toString());
            dateFormat.applyPattern("EEEE, MMM dd, yyyy");
            return dateFormat.format(d);

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

    public void applyColor(String color) {
        int colorID = Color.parseColor(color);
        int blackID = Color.parseColor("#000000");
        int goldID = Color.parseColor("#FFD700");

        switch (color) {
            case "#494949":  //black
                int whiteID = Color.parseColor("#FFFFFF");

                textTaskName.setTextColor(whiteID);
                textDescription.setTextColor(whiteID);
                textDate.setTextColor(whiteID);
                textTitleDesc.setTextColor(goldID);
                textTitleDate.setTextColor(goldID);
                Objects.requireNonNull(getSupportActionBar()).
                        setHomeAsUpIndicator(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_back_white));
                break;
            case "#E2E165":  //yellow
                int darkBlueID = Color.parseColor("#66553E");

                textTaskName.setTextColor(blackID);
                textDescription.setTextColor(blackID);
                textDate.setTextColor(blackID);
                textTitleDesc.setTextColor(darkBlueID);
                textTitleDate.setTextColor(darkBlueID);
                Objects.requireNonNull(getSupportActionBar())
                        .setHomeAsUpIndicator(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_back_black));
                break;
            case "#F181C3":  //magenta
                textTaskName.setTextColor(blackID);
                textDescription.setTextColor(blackID);
                textDate.setTextColor(blackID);
                textTitleDesc.setTextColor(goldID);
                textTitleDate.setTextColor(goldID);
                Objects.requireNonNull(getSupportActionBar())
                        .setHomeAsUpIndicator(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_back_black));
                break;
            default:
                int darkGreyID = Color.parseColor("#333333");

                textTaskName.setTextColor(blackID);
                textDescription.setTextColor(blackID);
                textDate.setTextColor(blackID);
                textTitleDesc.setTextColor(darkGreyID);
                textTitleDate.setTextColor(darkGreyID);
                Objects.requireNonNull(getSupportActionBar())
                        .setHomeAsUpIndicator(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_back_black));
                break;
        }

        appBarLayout.setBackgroundColor(colorID);
        scrollView.setBackgroundColor(colorID);
    }
}