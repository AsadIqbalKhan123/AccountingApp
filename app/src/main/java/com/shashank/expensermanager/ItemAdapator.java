package com.shashank.expensermanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.expensermanager.activities.Cat_Model_Class;

import java.util.List;

public class ItemAdapator extends RecyclerView.Adapter<ItemAdapator.MyViewHolder> {


    List<Cat_Model_Class> cat_model_classes1;
    private OnNoteListener monNoteListener;
    private Context context;


    public ItemAdapator(List<Cat_Model_Class> cat_model_classes1, OnNoteListener onNoteListener, Context context) {

        this.cat_model_classes1 = cat_model_classes1;
        this.monNoteListener = onNoteListener;
        this.context = context;

    }

    @Override
    public ItemAdapator.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, monNoteListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapator.MyViewHolder holder, int position) {

        holder.itemImage.setImageResource(cat_model_classes1.get(position).getImage());
        holder.itemText.setText(cat_model_classes1.get(position).getName());

        switch (position) {
            case 0:
                holder.itemText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "Text is Click", Toast.LENGTH_SHORT).show();
                    }
                });
        }


//        holder.edit_icon.setOnClickListener(view ->
//        {
//
//            final DialogPlus dialogPlus = DialogPlus.newDialog(holder.itemImage.getContext())
//                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
//                    .setExpanded(true, 1100)
//                    .create();
//
//            View view1 = dialogPlus.getHolderView();
//            final EditText name = view1.findViewById(R.id.uname);
//
//            Button submitt = view1.findViewById(R.id.usubmit);
//
//            name.setText(cat_model_classes1.get(position).getName());
//
//            dialogPlus.show();
//
//            submitt.setOnClickListener(view2 ->
//            {
//                Map<String, Object> map = new HashMap<>();
//                map.put("name", name.getText().toString());
//
//                dialogPlus.dismiss();
//            });
//
//            holder.delete_icon.setOnClickListener(view2 ->
//            {
//                AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemImage.getContext());
//                builder.setTitle("Delete Category");
//                builder.setMessage("Are you Want to Sure");
//                builder.setPositiveButton("Yes ", ((dialogInterface, i) -> {
//
//
//                }));
//
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//
//                    }
//
//                });
//                builder.show();
//
//            });
//
//
//        });


    }

    @Override
    public int getItemCount() {
        return cat_model_classes1.size();
    }

    public interface OnNoteListener {

        void onNoteClick(int postion);


//        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView itemImage, edit_icon, delete_icon;
        TextView itemText;

        OnNoteListener onNoteListener;

        public MyViewHolder(View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            this.onNoteListener = onNoteListener;
            itemImage = itemView.findViewById(R.id.item_image);
            itemText = itemView.findViewById(R.id.text_item);
//            edit_icon = itemView.findViewById(R.id.editicon1);
//            delete_icon = itemView.findViewById(R.id.deleteicon1);

            itemText.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            monNoteListener.onNoteClick(getAdapterPosition());

        }
    }
}
