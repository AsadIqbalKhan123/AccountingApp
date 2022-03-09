package com.shashank.expensermanager.fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.shashank.expensermanager.R;
import com.shashank.expensermanager.UserData;
import com.shashank.expensermanager.adapters.CustomAdapter;
import com.shashank.expensermanager.transactionDb.AppDatabase;
import com.shashank.expensermanager.transactionDb.AppExecutors;
import com.shashank.expensermanager.transactionDb.TransactionEntry;
import com.shashank.expensermanager.transactionDb.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseFragment extends Fragment {

    private static final String LOG_TAG = ExpenseFragment.class.getSimpleName();
    public TransactionViewModel transactionViewModel;
    Spinner spinner;
    ArrayList<UserData> Mylist = new ArrayList<>();
    private RecyclerView rv;
    private List<TransactionEntry> transactionEntries;
    private CustomAdapter customAdapter;
    private AppDatabase mAppDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_expense, container, false);
        rv = view.findViewById(R.id.transactionRecyclerView);
        rv.setHasFixedSize(true);
        transactionEntries = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

//        setupPieChart();
        spinner = view.findViewById(R.id.spinner1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (adapterView.getSelectedItemPosition() == 1) {

                    customAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Weekly Data", Toast.LENGTH_SHORT).show();

                } else if (adapterView.getSelectedItemPosition() == 2) {
                    customAdapter.notifyItemChanged(i);
                    customAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Monthly Data", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        ArrayList<String> values = getArguments().getStringArrayList("valuesArray");


//        Intent intent = new Intent(getContext(),
//                MainActivity.class);
//        intent.putExtras(intent);
//        getContext().startActivity(intent);

        mAppDb = AppDatabase.getInstance(getContext());


        setupSpinner();


//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {

                new AlertDialog.Builder(viewHolder.itemView.getContext())
                        .setMessage("Are you want to sure to delete this!")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {

                                // Here is where you'll implement swipe to delete
                                // COMPLETED (1) Get the diskIO Executor from the instance of AppExecutors and
                                // call the diskIO execute method with a new Runnable and implement its run method
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {

                                    @Override
                                    public void run() {
                                        int position = viewHolder.getAdapterPosition();

                                        List<TransactionEntry> transactionEntries = customAdapter.getTransactionEntries();
                                        mAppDb.transactionDao().removeExpense(transactionEntries.get(position));

                                    }
                                });

                                Snackbar.make(view, "Transaction Deleted", Snackbar.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                customAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            }
                        })
                        .create()
                        .show();


            }
        }).attachToRecyclerView(rv);

        setupViewModel();

        return view;
    }

//    private void setupPieChart() {
//
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//
//            @Override
//            public void run() {
//                if (spinner.getSelectedItemPosition() == 0)
//                    getAllPieValues();
//                else if (spinner.getSelectedItemPosition() == 1) {
//                    try {
//                        getWeekPieValues();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else if (spinner.getSelectedItemPosition() == 2) {
//                    try {
//                        getMonthPieValues();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//            }
//        });
//
//
//    }
//


    private void setupSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.date_array,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }


    public void setupViewModel() {
        transactionViewModel = ViewModelProviders.of(this)
                .get(TransactionViewModel.class);

        transactionViewModel.getExpenseList()
                .observe(this, new Observer<List<TransactionEntry>>() {
                    @Override
                    public void onChanged(@Nullable List<TransactionEntry> transactionEntriesFromDb) {
                        Log.i(LOG_TAG, "Actively retrieving from DB");


                        transactionEntries = transactionEntriesFromDb;
//                        Logging to check DB values
                        for (int i = 0; i < transactionEntries.size(); i++) {
                            String description = transactionEntries.get(i).getDescription();
                            int amount = transactionEntries.get(i).getAmount();
                            //Log.i("DESCRIPTION AMOUNT",description + String.valueOf(amount));
                        }


//                        Setting Adapter
                        customAdapter = new CustomAdapter(getActivity(), transactionEntries);

                        rv.setAdapter(customAdapter);
                        customAdapter.notifyDataSetChanged();
                    }
                });
    }
}
