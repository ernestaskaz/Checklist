package com.example.checklist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.Helpers.ListOfCheckListsAdapter;
import com.example.checklist.ViewModels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListOfCheckListsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addNewCheckListFab;
    private SharedViewModel sharedViewModel;
    private Toolbar toolbar;
    private List<CheckList> getPureCheckLists;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewCheckListFab = findViewById(R.id.add_check_list_fab);

//recycle view init
        recyclerView = findViewById(R.id.checklist_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ListOfCheckListsAdapter();
        //mAdapter.setContext(this);
        recyclerView.setAdapter(mAdapter);
//toolbar init
        toolbar = findViewById(R.id.home_toolbar);
        toolbar.setTitle("Your Check Lists");
        setSupportActionBar(toolbar);
        getSupportActionBar();
//viewmodel init
        sharedViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(SharedViewModel.class);
        sharedViewModel.getAllCheckLists().observe(this, new Observer<List<CheckList>>() {
            @Override
            public void onChanged(List<CheckList> list) {
                mAdapter.setListOfCheckLists(list);


            }
        });

        //add new checklist
        addNewCheckListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCheckListActivity.class);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                sharedViewModel.deleteCheckList(mAdapter.getCurrentCheckList(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "CheckList deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //TODO. hour receiver alarm timer == hours/days




        mAdapter.setOnItemClickListener(new ListOfCheckListsAdapter.OnCheckListListener() {
            @Override
            public void onCheckListClick(CheckList checkList) {

                //send object over;
                Intent onClickList = new Intent(MainActivity.this, CheckitemsActivity.class);
                onClickList.putExtra("On_Clicked_Check_List", checkList);
                startActivity(onClickList);

            }
        });


    }
}