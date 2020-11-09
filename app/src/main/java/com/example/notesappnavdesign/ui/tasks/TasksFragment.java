package com.example.notesappnavdesign.ui.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.ui.taskItem.TaskCreate;
import com.example.notesappnavdesign.ui.taskItem.TaskEdit;
import com.example.notesappnavdesign.ui.taskItem.TaskView;

import java.util.List;

public class TasksFragment extends Fragment {
    public static final int CREATE_TASK_REQUEST = 1;
//    public static final int EDIT_TASK_REQUEST = 2;

    private TasksViewModel tasksViewModel;
    private RecyclerView recyclerView;
    private Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tasksViewModel =
                ViewModelProviders.of(this).get(TasksViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerView = root.findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AllTaskAdapter adapterTask = new AllTaskAdapter();
        recyclerView.setAdapter(adapterTask);

        tasksViewModel.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<ItemTask>>() {
            @Override
            public void onChanged(List<ItemTask> itemTasks) {
                adapterTask.setAllItemTask(itemTasks);
            }
        });
        toolbar = root.findViewById(R.id.toolbarTask);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        adapterTask.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemTask itemTask) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("My prefs", Context.MODE_PRIVATE).edit();
                editor.putInt("Task ID", itemTask.gettId());
                editor.putString("Task Name", itemTask.getTaskName());
                editor.putString("Task Description", itemTask.getTaskDesc());
                editor.putString("Task Date", itemTask.getTaskDate());
                editor.apply();
//                Intent intent = new Intent(getActivity(), TaskView.class);
//                intent.putExtra("Task ID", itemTask.gettId());
//                intent.putExtra("Task Name", itemTask.getTaskName());
//                intent.putExtra("Task Description", itemTask.getTaskDesc());
                startActivity(new Intent(getActivity(),TaskView.class));
            }
        });

//        final TextView textView = root.findViewById(R.id.text_home);
//        listsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    public void createTask() {
        startActivityForResult(new Intent(getActivity(), TaskCreate.class), CREATE_TASK_REQUEST);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.create_nav_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_add:
                createTask();
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            String taskName = data.getStringExtra(TaskCreate.EXTRA_NAME);
            String description = data.getStringExtra(TaskCreate.EXTRA_DESCRIPTION);
            String taskDate = data.getStringExtra(TaskCreate.EXTRA_DATE);

            ItemTask itemTask = new ItemTask(taskName, description, taskDate);
            tasksViewModel.insertTask(itemTask);
            Toast.makeText(getActivity(), "Successfully created new task", Toast.LENGTH_LONG).show();
        }
    }
}