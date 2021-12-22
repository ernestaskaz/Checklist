package com.example.checklist.Services;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.checklist.Entities.CheckItem;

import java.util.List;

@Dao
//because Room auto generates method bodies. no need for implementation as in Spring project.
//Why is reference reversed between DB and DAO? Spring DAO references repo.
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
    // LiveData reaguoja į bazės pakeitimus ("stebi ją") ir iškart siunčia atnaujintą informaciją į activity/ies.
    @Query("SELECT * FROM all_checkitems WHERE checklistId =:checklistId ORDER BY item_is_done ASC, id DESC")
    LiveData<List<CheckItem>> getCheckListItems(int checklistId);
    @Query("SELECT * FROM all_checkitems WHERE checklistId =:checklistId")
    List<CheckItem> getPureCheckItemsByListId(int checklistId);
    @Update
    void updateCheckItemList(List<CheckItem> checkItemList);
    //TODO. Whats more efficient - update each item in For Loop (checkitemsactivity) or create List and update whole list at once? Speed? Logic? Readability?


}
