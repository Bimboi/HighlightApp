package com.example.notesappnavdesign.ui.taskItem;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
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

public class TaskView extends AppCompatActivity {

    private TasksViewModel tasksViewModel;
    private Toolbar toolbar;
    private TextView textTaskName;
    private TextView textDescription;
    private String name;
    private String desc;
    private AllTaskAdapter allTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        tasksViewModel =
                ViewModelProviders.of(this).get(TasksViewModel.class);

        toolbar = findViewById(R.id.toolbarBackHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences preferences = getSharedPreferences("My prefs", MODE_PRIVATE);

        name = preferences.getString("Task Name", "");
        desc = preferences.getString("Task Description", "");

        textTaskName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);

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

    public void deleteTask(){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_update:
                updateTask();
                break;
            case R.id.navigation_delete:
                deleteTask();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }
}