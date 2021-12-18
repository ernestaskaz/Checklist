package com.example.checklist.Entities;


import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = CheckList.class, parentColumns = "id", childColumns = "checklistId", onDelete = CASCADE), tableName = "all_checkitems")
public class CheckItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "item_name")
    private String name;
    @ColumnInfo(name = "item_comment")
    private String comment;
    @ColumnInfo(name = "item_is_done")
    private Boolean isDone;
    private int checklistId;

    public CheckItem(String name, int checklistId) {
        this.name = name;
        this.checklistId = checklistId;
        isDone = false;
        comment = "";
    }

    public void setChecklistId(int checklistId) {
        this.checklistId = checklistId;
    }

    public int getChecklistId() {
        return checklistId;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    @Override
    public String toString() {
        return "CheckItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", isDone=" + isDone +
                ", checklistId=" + checklistId +
                '}';
    }
}
