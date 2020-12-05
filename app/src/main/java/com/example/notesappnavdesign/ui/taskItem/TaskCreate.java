package com.example.notesappnavdesign.ui.taskItem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappnavdesign.ColorAdapter;
import com.example.notesappnavdesign.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class TaskCreate extends AppCompatActivity{
    public static final String EXTRA_NAME
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DATE";
    public static final String EXTRA_FLAG
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_FLAG";
    public static final String EXTRA_COLOR
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_COLOR";

    private EditText dateText;
    private EditText editTextName;
    private EditText editTextDesc;
    private ImageView taskImportance;
    private boolean flag;
    private String date;
    private String color;
    private String formatDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        Toolbar toolbar = findViewById(R.id.toolbarBackHome);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        dateText = findViewById(R.id.dateText);
        ImageView buttonDate = findViewById(R.id.date_pick_btn);
        editTextName = findViewById(R.id.taskName);
        editTextDesc = findViewById(R.id.taskDesc);

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        flag = false;
        taskImportance = findViewById(R.id.important_status);
        taskImportance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Flag function: ","clicked");
                changeFlag();
            }
        });

        ArrayList<String> colorImages = new ArrayList<>();
        colorImages.add("no color");
        colorImages.add("black");
        colorImages.add("cyan");
        colorImages.add("magenta");
        colorImages.add("yellow");

        RecyclerView recyclerView = findViewById(R.id.colorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);

        ColorAdapter adapterColor = new ColorAdapter(colorImages);
        recyclerView.setAdapter(adapterColor);

        color = "#FFFFFF";
        adapterColor.setOnItemClickListener(new ColorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String strColor) {
                color = strColor;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_nav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_save) {
            addTask();
        }
        return false;
    }

    private void addTask() {
        String taskName = editTextName.getText().toString();
        String taskDesc = editTextDesc.getText().toString();
        String taskDate = dateText.getText().toString();
        int taskImportance = flag ? 1 : 0;

        Log.d("taskImportance: ",taskImportance+"");

        if (!taskName.trim().isEmpty() && !taskDate.equals("")) {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME,taskName);
            data.putExtra(EXTRA_DESCRIPTION, taskDesc);
            data.putExtra(EXTRA_DATE, taskDate);
            data.putExtra(EXTRA_FLAG, taskImportance);
            Log.d("Color here: ",color);
            data.putExtra(EXTRA_COLOR, color);

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
                        if(day < 10){
                            formatDay = "0" + day;
                        } else {
                            formatDay = day + "";
                        }
                        dateText.setText(formatDay+"-"+month+"-"+year);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
    }

    private void changeFlag(){
        Log.d("Flag function: ","inside");
        if(!flag){
            taskImportance.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_important_flag));
            flag = true;
            Log.d("Flag function:",flag+"");
        }else {
            taskImportance.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_important_notflag));
            flag = false;
        }
    }
}
