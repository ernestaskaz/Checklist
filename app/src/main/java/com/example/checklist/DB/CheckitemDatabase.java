package com.example.checklist.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.checklist.Entities.CheckItem;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.Services.CheckItemService;
import com.example.checklist.Services.CheckListService;

@Database(entities = {CheckItem.class, CheckList.class}, version = 1, exportSchema = false)
public abstract class CheckitemDatabase extends RoomDatabase {
    private static CheckitemDatabase checkitemDatabase;
    private static String DBNAME = "Checkitemdb";

    public synchronized static CheckitemDatabase getInstance(Context context) {
        //condition
        if (checkitemDatabase == null) {
            checkitemDatabase = Room.databaseBuilder(context.getApplicationContext(), CheckitemDatabase.class,DBNAME).fallbackToDestructiveMigration().build();
        //.allowMainThreadQueries() bad practice.
            //TODO. Initial DB on first run?
        }
        return checkitemDatabase;
    }

    public abstract CheckItemService checkItemService();
    public abstract CheckListService checkListService();

}
