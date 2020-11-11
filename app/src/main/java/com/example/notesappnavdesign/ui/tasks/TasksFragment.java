package com.example.notesappnavdesign.ui.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.notesappnavdesign.AllTaskAdapter;
import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.AllViewModel;
import com.example.notesappnavdesign.ui.taskItem.TaskCreate;
import com.example.notesappnavdesign.ui.taskItem.TaskView;

import java.util.List;

public class TasksFragment extends Fragment {
    public static final int CREATE_TASK_REQUEST = 1;

    private AllViewModel allViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allViewModel =
                ViewModelProviders.of(this).get(AllViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AllTaskAdapter adapterTask = new AllTaskAdapter();
        recyclerView.setAdapter(adapterTask);

        allViewModel.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<ItemTask>>() {
            @Override
            public void onChanged(List<ItemTask> itemTasks) {
                adapterTask.setAllItemTask(itemTasks);
            }
        });

        Toolbar toolbar = root.findViewById(R.id.toolbarTask);
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
                startActivity(new Intent(getActivity(), TaskView.class));
            }
        });
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
            allViewModel.insertTask(itemTask);
            Toast.makeText(getActivity(), "Successfully created new task", Toast.LENGTH_LONG).show();
        }
    }
}