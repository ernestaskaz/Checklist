package com.example.checklist;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.checklist.Entities.CheckList;
import com.example.checklist.ViewModels.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class AddCheckListActivity extends AppCompatActivity {

    private EditText addCheckListName;
    private FloatingActionButton saveCheckListFab;
    private SharedViewModel sharedViewModel;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_list);

        sharedViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(SharedViewModel.class);

        addCheckListName = findViewById(R.id.add_check_list_name);
        saveCheckListFab = findViewById(R.id.add_check_list_done_fab);

        toolbar = findViewById(R.id.home_toolbar);
        toolbar.setTitle("Add New Check List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveCheckListFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String checkListName = addCheckListName.getText().toString();
                CheckList currentCheckList = new CheckList(checkListName);
                sharedViewModel.insertCheckList(currentCheckList);
                Intent intent = new Intent(AddCheckListActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(AddCheckListActivity.this, "Check List Added", Toast.LENGTH_SHORT).show();

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}