package com.example.notesappnavdesign.ui.taskItem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.notesappnavdesign.R;

import java.util.Calendar;
import java.util.Date;

public class TaskCreate extends AppCompatActivity{
    public static final String EXTRA_NAME
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DATE";

    private TextView dateText;
    private EditText editTextName;
    private EditText editTextDesc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        Toolbar toolbar = findViewById(R.id.toolbarBackHome);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button buttonCreate = findViewById(R.id.create_btn);
        ImageView buttonDate = findViewById(R.id.date_pick_btn);
        dateText = findViewById(R.id.dateText);
        editTextName = findViewById(R.id.taskName);
        editTextDesc = findViewById(R.id.taskDesc);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void addTask() {
        String taskName = editTextName.getText().toString();
        String taskDesc = editTextDesc.getText().toString();
        String taskDate = dateText.getText().toString();

        if (!taskName.trim().isEmpty() && !taskDate.equals("dd-mm-yyyy")) {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME,taskName);
            data.putExtra(EXTRA_DESCRIPTION, taskDesc);
            data.putExtra(EXTRA_DATE, taskDate);

            setResult(RESULT_OK,data);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Please add name and date", Toast.LENGTH_LONG).show();
        }
    }
    private void showDatePickerDialog(){
        new DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month++;
                        Log.d("Date Calendar: ", new Date()+"");
                        dateText.setText(day+"-"+month+"-"+year);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
    }
}
