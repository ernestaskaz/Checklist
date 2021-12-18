package com.example.checklist.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity(tableName = "all_checklists")
public class CheckList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "list_name")
    private String name;
    @ColumnInfo(name = "items_done")
    private int itemsDone;
    @ColumnInfo(name = "items_to_do")
    private int itemsToDo;
    @ColumnInfo(name = "list_type")
    private boolean isDaily;


    public CheckList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getItemsDone() {
        return itemsDone;
    }

    public void setItemsDone(int itemsDone) {
        this.itemsDone = itemsDone;
    }

    public int getItemsToDo() {
        return itemsToDo;
    }

    public void setItemsToDo(int itemsToDo) {
        this.itemsToDo = itemsToDo;
    }

    public boolean isDaily() {
        return isDaily;
    }

    public void setDaily(boolean daily) {
        isDaily = daily;
    }

    @Override
    public String toString() {
        return "CheckList{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemsDone=" + itemsDone +
                ", itemsToDo=" + itemsToDo +
                ", isDaily=" + isDaily +
                '}';
    }
}
