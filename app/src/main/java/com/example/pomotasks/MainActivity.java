package com.example.pomotasks;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addNewTasksButton;
    FloatingActionButton seeCompletedTasks;
    MyDatabaseHelper myDB;
    ArrayList<String> task_id, task_name,task_status;
    CustomAdapter customAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.TasksList);
        addNewTasksButton = findViewById(R.id.addNewTaskButton);
        seeCompletedTasks = findViewById(R.id.seeCompletedTasks);
        addNewTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        seeCompletedTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Completed.class);
                startActivity(intent);
            }
        });
        myDB = new MyDatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_name = new ArrayList<>();
        task_status = new ArrayList<>();

        displayTasks();
        customAdapter = new CustomAdapter(MainActivity.this, this,task_id,task_name,task_status);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager((MainActivity.this)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            recreate();
        }
    }

    void displayTasks(){
        Cursor cursor = myDB.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "Add New Tasks to Get Started", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                task_id.add(cursor.getString(0));
                task_name.add(cursor.getString(1));
                task_status.add(cursor.getString(2));
            }
        }
    }
}