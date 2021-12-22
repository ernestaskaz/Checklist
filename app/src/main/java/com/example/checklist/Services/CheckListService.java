package com.example.checklist.Services;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.checklist.Entities.CheckList;

import java.util.List;

@Dao
public interface CheckListService {
    @Insert(onConflict = REPLACE)
    void insert(CheckList checkList);
    @Delete
    void delete(CheckList checkList);
    @Delete
    void reset(List<CheckList> checklists);
    @Update
    void update(CheckList checkList);
    @Query("SELECT * FROM all_checklists")
    LiveData<List<CheckList>> getAll();
    @Query("SELECT * FROM all_checklists WHERE id = :id")
    LiveData<CheckList>  getById(int id);
    @Query("SELECT * FROM all_checklists")
    // non live data.
    List<CheckList> getAllPureCheckLists();
    //standart data so it can be edited.
    @Query("SELECT * FROM all_checklists")
    List<CheckList> getAllSync();
    //TODO. what's more efficient - get all lists and then get by id from the list or create new method and call it
}
