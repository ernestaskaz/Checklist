package com.example.checklist.Repositories;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.checklist.DB.CheckitemDatabase;
import com.example.checklist.Entities.CheckItem;
import com.example.checklist.Services.CheckItemService;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckItemRepository {

    private CheckItemService checkItemService;
    private LiveData<List<CheckItem>> checkListCheckItemsList;
    private LiveData<List<CheckItem>> checkListItems;
    private List<CheckItem> getAllPureCheckItemsByListId;

    public CheckItemRepository(Application application) {
        CheckitemDatabase checkitemDatabase = CheckitemDatabase.getInstance(application);
        checkItemService = checkitemDatabase.checkItemService();
        checkListCheckItemsList = checkItemService.getAll();
    }

    public void insert(CheckItem checkItem) {
        new CheckItemRepository.InsertCheckItemAsyncTask(checkItemService).execute(checkItem);


    }

    public void updateCheckItemList(List<CheckItem> checkItemList) {
        new CheckItemRepository.UpdateCheckItemListAsyncTask(checkItemService).execute(checkItemList);
    }

    public void delete(CheckItem checkItem) {
        new CheckItemRepository.DeleteCheckItemAsyncTask(checkItemService).execute(checkItem);

    }

    public void update(CheckItem checkItem) {
        new CheckItemRepository.UpdateCheckItemAsyncTask(checkItemService).execute(checkItem);
    }

    public void reset(List<CheckItem> list) {
        new CheckItemRepository.ResetCheckItemsAsyncTask(checkItemService).execute(list);
    }

    public LiveData<List<CheckItem>> getAllCheckItems() {
        return checkListCheckItemsList;
    }

    public LiveData<List<CheckItem>> getCheckListItems(int checkListId) {
        checkListItems = checkItemService.getCheckListItems(checkListId);

        return checkListItems;
    }

    public List<CheckItem> getPureCheckItemsByListId(int checkListId) {
        try {
            getAllPureCheckItemsByListId = new GetAllPureCheckItemsByListIdAsyncTask(checkItemService).execute(checkListId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getAllPureCheckItemsByListId;
    }

    private static class InsertCheckItemAsyncTask extends AsyncTask<CheckItem, Void, Void> {
        private CheckItemService checkItemService;

        private InsertCheckItemAsyncTask(CheckItemService checkItemService) {
            this.checkItemService = checkItemService;
        }


        @Override
        protected Void doInBackground(CheckItem... checkItems) {
            checkItemService.insert(checkItems[0]);
            return null;
        }
    }


    private static class GetAllPureCheckItemsByListIdAsyncTask extends AsyncTask<Integer, Void, List<CheckItem>> {
        private CheckItemService checkItemService;
        List<CheckItem> checkItemsListById;

        private GetAllPureCheckItemsByListIdAsyncTask(CheckItemService checkItemService) {
            this.checkItemService = checkItemService;
        }

        @Override
        protected List<CheckItem> doInBackground(Integer... position) {
            int post = position[0].intValue();
            checkItemsListById = checkItemService.getPureCheckItemsByListId(post);
            return checkItemsListById;
        }

        @Override
        protected void onPostExecute(List<CheckItem> list) {
            super.onPostExecute(list);
        }
    }

    private static class UpdateCheckItemListAsyncTask extends AsyncTask<List<CheckItem>, Void, Void> {
        private CheckItemService checkItemService;

        private UpdateCheckItemListAsyncTask(CheckItemService checkItemService) {
            this.checkItemService = checkItemService;
        }


        @Override
        protected Void doInBackground(List<CheckItem>... lists) {
            checkItemService.updateCheckItemList(lists[0]);
            return null;
        }
    }

    private static class DeleteCheckItemAsyncTask extends AsyncTask<CheckItem, Void, Void> {
        private CheckItemService checkItemService;

        private DeleteCheckItemAsyncTask(CheckItemService checkItemService) {
            this.checkItemService = checkItemService;
        }


        @Override
        protected Void doInBackground(CheckItem... checkItems) {
            checkItemService.delete(checkItems[0]);
            return null;
        }
    }

    private static class UpdateCheckItemAsyncTask extends AsyncTask<CheckItem, Void, Void> {
        private CheckItemService checkItemService;

        private UpdateCheckItemAsyncTask(CheckItemService checkItemService) {
            this.checkItemService = checkItemService;
        }


        @Override
        protected Void doInBackground(CheckItem... checkItems) {
            checkItemService.update(checkItems[0]);
            return null;
        }
    }

    private static class ResetCheckItemsAsyncTask extends AsyncTask<List<CheckItem>, Void, Void> {
        private CheckItemService checkItemService;

        private ResetCheckItemsAsyncTask(CheckItemService checkItemService) {
            this.checkItemService = checkItemService;
        }

        @Override
        protected Void doInBackground(List<CheckItem>... lists) {
            checkItemService.reset(lists[0]);
            return null;
        }
    }
}