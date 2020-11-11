package com.example.notesappnavdesign.ui.taskItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.AllViewModel;

import java.util.Calendar;

public class TaskEdit extends AppCompatActivity {
    public static final String EXTRA_NAME_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DATE";

    private Toolbar toolbar;
    private TextView textViewName;
    private EditText editTextName;
    private EditText editTextDesc;
    private Button buttonSave;
    private Integer id;
    private TextView taskDateEdit;

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
        taskDateEdit = findViewById(R.id.dateTextEdit);

        SharedPreferences preferences = getSharedPreferences("My prefs", MODE_PRIVATE);

        String name = getIntent().getStringExtra("name");

        Log.d("name extra: ",name);
        textViewName.setText(name);
        editTextName.setText(name);
        editTextDesc.setText(getIntent().getStringExtra("desc"));
        taskDateEdit.setText(getIntent().getStringExtra("date"));

        id = preferences.getInt("Task ID", 0);

        buttonSave = findViewById(R.id.save_btn);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });

        ImageView buttonDate = findViewById(R.id.date_edit_btn);
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    public void saveChanges() {
        String taskName = editTextName.getText().toString();
        String taskDesc = editTextDesc.getText().toString();
        String taskDate = taskDateEdit.getText().toString();

        if (!taskName.trim().isEmpty() && !taskDate.equals("dd-mm-yyyy")) {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME_EDIT,taskName);
            data.putExtra(EXTRA_DESCRIPTION_EDIT, taskDesc);
            data.putExtra(EXTRA_DATE_EDIT, taskDate);

//            SharedPreferences.Editor editor = getSharedPreferences("My prefs", MODE_PRIVATE).edit();
//            editor.putString("Task Name", taskName);
//            editor.putString("Task Description", taskDesc);
//            editor.putString("Task Date", taskDate);
//            editor.apply();

            setResult(RESULT_OK,data);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Please provide a name and date", Toast.LENGTH_LONG).show();
        }
    }
    private void showDatePickerDialog(){
        new DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar.getInstance().set(year,month,day);
                        taskDateEdit.setText(day+"-"+month+"-"+year);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
    }
}