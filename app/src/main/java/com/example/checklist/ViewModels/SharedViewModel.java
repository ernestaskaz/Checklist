package com.example.checklist.ViewModels;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.checklist.Entities.CheckItem;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.Repositories.CheckItemRepository;
import com.example.checklist.Repositories.CheckListRepository;

import java.util.List;

public class SharedViewModel extends AndroidViewModel {
    private CheckListRepository checkListRepository;
    private CheckItemRepository checkItemRepository;

    private LiveData<List<CheckList>> allCheckLists;
    private LiveData<List<CheckItem>> checkListCheckItemsList;
    private LiveData <List<CheckItem>> checkListItems;
    private LiveData<CheckList> currentCheckList;
    private List<CheckList> allPureCheckLists;
    private List<CheckItem> allPureCheckItemsById;
    private CheckList editableCheckList;

 //TODO. rename checklistbyid purechecklistbyid  - aiškumui.
    public SharedViewModel(@NonNull Application application) {
        super(application);
        checkListRepository = new CheckListRepository(application);
        checkItemRepository = new CheckItemRepository(application);
        allCheckLists = checkListRepository.getAllCheckLists();
        checkListCheckItemsList = checkItemRepository.getAllCheckItems();


    }
    //CheckItem VM methods
    public void insertCheckItem(CheckItem checkItem) {
        checkItemRepository.insert(checkItem);
    }
    public void DONTresetAllCheckItems(List<CheckItem> list) {
        checkItemRepository.reset(list);

    }
    public void updateCheckItemList(List<CheckItem> checkItems) {
        checkItemRepository.updateCheckItemList(checkItems);
    }
    public void updateCheckItem(CheckItem checkItem) {
        checkItemRepository.update(checkItem);
    }
    public void deleteCheckItem(CheckItem checkItem) {
        checkItemRepository.delete(checkItem);
    }
    public LiveData<List<CheckItem>> getAllCheckItems() {
        return checkListCheckItemsList;
    }

    // every checklist has its own checkitems; these are live items for adapter;
    public LiveData<List<CheckItem>> getCheckListItems(int id) {
        checkListItems = checkItemRepository.getCheckListItems(id);
        return checkListItems;
    }

    // every checklist has its own checkitems; these are standart("pure") items to manipulate;
    public List<CheckItem> getAllPureCheckItemsById(int id) {
        allPureCheckItemsById = checkItemRepository.getPureCheckItemsByListId(id);
        return allPureCheckItemsById;
    }

    //CheckList WM methods
    public LiveData<CheckList> getCurrentCheckListById(int id) {
        currentCheckList = checkListRepository.getCurrentCheckListById(id);
        return currentCheckList;
    }
    public void insertCheckList(CheckList checkList) {
        checkListRepository.insert(checkList);
    }
    public void updateCheckList(CheckList checkList) {
        checkListRepository.update(checkList);
    }
    public void deleteCheckList(CheckList checkList) {
        checkListRepository.delete(checkList);
    }
    public LiveData<List<CheckList>> getAllCheckLists() {
        return allCheckLists;
    }
    public void DONTresetAllCheckLists(List<CheckList> checklist) {
        checkListRepository.reset(checklist);
    }
    //sets local checklist to manipulate here with increment/decrement
    public void setCheckList(CheckList checkList) {
        this.editableCheckList = checkList;
    }

    // normal("pure") checklists to manipulate; again, efficiency question, thus, not deleted but not used;
    public List<CheckList> getAllPureCheckLists() {
        //TODO. perkelti į konstruktorių, kad necallintų kiekvieną kartą? arba kai bus perkelta į callback async task, atskiras metodas. poto sukurti getteri
        allPureCheckLists = checkListRepository.getAllPureCheckLists();
        return allPureCheckLists;

    }
    //helper methods to update CheckList object when checkitem is manipuilated.
    //increments CheckList items that are checked
    public void incrementCheckListItemsDone() {
        int currentItemsDone = editableCheckList.getItemsDone();
        int incrementedItemsDone = currentItemsDone + 1;
        editableCheckList.setItemsDone(incrementedItemsDone);
        updateCheckList(editableCheckList);

    }
    //dencrements CheckList items that are checked
    public void decrementCheckListItemsDone() {
        int currentItemsDone = editableCheckList.getItemsDone();
        int decrementedItemsDone = currentItemsDone - 1;
        editableCheckList.setItemsDone(decrementedItemsDone);
        updateCheckList(editableCheckList);





    }
    //increments CheckList ALL items
    public void incrementCheckListItemsToDo() {
        int currentItemsToDo = editableCheckList.getItemsToDo();
        int incrementedItemsToDo = currentItemsToDo + 1;
        editableCheckList.setItemsToDo(incrementedItemsToDo);
        updateCheckList(editableCheckList);

    }
    //decrements CheckList ALL items
    public void decrementCheckListItemsToDo() {
        int currentItemsToDo = editableCheckList.getItemsToDo();
        int decrementedItemsToDo = currentItemsToDo - 1;
        editableCheckList.setItemsToDo(decrementedItemsToDo);
        updateCheckList(editableCheckList);

    }
}
