package com.example.checklist.DB;
import  android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.checklist.Entities.CheckItem;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.MainActivity;
import com.example.checklist.Services.CheckItemService;
import com.example.checklist.Services.CheckListService;

@Database(entities = {CheckItem.class, CheckList.class}, version = 1, exportSchema = false)

// one class to rule them all, can't be instantiated, singleton, uses only this one and no copies.
public abstract class CheckitemDatabase extends RoomDatabase {
    private static CheckitemDatabase checkitemDatabase;
    private static String DBNAME = "Checkitemdb";
    // one database on one thread. If not sync, could be created multiple DBs on different threads.
    public synchronized static CheckitemDatabase getInstance(Context context) {
        //condition
        if (checkitemDatabase == null) {
            // could it be that builder is used when NEW object can not be created?
            checkitemDatabase = Room.databaseBuilder(context.getApplicationContext(), CheckitemDatabase.class,DBNAME).fallbackToDestructiveMigration().build();
        //.allowMainThreadQueries()
            //TODO. Initial DB on first run with example checklist/items
        }
        return checkitemDatabase;
    }
    //body is taken care of by Room; Similar to Spring, but in Spring pačiam reikėjo implementuotis. čia nereik.
    public abstract CheckItemService checkItemService();
    public abstract CheckListService checkListService();

}
