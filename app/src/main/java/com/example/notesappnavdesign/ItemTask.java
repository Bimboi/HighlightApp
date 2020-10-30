package com.example.notesappnavdesign;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemTask {
    public ItemTask(String taskName, String taskDesc) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
    }

    @PrimaryKey(autoGenerate = true)
    public int tId;

    @ColumnInfo(name = "task_Name")
    private String taskName;

    @ColumnInfo(name = "task_Desc")
    private String taskDesc;

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String listName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }
}
