package com.example.notesappnavdesign.ui.lists;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesappnavdesign.ItemList;
import com.example.notesappnavdesign.Repository;

import java.util.List;

public class ListsViewModel extends AndroidViewModel {

    Repository listRepository;
    LiveData<List<ItemList>> allList;

    public ListsViewModel(@NonNull Application application) {
        super(application);
        listRepository = new Repository(application);
        allList = listRepository.getAllList();
    }

    public LiveData<List<ItemList>> getAllList(){
        return listRepository.getAllList();
    }

    public void insertList(ItemList itemList){
        listRepository.insertList(itemList);
    }

    public void updateList(ItemList itemList){
        listRepository.updateList(itemList);
    }

    public void deleteList(ItemList itemList){
        listRepository.deleteList(itemList);
    }
}