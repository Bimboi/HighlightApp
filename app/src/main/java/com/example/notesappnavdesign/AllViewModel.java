package com.example.notesappnavdesign;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AllViewModel extends AndroidViewModel {

    Repository taskRepository;
    LiveData<List<ItemTask>> allTask, allToday;

    public AllViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new Repository(application);
        allTask = taskRepository.getAllTask();
        allToday = taskRepository.getAllToday();
    }

    public LiveData<List<ItemTask>> getAllTask(){
        return taskRepository.getAllTask();
    }

    public LiveData<List<ItemTask>> getAllToday(){
        return taskRepository.getAllToday();
    }

    public void insertTask(ItemTask itemTask){
        taskRepository.insertTask(itemTask);
    }

    public void updateTask(ItemTask itemTask){
        taskRepository.updateTask(itemTask);
    }

    public void deleteTaskById(Integer id) { taskRepository.deleteTaskById(id); }
}