package com.example.notesappnavdesign.ui.tasks;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.Repository;

import java.util.List;

public class TasksViewModel extends AndroidViewModel {

    Repository taskRepository;
    LiveData<List<ItemTask>> allList;

    public TasksViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new Repository(application);
        allList = taskRepository.getAllTask();
    }

    public LiveData<List<ItemTask>> getAllTask(){
        return taskRepository.getAllTask();
    }

    public void insertTask(ItemTask itemTask){
        taskRepository.insertList(itemTask);
    }

    public void updateTask(ItemTask itemTask){
        taskRepository.updateList(itemTask);
    }

    public void deleteTask(ItemTask itemTask){
        taskRepository.deleteList(itemTask);
    }
}