package com.example.notesappnavdesign.ui.overdue;

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
import androidx.lifecycle.ViewModelProviders;

import com.example.notesappnavdesign.AllViewModel;
import com.example.notesappnavdesign.R;

public class OverdueFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllViewModel allViewModel = ViewModelProviders.of(this).get(AllViewModel.class);
        View root = inflater.inflate(R.layout.fragment_overdue, container, false);

        //temporary while no code for display
        root.findViewById(R.id.noTaskOverdue).setVisibility(View.VISIBLE);
        root.findViewById(R.id.noTaskOverdueText).setVisibility(View.VISIBLE);

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
