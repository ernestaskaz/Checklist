package com.example.checklist.Services;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.checklist.Entities.CheckItem;
import com.example.checklist.Entities.CheckList;

import java.util.List;

@Dao
public interface CheckItemService {

    @Insert(onConflict = REPLACE)
    void insert(CheckItem checkItem);
    @Delete
    void delete(CheckItem checkItem);
    @Delete
    void reset(List<CheckItem> checkItems);
    @Update
    void update(CheckItem checkItem);

    @Query("SELECT * FROM all_checkitems")
    LiveData<List<CheckItem>> getAll();

    @Query("SELECT * FROM all_checkitems WHERE checklistId =:checklistId ORDER BY id DESC")
    LiveData<List<CheckItem>> getCheckListItems(int checklistId);

    @Query("SELECT * FROM all_checkitems WHERE id = :id")
    CheckItem getById(int id);

    @Query("DELETE FROM all_checkitems WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM all_checkitems WHERE checklistId =:checklistId")
    List<CheckItem> getPureCheckItemsByListId(int checklistId);
    @Update
    void updateCheckItemList(List<CheckItem> checkItemList);


}
