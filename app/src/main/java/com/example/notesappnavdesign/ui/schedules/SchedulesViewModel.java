package com.example.notesappnavdesign.ui.schedules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SchedulesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SchedulesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}