package com.example.checklist;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.checklist.Entities.CheckItem;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.Helpers.CheckItemsAdapter;
import com.example.checklist.ViewModels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CheckitemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CheckItemsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton add_check_item_fab;
    private SharedViewModel sharedViewModel;
    private Toolbar toolbar;
    private EditText add_check_item_name;
    private CheckList currentCheckList;
    private int currentCheckListId;
    private List<CheckItem> pureCheckItemsById;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkitems);

        add_check_item_fab = findViewById(R.id.add_check_item_fab);
        add_check_item_name = findViewById(R.id.add_check_item_name);

        sharedViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(SharedViewModel.class);


        recyclerView = findViewById(R.id.checkitem_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // nuo cia adapteri perkelus i onchanged, is naujo sukurs lista ir pakeis dydi. reiskia reikia pakeisti dydi ten.
        mAdapter = new CheckItemsAdapter();
        mAdapter.setContext(this);
        recyclerView.setAdapter(mAdapter);
        //get object from MainActgivity.java
        currentCheckList = (CheckList) getIntent().getSerializableExtra("On_Clicked_Check_List");

        //get id to use in this activity
        currentCheckListId = currentCheckList.getId();

        //set checklist inside sharedviewmodel to manipulate it inside sharedviewmodel
        sharedViewModel.setCheckList(currentCheckList);

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sharedViewModel.getCurrentCheckListById(currentCheckListId).observe(this, new Observer<CheckList>() {
            @Override
            public void onChanged(CheckList checkList) {

                toolbar.setTitle(checkList.getName());

                //TODO. mini info for numbers set done/todo


            }
        });


        sharedViewModel.getCheckListItems(currentCheckListId).observe(this, new Observer<List<CheckItem>>() {
            @Override
            public void onChanged(List<CheckItem> list) {
                mAdapter.submitList(list);
                //padeda is dugno bet ne is originalaus 0. t.y., pridejus nauja item, 0 pzoicijoje,nepasislenka i virsu (new 0);
                //recyclerView.smoothScrollToPosition(0);

            }
        });


        add_check_item_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkItemName = add_check_item_name.getText().toString();

                if (checkItemName == ""|| checkItemName.isEmpty()) {
                    Toast.makeText(getApplicationContext() , "Check item name is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    CheckItem currentCheckItem = new CheckItem(checkItemName, currentCheckListId);
                    sharedViewModel.insertCheckItem(currentCheckItem);
                    sharedViewModel.incrementCheckListItemsToDo();
                    //mAdapter.notifyItemInserted(0); padeda i 0 pozicija patekti, taciau buginasi ir su praktiškai su kiekvienu action'u refreshina.
                    add_check_item_name.getText().clear();
                    Toast.makeText(getApplicationContext() , "Check Item Added", Toast.LENGTH_SHORT).show();


                }

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
                sharedViewModel.deleteCheckItem(mAdapter.getCurrentCheckItem(viewHolder.getAdapterPosition()));
                sharedViewModel.decrementCheckListItemsToDo();
                Toast.makeText(getApplicationContext(), "CheckItem deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);



        mAdapter.setOnCheckBoxListener(new CheckItemsAdapter.CheckBoxListener() {
            @Override
            public void onCheckBoxClick(CheckItem checkItem) {
                if (checkItem.getDone() == false) {
                    checkItem.setDone(true);
                    sharedViewModel.incrementCheckListItemsDone();
                } else { checkItem.setDone(false);
                    sharedViewModel.decrementCheckListItemsDone();

                }
                sharedViewModel.updateCheckItem(checkItem);
                sharedViewModel.updateCheckList(currentCheckList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_items_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //TODO. delete all + error message popup
        switch (item.getItemId()) {
            case R.id.uncheck_all:
                List<CheckItem> listToUncheck = sharedViewModel.getAllPureCheckItemsById(currentCheckListId);
                //TODO. straight from ROOM? no for loop. check/uncheck all.
                for (CheckItem checkItem : listToUncheck) {
                    if (checkItem.getDone()== true) {
                        checkItem.setDone(false);
                        sharedViewModel.decrementCheckListItemsDone();
                        sharedViewModel.updateCheckItem(checkItem);
                    }
                    
                } break;
        }
        return super.onOptionsItemSelected(item);
    }
}