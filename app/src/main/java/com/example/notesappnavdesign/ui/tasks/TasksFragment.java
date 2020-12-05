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
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;
import java.util.Objects;

public class TasksFragment extends Fragment {
    public static final int CREATE_TASK_REQUEST = 1;

    private AllViewModel allViewModel;
    private ConstraintLayout constraintLayout;
    private AppBarLayout appBarLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allViewModel =
                ViewModelProviders.of(this).get(AllViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_tasks, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.tasksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AllTaskAdapter adapterTask = new AllTaskAdapter();
        recyclerView.setAdapter(adapterTask);

        allViewModel.getAllTask().observe(getViewLifecycleOwner(), new Observer<List<ItemTask>>() {
            @Override
            public void onChanged(List<ItemTask> itemTasks) {
                adapterTask.setAllItemTask(itemTasks);
                if (Objects.requireNonNull(recyclerView.getLayoutManager()).getItemCount() == 0) {
                    root.findViewById(R.id.noTasks).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.noTasksText).setVisibility(View.VISIBLE);
                }
            }
        });

        Toolbar toolbar = root.findViewById(R.id.toolbarTask);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        SharedPreferences preferences = requireActivity().getSharedPreferences("My prefs", Context.MODE_PRIVATE);
        int colorID = preferences.getInt("Color ID", 0);

        constraintLayout = root.findViewById(R.id.allTasks_layout);
        appBarLayout = root.findViewById(R.id.appBarTasks);

        constraintLayout.setBackgroundColor(colorID);
        appBarLayout.setBackgroundColor(colorID);

        adapterTask.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemTask itemTask) {
                SharedPreferences.Editor editor = requireActivity().getSharedPreferences("My prefs", Context.MODE_PRIVATE).edit();
                editor.putInt("Task ID", itemTask.gettId());
                editor.putString("Task Name", itemTask.getTaskName());
                editor.putString("Task Description", itemTask.getTaskDesc());
                editor.putString("Task Date", itemTask.getTaskDate());
                editor.putInt("Task Importance", itemTask.getTaskImportance());
                editor.putString("Task Color", itemTask.getTaskColor());
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
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.create_nav_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navigation_add) {
            createTask();
        }
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_TASK_REQUEST && resultCode == Activity.RESULT_OK) {
            assert data != null;
            String taskName = data.getStringExtra(TaskCreate.EXTRA_NAME);
            String description = data.getStringExtra(TaskCreate.EXTRA_DESCRIPTION);
            String taskDate = data.getStringExtra(TaskCreate.EXTRA_DATE);
            int importance = data.getIntExtra(TaskCreate.EXTRA_FLAG, '0');
            String color = data.getStringExtra(TaskCreate.EXTRA_COLOR);

            ItemTask itemTask = new ItemTask(taskName, description, taskDate, importance, color);
            allViewModel.insertTask(itemTask);

            requireView().findViewById(R.id.noTasks).setVisibility(View.INVISIBLE);
            requireView().findViewById(R.id.noTasksText).setVisibility(View.INVISIBLE);

            Toast.makeText(getActivity(), "Successfully created new task", Toast.LENGTH_LONG).show();
        }
    }
}