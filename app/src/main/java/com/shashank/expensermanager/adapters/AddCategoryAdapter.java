package com.shashank.expensermanager.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shashank.expensermanager.Demo_Activity;
import com.shashank.expensermanager.R;
import com.shashank.expensermanager.UserData;

import java.util.ArrayList;

public class AddCategoryAdapter extends RecyclerView.Adapter<AddCategoryAdapter.MyViewHolder> {
    ArrayList<UserData> list;
    Context context;
    SharedPreferences.Editor editor;
    Gson gson;

    public AddCategoryAdapter(ArrayList<UserData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public AddCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.new_item, parent, false);
        return new AddCategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddCategoryAdapter.MyViewHolder holder, int position) {
        UserData model = list.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences("Category", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (model != null) {
            holder.textView.setText(model.getName());

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, WeatherActivity.class);
//                intent.putExtra("pos",holder.getAdapterPosition());
//                context.startActivity(intent);
            }
        });

        holder.edit_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, Demo_Activity.class);
                String s = (String) holder.textView.getText();

                i.putExtra("update", s);
                context.startActivity(i);
            }
        });


        holder.deletem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Are you want to sure to delete this!")
                        
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                list.remove(model);
                                notifyDataSetChanged();
                                gson = new Gson();
                                String json = gson.toJson(list);
                                editor = sharedPreferences.edit();
                                editor.putString("category", json);
                                editor.apply();
                                SharedPreferences sharedPreferences1 = context.getSharedPreferences(model.getName(), Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                                editor1.clear();
                                editor1.apply();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView, deletem, edit_item;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_1);
            textView = itemView.findViewById(R.id.text_mode1);
            deletem = itemView.findViewById(R.id.tv_delete_item1);
            edit_item = itemView.findViewById(R.id.edit_item);


        }
    }
}
