package com.example.notesappnavdesign;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ListDao {
    @Query("Select * FROM ItemList")
    LiveData<List<ItemList>> getAllList();

    @Insert
    void insertList(ItemList itemList);

    @Update
    void updateList(ItemList itemList);

    @Delete
    void deleteList(ItemList itemList);
}
