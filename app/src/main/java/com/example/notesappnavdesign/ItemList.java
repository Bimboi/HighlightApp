package com.example.notesappnavdesign;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemList {
    public ItemList(String listName) {
        this.listName = listName;
    }

    @PrimaryKey(autoGenerate = true)
    public int lid;

    @ColumnInfo(name = "list_Name")
    private String listName;

    public int getLid() {
        return lid;
    }

    public void setLid(int uid) {
        this.lid = lid;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }
}
