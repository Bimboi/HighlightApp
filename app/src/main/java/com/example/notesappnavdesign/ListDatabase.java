package com.example.notesappnavdesign;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ItemList.class},version = 1)
public abstract class ListDatabase extends RoomDatabase {
    public abstract ListDao getListDao();
    private static volatile ListDatabase listDB;
    static synchronized ListDatabase getInstance(Context context) {
        if (listDB == null) {
            listDB = buildDatabaseInstance(context);
        }
        return listDB;
    }

    private static ListDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                ListDatabase.class,
                "List Items")
                .fallbackToDestructiveMigration()
                .build();
    }
}
