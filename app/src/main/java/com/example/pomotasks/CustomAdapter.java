package com.example.pomotasks;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.myViewHolder> {
    private Context context;
    private ArrayList task_id, task_name, task_status;
    Activity activity;
    int position;
    CustomAdapter(Activity activity, Context context, ArrayList task_id, ArrayList task_name, ArrayList task_status){
        this.activity=activity;
        this.context=context;
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_status = task_status;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        this.position = position;
        holder.taskNameText.setText(String.valueOf(task_name.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(task_id.get(position)));
                intent.putExtra("nm", String.valueOf(task_name.get(position)));
                intent.putExtra("st", String.valueOf(task_status.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameText;
        LinearLayout mainLayout;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameText = itemView.findViewById(R.id.taskNameText);
            mainLayout = itemView.findViewById( R.id.mainLayout);

        }
    }
}
