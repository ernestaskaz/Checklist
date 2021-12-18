package com.example.checklist.Services;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
    //@Query("UPDATE all_checklists SET list_name = :text WHERE ID = :id")
    @Update
    void update(CheckList checkList);
    @Query("SELECT * FROM all_checklists")
    LiveData<List<CheckList>> getAll();
    // List<CheckList> getAll();
    @Query("SELECT * FROM all_checklists WHERE id = :id")
    LiveData<CheckList>  getById(int id);

    @Query("DELETE FROM all_checklists WHERE id = :id")
    void deleteById(int id);
    @Query("SELECT * FROM all_checklists")
    List<CheckList> getAllPureCheckLists();
    @Query("SELECT * FROM all_checklists")
    List<CheckList> getAllSync();
}
