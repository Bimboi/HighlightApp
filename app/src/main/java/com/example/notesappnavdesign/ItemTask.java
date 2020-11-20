package com.example.notesappnavdesign;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemTask {
    public ItemTask(String taskName, String taskDesc, String taskDate, int taskImportance) {
        this.taskName = taskName;
        this.taskDesc = taskDesc;
        this.taskDate = taskDate;
        this.taskImportance = taskImportance;
    }

    @PrimaryKey(autoGenerate = true)
    public int tId;

    @ColumnInfo(name = "task_Name")
    private String taskName;

    @ColumnInfo(name = "task_Desc")
    private String taskDesc;

    @ColumnInfo(name = "task_Date")
    private String taskDate;

    @ColumnInfo(name = "task_Importance")
    private int taskImportance;

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public int getTaskImportance() { return taskImportance; }

    public void setTaskImportance(int taskImportance) { this.taskImportance = taskImportance; }
}
