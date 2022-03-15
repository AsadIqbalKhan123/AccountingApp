package com.shashank.expensermanager.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.expensermanager.R;
import com.shashank.expensermanager.UserData;
import com.shashank.expensermanager.activities.AddExpenseActivity;
import com.shashank.expensermanager.transactionDb.AppDatabase;
import com.shashank.expensermanager.transactionDb.TransactionEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.shashank.expensermanager.utils.Constants.editExpenseString;
import static com.shashank.expensermanager.utils.Constants.editIncomeString;
import static com.shashank.expensermanager.utils.Constants.incomeCategory;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    ArrayList<UserData> list;
    Context context;
    ItemClickListener listener;

    private List<TransactionEntry> transactionEntries;
    private AppDatabase appDatabase;

    public CustomAdapter(Context context, List<TransactionEntry> transactionEntries, ItemClickListener listener) {
        this.context = context;
        this.transactionEntries = transactionEntries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String amount;

        TransactionEntry model = transactionEntries.get(position);


//        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
////                transactionEntries.remove(1)
//
//                Toast.makeText(context, "Press Long", Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });

        holder.delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(view.getContext())
                        .setTitle("Are you want to sure to delete this!")
                        .setMessage("Delete this")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                if (listener != null) {
                                    listener.OnItemClick(position);
                                    notifyDataSetChanged();
                                }
//                                transactionEntries.remove(model);
//                                notifyDataSetChanged();
//                                transactionEntries.remove(model);


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


        holder.categoryTextViewrv.setText(transactionEntries.get(position).getCategory());
        if (incomeCategory.equals(transactionEntries.get(position).getTransactionType())) {
            amount = "" + transactionEntries.get(position).getAmount();
            holder.amountTextViewrv.setText(amount);
            holder.amountTextViewrv.setTextColor(Color.parseColor("#aeea00"));
        } else {
            amount = "-" + transactionEntries.get(position).getAmount();
            holder.amountTextViewrv.setText(amount);
            holder.amountTextViewrv.setTextColor(Color.parseColor("#ff5722"));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//        String dateToBeSet = sdf.format(transactionEntries.get(position).getDate());
////        holder.dateTextViewrv.setText(dateToBeSet);
        holder.descriptionTextViewrv.setText(transactionEntries.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (transactionEntries == null || transactionEntries.size() == 0) {
            return 0;
        } else {
            return transactionEntries.size();
        }
    }

    public void setTasks(List<TransactionEntry> personList) {
        transactionEntries = personList;
        notifyDataSetChanged();
    }

    public List<TransactionEntry> getTransactionEntries() {
        return transactionEntries;
    }


    public interface ItemClickListener {

        void OnItemClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        TextView categoryTextViewrv;
        TextView amountTextViewrv;
        TextView descriptionTextViewrv;
        TextView dateTextViewrv;
        TextView delete_tv;


        public ViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;

            categoryTextViewrv = itemView.findViewById(R.id.categoryTextViewrv);
            amountTextViewrv = itemView.findViewById(R.id.amountTextViewrv);
            descriptionTextViewrv = itemView.findViewById(R.id.descriptionTextViewrv);
            dateTextViewrv = itemView.findViewById(R.id.dateTextViewrv);
            delete_tv = itemView.findViewById(R.id.tv_delete_item_tv);

            appDatabase = AppDatabase.getInstance(context);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Toast.makeText(context, "Press long for Edit ", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, AddExpenseActivity.class);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    String date = sdf.format(transactionEntries.get(getAdapterPosition()).getDate());


                    if (transactionEntries.get(getAdapterPosition()).getTransactionType().equals(incomeCategory)) {
                        intent.putExtra("from", editIncomeString);
                        intent.putExtra("amount", transactionEntries.get(getAdapterPosition()).getAmount());
                        intent.putExtra("description", transactionEntries.get(getAdapterPosition()).getDescription());
                        intent.putExtra("date", date);
                        intent.putExtra("category", transactionEntries.get(getAdapterPosition()).getCategory());
                        intent.putExtra("id", transactionEntries.get(getAdapterPosition()).getId());
                    } else {
                        intent.putExtra("from", editExpenseString);
                        intent.putExtra("amount", transactionEntries.get(getAdapterPosition()).getAmount());
                        intent.putExtra("description", transactionEntries.get(getAdapterPosition()).getDescription());
                        intent.putExtra("date", date);
                        intent.putExtra("category", transactionEntries.get(getAdapterPosition()).getCategory());
                        intent.putExtra("id", transactionEntries.get(getAdapterPosition()).getId());
                    }


                    //Updated on 19/8/2018 no need of this now as added update function properly
                    /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            appDatabase.transactionDao().removeExpense(transactionEntries.get(getAdapterPosition()));
                        }
                    });*/

                    context.startActivity(intent);
                    return true;

                }
            });
        }
    }
}
