package com.example.notesappnavdesign.ui.taskItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.MainActivity;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.ui.tasks.TasksViewModel;

public class TaskEdit extends AppCompatActivity {
//    public static final String EXTRA_ID_EDIT
//            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_ID_EDIT";
//    public static final String EXTRA_NAME_EDIT
//            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_NAME_EDIT";
//    public static final String EXTRA_DESCRIPTION_EDIT
//            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DESCRIPTION_EDIT";

    private Toolbar toolbar;
    private TextView textViewName;
    private EditText editTextName;
    private EditText editTextDesc;
    private Button buttonSave;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        toolbar = findViewById(R.id.toolbarBackTaskView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        textViewName = findViewById(R.id.textUpdateTask);
        editTextName = findViewById(R.id.taskNameUpdate);
        editTextDesc = findViewById(R.id.taskDescUpdate);

        SharedPreferences preferences = getSharedPreferences("My prefs", MODE_PRIVATE);

        String name = preferences.getString("Task Name","");

        textViewName.setText(name);
        editTextName.setText(name);
        editTextDesc.setText(preferences.getString("Task Description",""));

        id = preferences.getInt("Task ID",0);

        buttonSave = findViewById(R.id.save_btn);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
    }

    public void saveChanges(){ ;
        String taskName = editTextName.getText().toString();
        String taskDesc = editTextDesc.getText().toString();
        TasksViewModel tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);

        if (taskName.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please provide a name", Toast.LENGTH_LONG).show();
        } else {
            ItemTask itemTask = new ItemTask(taskName,taskDesc);
            itemTask.settId(id);
            tasksViewModel.updateTask(itemTask);
            SharedPreferences.Editor editor = getSharedPreferences("My prefs", MODE_PRIVATE).edit();
            editor.putString("Task Name",taskName);
            editor.putString("Task Description", taskDesc);
            editor.apply();
            startActivity(new Intent(this, TaskView.class));
        }
    }
}