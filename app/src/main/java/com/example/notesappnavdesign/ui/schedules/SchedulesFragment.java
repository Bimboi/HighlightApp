package com.example.notesappnavdesign.ui.schedules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.notesappnavdesign.R;

public class SchedulesFragment extends androidx.fragment.app.Fragment {

    private SchedulesViewModel schedulesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        schedulesViewModel =
                ViewModelProviders.of(this).get(SchedulesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_schedules, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        schedulesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}