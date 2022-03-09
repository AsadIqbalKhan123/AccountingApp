package com.shashank.expensermanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    List<UserData> list = new ArrayList<>();
    ItemClickListener itemClickListener;
    Context context;

    public MyAdapter(List<UserData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, @SuppressLint("RecyclerView") final int position) {

        final UserData userData = list.get(position);

        holder.tv_name.setText(userData.getName());
        holder.share.setImageResource(userData.getImgName());
        holder.edit.setImageResource(R.drawable.ic_baseline_edit_);
        holder.share.setImageResource(R.drawable.ic_baseline_share_24);

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(context, Add_Category.class);
//                intent.putExtra("imgName", userData.getImgName());
//                intent.putExtra("name", userData.getName());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
                Toast.makeText(context, "Share ", Toast.LENGTH_SHORT).show();

            }
        });


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.OnItemClick(position, userData);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void UpdateData(int position, UserData userData) {

        list.remove(position);
        list.add(userData);
        notifyItemChanged(position);
        notifyDataSetChanged();

    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_name;
        TextView delete;
        ImageView edit, share;


        public MyHolder(View itemView) {

            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_item);
            delete = itemView.findViewById(R.id.tv_delete_item);
            edit = itemView.findViewById(R.id.tv_edit_item);
            share = itemView.findViewById(R.id.send_to);

        }
    }
}
