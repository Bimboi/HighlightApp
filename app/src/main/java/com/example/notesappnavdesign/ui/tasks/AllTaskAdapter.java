package com.example.notesappnavdesign.ui.tasks;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.notesappnavdesign.ItemTask;
import com.example.notesappnavdesign.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllTaskAdapter extends RecyclerView.Adapter<AllTaskAdapter.TaskHolder> {
    private List<ItemTask> allItemTask = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        ItemTask currentItemTask = allItemTask.get(position);
        String strDate = currentItemTask.getTaskDate();
        Log.d("Date: ",strDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
        try {
            Date d = dateFormat.parse(strDate);
            Log.d("New Date: ",d.toString());
            dateFormat.applyPattern("EEEE, MMM dd, yyyy");
            String date = dateFormat.format(d);
            holder.textViewItemDate.setText(date);
        } catch (ParseException e) {
            Log.d("Date Error: ",e.toString());
        }
        holder.textViewItemTaskName.setText(currentItemTask.getTaskName());
    }

    @Override
    public int getItemCount() {
        return allItemTask.size();
    }

    public void setAllItemTask(List<ItemTask> allItemTask) {
        this.allItemTask = allItemTask;
        notifyDataSetChanged();
    }

    class TaskHolder extends RecyclerView.ViewHolder {
        private TextView textViewItemTaskName;
        private TextView textViewItemDate;

        public TaskHolder(final View itemView) {
            super(itemView);
            textViewItemTaskName = itemView.findViewById(R.id.itemTaskName);
            textViewItemDate = itemView.findViewById(R.id.itemDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(allItemTask.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ItemTask itemTask);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
