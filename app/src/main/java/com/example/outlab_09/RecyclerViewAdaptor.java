package com.example.outlab_09;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder> {

    private static final String TAG = "RecyclerViewAdaptor";

    private Context context;
    private ArrayList<HashMap<String, String>> data = new ArrayList<>();

    public RecyclerViewAdaptor(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.title.setText(data.get(position).get("title"));
        holder.author.setText(data.get(position).get("author"));
        holder.date.setText(data.get(position).get("date"));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + data.size());
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView author;
        TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.date);
        }
    }
}
