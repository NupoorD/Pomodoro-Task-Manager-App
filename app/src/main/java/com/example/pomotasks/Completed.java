package com.example.pomotasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Completed extends AppCompatActivity {
    private RecyclerView allCompletedTasks;
    private MyDatabaseHelper databaseHelper;
    FloatingActionButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);
        allCompletedTasks = findViewById(R.id.allCompletedTasks);
        home = findViewById(R.id.homeButton);
        databaseHelper = new MyDatabaseHelper(this);
        loadRecords();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Completed.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadRecords() {
        AdapterTwo adapterTwo = new AdapterTwo(Completed.this,databaseHelper.getAllRecords(""));
        allCompletedTasks.setAdapter(adapterTwo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecords();
    }
}