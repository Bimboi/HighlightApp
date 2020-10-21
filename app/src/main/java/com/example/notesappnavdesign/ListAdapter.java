package com.example.notesappnavdesign;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    private List<ItemList> allItemList = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ListHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {
        ItemList currentItemList = allItemList.get(position);
        holder.textViewItemListName.setText(currentItemList.getListName());

    }

    @Override
    public int getItemCount() {
        return allItemList.size();
    }

    public void setAllItemList(List<ItemList> allItemList){
        this.allItemList = allItemList;
        notifyDataSetChanged();
    }

    class ListHolder extends RecyclerView.ViewHolder{
        private TextView textViewItemListName;

        public ListHolder(final View itemView){
            super(itemView);
            textViewItemListName = itemView.findViewById(R.id.itemListName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(allItemList.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(ItemList itemList);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
