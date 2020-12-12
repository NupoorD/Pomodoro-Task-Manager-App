package com.example.pomotasks;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterTwo extends RecyclerView.Adapter<AdapterTwo.HolderRecord>{
    private Context context;
    private ArrayList<ModelRecord> recordList;

    public AdapterTwo(Context context, ArrayList<ModelRecord> recordList) {
        this.context = context;
        this.recordList = recordList;
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_row_completed,parent,false);
        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, int position) {
        ModelRecord model = recordList.get(position);
        String mid = model.getId();
        String mname = model.getName();
        String mcaption = model.getCaption();
        String mimage = model.getImage();
        String mloc = model.getLocation();

        holder.name.setText(mname);
        holder.caption.setText(mcaption);
        holder.image.setImageURI(Uri.parse(mimage));
        holder.locn.setText(mloc);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

    class HolderRecord extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView caption;
        TextView locn;
        public HolderRecord(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.taskDonePhoto);
            name = itemView.findViewById(R.id.taskDoneName);
            caption = itemView.findViewById(R.id.taskDoneCaption);
            locn = itemView.findViewById(R.id.taskDoneLocation);
        }
    }
}
