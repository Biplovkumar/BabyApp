package com.example.vivek.tablyoutdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vivek.tablyoutdemo.DetailActivity;
import com.example.vivek.tablyoutdemo.R;
import com.example.vivek.tablyoutdemo.database.AppDatabase;
import com.example.vivek.tablyoutdemo.model.BabyName;

import java.util.ArrayList;
import java.util.List;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.MyViewHolder> {
    private Context context;
    private List<BabyName> babyNames = new ArrayList<>();

    public BabyAdapter(Context context) {
        this.context = context;
    }

    public void setBabyNames(List<BabyName> babyNames) {
        this.babyNames = babyNames;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.items);

        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.baby_name,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.name.setText(babyNames.get(position).getName());

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("BabyList", babyNames.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return babyNames.size();
    }


}
