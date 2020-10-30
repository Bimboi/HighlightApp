package com.example.notesappnavdesign;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("Select * FROM ItemTask")
    LiveData<List<ItemTask>> getAllTask();

    @Query("DELETE FROM itemtask WHERE tId=:id")
    void deleteById(int id);

    @Insert
    void insertTask(ItemTask itemTask);

    @Update
    void updateTask(ItemTask itemTask);

    @Delete
    void deleteTask(ItemTask itemTask);
}
