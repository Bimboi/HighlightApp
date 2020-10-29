package com.example.notesappnavdesign;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemTask {
    public ItemTask(String taskName) {
        this.taskName = taskName;
    }

    @PrimaryKey(autoGenerate = true)
    public int lid;

    @ColumnInfo(name = "task_Name")
    private String taskName;

    public int getLid() {
        return lid;
    }

    public void setLid(int uid) {
        this.lid = lid;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String listName) {
        this.taskName = taskName;
    }
}
