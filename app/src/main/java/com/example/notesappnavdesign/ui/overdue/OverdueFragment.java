package com.example.notesappnavdesign.ui.overdue;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesappnavdesign.AllTaskAdapter;
import com.example.notesappnavdesign.AllViewModel;
import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.R;
import com.example.notesappnavdesign.ui.taskItem.TaskView;

import java.util.List;

public class OverdueFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllViewModel allViewModel = ViewModelProviders.of(this).get(AllViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_overdue, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.overdueRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AllTaskAdapter adapterTask = new AllTaskAdapter();
        recyclerView.setAdapter(adapterTask);

        allViewModel.getAllOverdue().observe(getViewLifecycleOwner(), new Observer<List<ItemTask>>() {
            @Override
            public void onChanged(List<ItemTask> itemTasks) {
                adapterTask.setAllItemTask(itemTasks);
                if(recyclerView.getLayoutManager().getItemCount() == 0){
                    root.findViewById(R.id.noTaskOverdue).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.noTaskOverdueText).setVisibility(View.VISIBLE);
                }
            }
        });

        adapterTask.setOnItemClickListener(new AllTaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ItemTask itemTask) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("My prefs", Context.MODE_PRIVATE).edit();
                editor.putInt("Task ID", itemTask.gettId());
                editor.putString("Task Name", itemTask.getTaskName());
                editor.putString("Task Description", itemTask.getTaskDesc());
                editor.putString("Task Date", itemTask.getTaskDate());
                editor.putInt("Task Importance", itemTask.getTaskImportance());
                editor.apply();
                startActivity(new Intent(getActivity(), TaskView.class));
            }
        });

        Toolbar toolbar = root.findViewById(R.id.toolbarOverdue);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        return root;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.option_nav_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
