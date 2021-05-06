package com.example.notesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<NotesData> listdata;

    // RecyclerView recyclerView;
    public ListAdapter(ArrayList<NotesData> listdata) {
        this.listdata = listdata;
    }

    @Override
    public @NotNull ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final NotesData myListData = listdata.get(position);
        holder.textView1.setText(listdata.get(position).getTitle());
        holder.textView2.setText(listdata.get(position).getDescription());

        holder.imageView.setImageAlpha(listdata.get(position).getImage_id());
        holder.constraintLayout.setOnClickListener(view -> Toast.makeText(view.getContext(), "click on item: " + myListData.getDescription(), Toast.LENGTH_LONG).show());
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView1, textView2;
        public ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.photo);
            this.textView1 = (TextView) itemView.findViewById(R.id.heading);
            this.textView2 = (TextView) itemView.findViewById(R.id.description);

            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.item);
        }
    }
}