package com.example.notesappnavdesign;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorHolder> {

    private List<String> initColors;
    private ArrayList<String> updatedColors = new ArrayList<>();
    private ColorAdapter.OnItemClickListener listener;

    public ColorAdapter(List<String> s){
        this.initColors = s;
    }

    @NonNull
    @Override
    public ColorAdapter.ColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View colorView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_item, parent, false);
        return new ColorAdapter.ColorHolder(colorView);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorAdapter.ColorHolder holder, int position) {
        holder.imageViewColor.setImageDrawable(getColor(initColors.get(position),
                holder.imageViewColor.getContext()));
    }

    @Override
    public int getItemCount() {
        return initColors.size();
    }

    class ColorHolder extends RecyclerView.ViewHolder {
        ImageView imageViewColor;

        public ColorHolder(final View color) {
            super(color);
            imageViewColor = color.findViewById(R.id.colorImage);

            color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    String currentColor = initColors.get(position);

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Log.d("Inside onClick Color:",currentColor);
                        updatedColors.clear();
                        switch(currentColor){
                            case "colored":
                                updatedColors.add("no color");
                                updatedColors.add("black");
                                updatedColors.add("cyan");
                                updatedColors.add("magenta");
                                updatedColors.add("yellow");
                                initColors = updatedColors;
                                notifyDataSetChanged();
                                listener.onItemClick("#FFFFFF");
                                break;
                            case "black":
                                updatedColors.add("colored");
                                updatedColors.add("black enable");
                                updatedColors.add("cyan");
                                updatedColors.add("magenta");
                                updatedColors.add("yellow");
                                initColors = updatedColors;
                                notifyDataSetChanged();
                                listener.onItemClick("#494949"); //#1A1A1A
                                break;
                            case "cyan":
                                updatedColors.add("colored");
                                updatedColors.add("black");
                                updatedColors.add("cyan enable");
                                updatedColors.add("magenta");
                                updatedColors.add("yellow");
                                initColors = updatedColors;
                                notifyDataSetChanged();
                                listener.onItemClick("#8EC5D9"); //#12A4D9
                                break;
                            case "magenta":
                                updatedColors.add("colored");
                                updatedColors.add("black");
                                updatedColors.add("cyan");
                                updatedColors.add("magenta enable");
                                updatedColors.add("yellow");
                                initColors = updatedColors;
                                notifyDataSetChanged();
                                listener.onItemClick("#F181C3"); //#D9138A
                                break;
                            case "yellow":
                                updatedColors.add("colored");
                                updatedColors.add("black");
                                updatedColors.add("cyan");
                                updatedColors.add("magenta");
                                updatedColors.add("yellow enable");
                                initColors = updatedColors;
                                notifyDataSetChanged();
                                listener.onItemClick("#E2E165"); //#E2D810
                                break;
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String strColor);
    }

    public void setOnItemClickListener(ColorAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public Drawable getColor(@NonNull  String colorTag, Context c){
        Drawable d = ContextCompat.getDrawable(c,R.drawable.ic_no_color);
        switch(colorTag){
            case "no color":
                d = ContextCompat.getDrawable(c,R.drawable.ic_no_color);
                break;
            case "colored":
                d = ContextCompat.getDrawable(c,R.drawable.ic_colored);
                break;
            case "black":
                d = ContextCompat.getDrawable(c,R.drawable.ic_black);
                break;
            case "black enable":
                d = ContextCompat.getDrawable(c,R.drawable.ic_black_enable);
                break;
            case "cyan":
                d = ContextCompat.getDrawable(c,R.drawable.ic_cyan);
                break;
            case "cyan enable":
                d = ContextCompat.getDrawable(c,R.drawable.ic_cyan_enable);
                break;
            case "magenta":
                d = ContextCompat.getDrawable(c,R.drawable.ic_magenta);
                break;
            case "magenta enable":
                d = ContextCompat.getDrawable(c,R.drawable.ic_magenta_enable);
                break;
            case "yellow":
                d = ContextCompat.getDrawable(c,R.drawable.ic_yellow);
                break;
            case "yellow enable":
                d = ContextCompat.getDrawable(c,R.drawable.ic_yellow_enable);
                break;
        }
        return d;
    }
}
