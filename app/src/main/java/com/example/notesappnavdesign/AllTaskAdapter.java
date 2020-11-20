package com.example.notesappnavdesign;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        Context context = holder.imageViewItemFlag.getContext();

        holder.textViewItemTaskName.setText(currentItemTask.getTaskName());
        holder.textViewItemDate.setText(reformatDate(currentItemTask.getTaskDate()));
        holder.imageViewItemFlag.setImageDrawable(getImage(currentItemTask.getTaskImportance(),context));
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
        private ImageView imageViewItemFlag;

        public TaskHolder(final View itemView) {
            super(itemView);
            textViewItemTaskName = itemView.findViewById(R.id.itemTaskName);
            textViewItemDate = itemView.findViewById(R.id.itemDate);
            imageViewItemFlag = itemView.findViewById(R.id.importance_label);

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

    public String reformatDate(String date){
        Log.d("Date: ",date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date d = dateFormat.parse(date);
            Log.d("New Date: ",d.toString());
            dateFormat.applyPattern("EEEE, MMM dd, yyyy");
            return dateFormat.format(d);
        } catch (ParseException e) {
            Log.d("Date Error: ",e.toString());
            return "Error date";
        }
    }

    public Drawable getImage(int indicator, Context con){
        if (indicator == 0) {
            return ContextCompat.getDrawable(con, R.drawable.ic_important_notflag);
        } else {
            return ContextCompat.getDrawable(con, R.drawable.ic_important_flag);
        }
    }
}
