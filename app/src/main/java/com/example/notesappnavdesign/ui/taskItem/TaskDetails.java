package com.example.notesappnavdesign.ui.taskItem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.MainActivity;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.ui.tasks.TasksFragment;
import com.example.notesappnavdesign.ui.tasks.TasksViewModel;

public class TaskDetails extends AppCompatActivity {

    private Toolbar toolbar;
    private Button buttonCreate;
    private EditText editTextName;
    private TasksViewModel tasksViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        toolbar = findViewById(R.id.toolbarBackHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        buttonCreate = findViewById(R.id.create_btn);
        editTextName = findViewById(R.id.taskName);
        tasksViewModel = ViewModelProviders.of(this).get(TasksViewModel.class);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    private void addTask() {
        String taskName = editTextName.getText().toString();

        if (taskName.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please add name for new list", Toast.LENGTH_LONG).show();
        } else {
            ItemTask itemTask = new ItemTask(taskName);
            tasksViewModel.insertTask(itemTask);
            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
