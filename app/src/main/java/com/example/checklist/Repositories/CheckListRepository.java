package com.example.checklist.Repositories;
import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import com.example.checklist.DB.CheckitemDatabase;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.Services.CheckListService;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CheckListRepository {
    private CheckListService checkListService;
    private LiveData<List<CheckList>> allCheckLists;
    private LiveData<CheckList> currentCheckList;
    private List<CheckList> listOfListsSync;
    private List<CheckList> allPureCheckLists;

    public CheckListRepository(Application application) {
        CheckitemDatabase checkitemDatabase = CheckitemDatabase.getInstance(application);
        checkListService = checkitemDatabase.checkListService();
        allCheckLists = checkListService.getAll();

    }
    public void insert(CheckList checkList) {
        new InsertCheckListAsyncTask(checkListService).execute(checkList);

    }
    public void delete(CheckList checkList) {
        new DeleteCheckListAsyncTask(checkListService).execute(checkList);

    }

    public void update(CheckList checkList) {
        new UpdateCheckListAsyncTask(checkListService).execute(checkList);
    }

    public void reset(List<CheckList> list) {
        new ResetCheckListsAsyncTask(checkListService).execute(list);
    }

    public LiveData<List<CheckList>> getAllCheckLists() {
        return allCheckLists;
    }
    public LiveData<CheckList> getCurrentCheckListById(int id) {
        currentCheckList = checkListService.getById(id);
        return currentCheckList;

    }
    public List<CheckList> getAllPureCheckLists () {

        try {
            allPureCheckLists = new GetAllPureCheckListsAsyncTask(checkListService).execute().get();
            //TODO. redo GET(). deprecated. Callback with interface? InPRogress splash window? updates everything.updates on broadcast?on done - checklists have been updated!
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return allPureCheckLists;
    }


    // has to be static (does nota access parent data) and no reference to repo. memory leak otherwise? Async task still runs and holds reference to parent class even though it is not neccessary?
    private static class GetAllPureCheckListsAsyncTask extends AsyncTask<Void, Void, List<CheckList>> {
        private CheckListService checkListService;
        List<CheckList> checkList;
        // because class is static, nera galimybes prieiti prie serviso tiesiogiai (Repo), tačiau galima tai padaryti per konstruktorių.
        private GetAllPureCheckListsAsyncTask(CheckListService checkListService) {
            this.checkListService = checkListService;
        }


        @Override
        protected List<CheckList> doInBackground(Void... voids) {
            checkList = checkListService.getAllPureCheckLists();
            return checkList;
        }

        @Override
        protected void onPostExecute(List<CheckList> list) {
            super.onPostExecute(list);
        }
    }

    private static class InsertCheckListAsyncTask extends AsyncTask<CheckList, Void, Void> {
        private CheckListService checkListService;
        //kadangi klasė yra statinė, serviso negalima callint tiesiogiai, turim kurti konstruktorių ir passint.

        private InsertCheckListAsyncTask(CheckListService checkListService) {
            this.checkListService = checkListService;
        }

        @Override
        protected Void doInBackground(CheckList... checkLists) {
            checkListService.insert(checkLists[0]);
            return null;
        }
    }

    private static class DeleteCheckListAsyncTask extends AsyncTask<CheckList, Void, Void> {
        private CheckListService checkListService;


        private DeleteCheckListAsyncTask(CheckListService checkListService) {
            this.checkListService = checkListService;
        }

        @Override
        protected Void doInBackground(CheckList... checkLists) {
            checkListService.delete(checkLists[0]);
            return null;
        }
    }

    private static class UpdateCheckListAsyncTask extends AsyncTask<CheckList, Void, Void> {
        private CheckListService checkListService;
        private UpdateCheckListAsyncTask(CheckListService checkListService) {
            this.checkListService = checkListService;
        }

        @Override
        protected Void doInBackground(CheckList... checkLists) {
            checkListService.update(checkLists[0]);
            return null;
        }
    }

    private static class ResetCheckListsAsyncTask extends AsyncTask<List<CheckList>, Void, Void> {
        private CheckListService checkListService;

        private ResetCheckListsAsyncTask(CheckListService checkListService) {
            this.checkListService = checkListService;
        }

        @Override
        protected Void doInBackground(List<CheckList>... lists) {
            checkListService.reset(lists[0]);
            return null;
        }
    }

}


