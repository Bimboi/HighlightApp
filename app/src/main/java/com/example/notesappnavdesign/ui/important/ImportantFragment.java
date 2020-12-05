package com.example.notesappnavdesign.ui.important;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.material.appbar.AppBarLayout;

import java.util.List;
import java.util.Objects;

public class ImportantFragment extends Fragment {

    private ConstraintLayout constraintLayout;
    private AppBarLayout appBarLayout;
    private SharedPreferences.Editor editor;
    private int colorID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllViewModel allViewModel = ViewModelProviders.of(this).get(AllViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_important, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.importantRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final AllTaskAdapter adapterTask = new AllTaskAdapter();
        recyclerView.setAdapter(adapterTask);

        allViewModel.getAllImportant().observe(getViewLifecycleOwner(), new Observer<List<ItemTask>>() {
            @Override
            public void onChanged(List<ItemTask> itemTasks) {
                adapterTask.setAllItemTask(itemTasks);
                if(Objects.requireNonNull(recyclerView.getLayoutManager()).getItemCount() == 0){
                    root.findViewById(R.id.noTaskImportant).setVisibility(View.VISIBLE);
                    root.findViewById(R.id.noTaskImportantText).setVisibility(View.VISIBLE);
                }
            }
        });

        Toolbar toolbar = root.findViewById(R.id.toolbarImportant);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        SharedPreferences preferences = requireActivity().getSharedPreferences("My prefs", Context.MODE_PRIVATE);
        colorID = preferences.getInt("Color ID", 0);

        constraintLayout = root.findViewById(R.id.important_layout);
        appBarLayout = root.findViewById(R.id.appBarImportant);

        constraintLayout.setBackgroundColor(colorID);
        appBarLayout.setBackgroundColor(colorID);

        editor = requireActivity().getSharedPreferences("My prefs", Context.MODE_PRIVATE).edit();

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.option_nav_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int lightGreyID = Color.parseColor("#666666");
        int whiteID = Color.parseColor("#FFFFFF");

        if (item.getItemId() == R.id.navigation_bg_color) {
            if (item.getTitle().equals("Switch Dark Mode")) {
                constraintLayout.setBackgroundColor(lightGreyID);
                appBarLayout.setBackgroundColor(lightGreyID);
                editor.putInt("Color ID", lightGreyID);
                editor.apply();
                colorID = lightGreyID;
            } else {
                constraintLayout.setBackgroundColor(whiteID);
                appBarLayout.setBackgroundColor(whiteID);
                editor.putInt("Color ID", whiteID);
                editor.apply();
                colorID = whiteID;
            }
        }
        return false;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(colorID == Color.parseColor("#666666")){
            menu.findItem(R.id.navigation_bg_color).setTitle("Switch Light Mode");
        }else{
            menu.findItem(R.id.navigation_bg_color).setTitle("Switch Dark Mode");
        }
    }
}
