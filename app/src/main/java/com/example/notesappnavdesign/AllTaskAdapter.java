package com.example.notesappnavdesign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    private int blackID = Color.parseColor("#000000");
    private int whiteID = Color.parseColor("#FFFFFF");

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
        String itemColor = currentItemTask.getTaskColor();

        holder.textViewItemTaskName.setText(currentItemTask.getTaskName());
        holder.textViewItemDate.setText(reformatDate(currentItemTask.getTaskDate()));
        holder.imageViewItemFlag.setImageDrawable(getImageImportance(currentItemTask.getTaskImportance(), itemColor, context));
        holder.imageViewItemOverdue.setImageDrawable(getImageOverdue(currentItemTask.getTaskDate(), context));
        holder.cardView.setCardBackgroundColor(Color.parseColor(itemColor));

        if(itemColor.equals("#494949")){
            holder.imageViewSampleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_assignment_white));
            holder.textViewItemTaskName.setTextColor(whiteID);
            holder.textViewItemDate.setTextColor(whiteID);
        }else{
            holder.imageViewSampleIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_assignment));
            holder.textViewItemTaskName.setTextColor(blackID);
            holder.textViewItemDate.setTextColor(blackID);
        }
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
        private ImageView imageViewSampleIcon;
        private ImageView imageViewItemFlag;
        private ImageView imageViewItemOverdue;
        private CardView cardView;

        public TaskHolder(final View itemView) {
            super(itemView);
            textViewItemTaskName = itemView.findViewById(R.id.itemTaskName);
            textViewItemDate = itemView.findViewById(R.id.itemDate);
            imageViewSampleIcon = itemView.findViewById(R.id.sampleTaskIcon);
            imageViewItemFlag = itemView.findViewById(R.id.importance_label);
            imageViewItemOverdue = itemView.findViewById(R.id.overdue_label);
            cardView = itemView.findViewById(R.id.cardView);

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
            assert d != null;
            Log.d("New Date: ",d.toString());
            dateFormat.applyPattern("EEEE, MMM dd, yyyy");
            return dateFormat.format(d);
        } catch (ParseException e) {
            Log.d("Date Error: ",e.toString());
            return "Error date";
        }
    }

    public Drawable getImageImportance(int indicator, @NonNull String color, Context con){
        if(color.equals("#494949")){
            if (indicator == 0) {
                return ContextCompat.getDrawable(con, R.drawable.ic_important_notflag_white);
            } else {
                return ContextCompat.getDrawable(con, R.drawable.ic_important_flag_white);
            }
        }else{
            if (indicator == 0) {
                return ContextCompat.getDrawable(con, R.drawable.ic_important_notflag);
            } else {
                return ContextCompat.getDrawable(con, R.drawable.ic_important_flag);
            }
        }
    }

    public Drawable getImageOverdue(String strDate, Context con){
        Drawable image = ContextCompat.getDrawable(con, R.drawable.ic_not_overdue);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try{
            Date d = dateFormat.parse(strDate);
            assert d != null;
            if(d.compareTo(dateFormat.parse(dateFormat.format(new Date()))) < 0){
                image = ContextCompat.getDrawable(con, R.drawable.ic_overdue);
            }
        }catch(ParseException e){
            Log.d("Date Overdue Error: ",e.toString());
        }
        return image;
    }
}
