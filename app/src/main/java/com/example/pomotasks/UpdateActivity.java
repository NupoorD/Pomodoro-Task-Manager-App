package com.example.pomotasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    TextView newTaskNameInput;
    FloatingActionButton updateTask;
    FloatingActionButton startPomo;
    FloatingActionButton completedTask;
    String id,name,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        newTaskNameInput = findViewById(R.id.updatedTaskName);
        updateTask = findViewById(R.id.updateTaskButton);
        startPomo = findViewById(R.id.startPomoButton);
        completedTask = findViewById(R.id.taskCompletedButton);
        getAndSetIntentData();
        updateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateData(id,name,status);
            }
        });

        startPomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, TimerActivity.class);
                intent.putExtra("taskName",name);
                startActivity(intent);
            }
        });

        completedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateActivity.this, OnCompleteActivity.class);
                intent.putExtra("taskId",id);
                startActivity(intent);
            }
        });
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("nm") && getIntent().hasExtra("st")){
           id = getIntent().getStringExtra("id");
           name = getIntent().getStringExtra("nm");
           status = getIntent().getStringExtra("st");

           newTaskNameInput.setText(name);
        }else{
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
}