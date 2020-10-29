package com.example.notesappnavdesign;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ItemTask.class},version = 1)
public abstract class TasksDatabase extends RoomDatabase {
    public abstract TaskDao getTaskDao();
    private static volatile TasksDatabase appDB;
    static synchronized TasksDatabase getInstance(Context context) {
        if (appDB == null) {
            appDB = buildDatabaseInstance(context);
        }
        return appDB;
    }

    private static TasksDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                TasksDatabase.class,
                "Task Items")
                .fallbackToDestructiveMigration()
                .build();
    }
}
