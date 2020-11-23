package com.example.notesappnavdesign.ui.taskItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesappnavdesign.ColorAdapter;
import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.AllViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskEdit extends AppCompatActivity {
    public static final String EXTRA_NAME_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DESCRIPTION";
    public static final String EXTRA_DATE_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_DATE";
    public static final String EXTRA_FLAG_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_FLAG";
    public static final String EXTRA_COLOR_EDIT
            = "com.example.notesappnavdesign.ui.taskItem.EXTRA_COLOR";

    private EditText editTextName;
    private EditText editTextDesc;
    private EditText taskDateEdit;
    private ImageView taskFlag;
    private boolean flag;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_edit);

        Toolbar toolbar = findViewById(R.id.toolbarBackTaskView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TextView textViewName = findViewById(R.id.textUpdateTask);
        ImageView buttonDate = findViewById(R.id.date_edit_btn);
        editTextName = findViewById(R.id.taskNameUpdate);
        editTextDesc = findViewById(R.id.taskDescUpdate);
        taskDateEdit = findViewById(R.id.dateTextEdit);
        taskFlag = findViewById(R.id.important_status_edit);

        Bundle extras = getIntent().getExtras();
        assert extras != null;

        int importance = extras.getInt("importance", 0);
        String name = extras.getString("name");
        color = extras.getString("color");

        textViewName.setText(name);
        editTextName.setText(name);
        editTextDesc.setText(extras.getString("desc"));
        taskDateEdit.setText(extras.getString("date"));
        taskFlag.setImageDrawable(getImage(importance));

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        taskFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFlag();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.colorEditRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setHasFixedSize(true);

        ColorAdapter adapterColor = new ColorAdapter(getArrayList(color));
        recyclerView.setAdapter(adapterColor);

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
            saveChanges();
        }
        return false;
    }

    public void saveChanges() {
        String taskName = editTextName.getText().toString();
        String taskDesc = editTextDesc.getText().toString();
        String taskDate = taskDateEdit.getText().toString();
        int taskImportance = flag ? 1 : 0;

        if (!taskName.trim().isEmpty() && !taskDate.equals("")) {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME_EDIT, taskName);
            data.putExtra(EXTRA_DESCRIPTION_EDIT, taskDesc);
            data.putExtra(EXTRA_DATE_EDIT, taskDate);
            data.putExtra(EXTRA_FLAG_EDIT, taskImportance);
            data.putExtra(EXTRA_COLOR_EDIT, color);

            setResult(RESULT_OK, data);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "Please provide a name and date", Toast.LENGTH_LONG).show();
        }
    }

    private Drawable getImage(int indicator) {
        if (indicator == 0) {
            flag = false;
            return ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_important_notflag);
        } else {
            flag = true;
            return ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_important_flag);
        }
    }

    private void changeFlag() {
        if (flag == false) {
            taskFlag.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_important_flag));
            flag = true;
        } else {
            taskFlag.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_important_notflag));
            flag = false;
        }
    }

    private void showDatePickerDialog() {
        new DatePickerDialog(
                this,
                android.R.style.Theme_DeviceDefault_Dialog,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar.getInstance().set(year, month, day);
                        month++;
                        taskDateEdit.setText(day + "-" + month + "-" + year);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
    }

    private ArrayList getArrayList(String color){
        ArrayList<String> colorImages = new ArrayList<>();
        colorImages.clear();
        switch (color){
            case "#FFFFFF":
                colorImages.add("no color");
                colorImages.add("black");
                colorImages.add("cyan");
                colorImages.add("magenta");
                colorImages.add("yellow");
                break;
            case "#494949":
                colorImages.add("colored");
                colorImages.add("black enable");
                colorImages.add("cyan");
                colorImages.add("magenta");
                colorImages.add("yellow");
                break;
            case "#8EC5D9":
                colorImages.add("colored");
                colorImages.add("black");
                colorImages.add("cyan enable");
                colorImages.add("magenta");
                colorImages.add("yellow");
                break;
            case "#F181C3":
                colorImages.add("colored");
                colorImages.add("black");
                colorImages.add("cyan");
                colorImages.add("magenta enable");
                colorImages.add("yellow");
                break;
            case "#E2E165":
                colorImages.add("colored");
                colorImages.add("black");
                colorImages.add("cyan");
                colorImages.add("magenta");
                colorImages.add("yellow enable");
                break;
        }
        return colorImages;
    }
}