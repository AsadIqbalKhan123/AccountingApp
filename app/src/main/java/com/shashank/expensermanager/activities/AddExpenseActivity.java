package com.shashank.expensermanager.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashank.expensermanager.R;
import com.shashank.expensermanager.UserData;
import com.shashank.expensermanager.transactionDb.AppDatabase;
import com.shashank.expensermanager.transactionDb.AppExecutors;
import com.shashank.expensermanager.transactionDb.TransactionEntry;
import com.shashank.expensermanager.transactionDb.TransactionViewModel;
import com.shashank.expensermanager.utils.Constants;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity {


    private static final String LOG_TAG = AddExpenseActivity.class.getSimpleName();
    private static AppDatabase appDatabase;
    TextInputEditText amountTextInputEditText;
    TextInputEditText descriptionTextInputEditText;
    TextInputLayout amountTextInputLayout;
    Gson gson;
    SharedPreferences sharedPreferences;
    ArrayList<UserData> Mylist = new ArrayList<>();
    TextInputLayout descriptionTextInputLayout;
    TextView dateTextView;
    LinearLayout dateLinearLayout;
    Spinner categorySpinner;
    ArrayList<String> categories;
    Calendar myCalendar;
    String description;
    Date dateOfExpense;

    Button button;
    //These variables contain data which will be stored permanently on hitting save button
    int amount;
    String categoryOfExpense;       //This parameter is to decide category in a transaction
    String categoryOfTransaction;  //This parameter to decide whether it is income and expense
    //Variable to keep track from where it came to this activity
    String intentFrom;
    TransactionViewModel transactionViewModel;
    int transactionid;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        amountTextInputEditText = findViewById(R.id.amountTextInputEditText);
        descriptionTextInputEditText = findViewById(R.id.descriptionTextInputEditText);
        amountTextInputLayout = findViewById(R.id.amountTextInputLayout);
        descriptionTextInputLayout = findViewById(R.id.descriptionTextInputLayout);
        dateTextView = findViewById(R.id.dateTextView);
        dateLinearLayout = findViewById(R.id.dateLinerLayout);
        categorySpinner = findViewById(R.id.categorySpinner);
        sharedPreferences = getSharedPreferences("Category", MODE_PRIVATE);
        loadCategory();

        appDatabase = AppDatabase.getInstance(getApplicationContext());


        transactionViewModel = ViewModelProviders.of(this)
                .get(TransactionViewModel.class);

        categories = new ArrayList<>();

        myCalendar = Calendar.getInstance();
        setDateToTextView();

        //First task here is to determine from where this activity is launched from the 4 possibilities

        Intent intent = getIntent();

        intentFrom = intent.getStringExtra("from");

        if (intentFrom.equals(Constants.addIncomeString)) {
            categoryOfTransaction = Constants.incomeCategory;
            setTitle("Add Income");

            for (int i = 0; i < Mylist.size(); i++) {
                categories.add(Mylist.get(i).getName());
            }
//            for (int i = 0; i < Mylist.size(); i++) {
//                categories.add(Mylist.get(i).getName());
//            }
            categorySpinner.setClickable(true);
            categorySpinner.setEnabled(true);
            categorySpinner.setAdapter(new ArrayAdapter<>(AddExpenseActivity.this, android.R.layout.simple_list_item_1, categories));

        } else if (intentFrom.equals(Constants.addExpenseString)) {
            categoryOfTransaction = Constants.expenseCategory;
            setTitle("Add Expense");
            for (int i = 0; i < Mylist.size(); i++) {
                categories.add(Mylist.get(i).getName());
            }
            categorySpinner.setAdapter(new ArrayAdapter<>(AddExpenseActivity.this,
                    android.R.layout.simple_list_item_1, categories));

        } else if (intentFrom.equals(Constants.editIncomeString)) {
            setTitle("Edit Income");

            amountTextInputEditText.setText(String.valueOf(intent.getIntExtra("amount", 1)));
            amountTextInputEditText.setSelection(amountTextInputEditText.getText().length());
            descriptionTextInputEditText.setText(intent.getStringExtra("description"));
            descriptionTextInputEditText.setSelection(descriptionTextInputEditText.getText().length());
            transactionid = intent.getIntExtra("id", -1);

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = sdf.parse(intent.getStringExtra("date"));
                myCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateTextView.setText(intent.getStringExtra("date"));

            categoryOfTransaction = Constants.incomeCategory;
            categories.add("Income");
            categorySpinner.setClickable(false);
            categorySpinner.setEnabled(false);
            categorySpinner.setAdapter(new ArrayAdapter<>(AddExpenseActivity.this, android.R.layout.simple_list_item_1, categories));

        } else if (intentFrom.equals(Constants.editExpenseString)) {
            categoryOfTransaction = Constants.expenseCategory;
            setTitle("Edit Expense");

            if (intentFrom.equals(Constants.addExpenseString))
                categoryOfTransaction = Constants.expenseCategory;
            setTitle("Add Expense");
            for (int i = 0; i < Mylist.size(); i++) {
                categories.add(Mylist.get(i).getName());
            }
            categorySpinner.setAdapter(new ArrayAdapter<>(AddExpenseActivity.this,
                    android.R.layout.simple_list_item_1, categories));


            amountTextInputEditText.setText(String.valueOf(intent.getIntExtra("amount", 0)));
            amountTextInputEditText.setSelection(amountTextInputEditText.getText().length());
            descriptionTextInputEditText.setText(intent.getStringExtra("description"));

            descriptionTextInputEditText.setSelection(descriptionTextInputEditText.getText().length());
            dateTextView.setText(intent.getStringExtra("date"));
            transactionid = intent.getIntExtra("id", -1);


            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                Date date = sdf.parse(intent.getStringExtra("date"));
                myCalendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            categories.add("Food");
//            categories.add("Travel");
//            categories.add("Clothes");
//            categories.add("Movies");
//            categories.add("Health");
//            categories.add("Grocery");
//            categories.add("Other");

            categorySpinner.setAdapter(new ArrayAdapter<>(AddExpenseActivity.this, android.R.layout.simple_list_item_1, categories));
            categorySpinner.setSelection(categories.indexOf(intent.getStringExtra("category")));
        }


        button = findViewById(R.id.clr_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClearbtn();

            }
        });


        dateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

    }


    public void onClearbtn() {

        amountTextInputEditText.getText().clear();
        descriptionTextInputEditText.getText().clear();

    }


    public void showDatePicker() {


        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDateToTextView();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddExpenseActivity.this, dateSetListener,
                myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();

        //In this method not able to set max date
        /*new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDateToTextView();
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();*/

        //This methode requires android n
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            datePickerDialog = new DatePickerDialog(AddExpenseActivity.this);

            datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    setDateToTextView();
                }
            });

            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            datePickerDialog.show();
        }*/

    }


    public void setDateToTextView() {
        Date date = myCalendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dateToBeSet = sdf.format(date);
        dateTextView.setText(dateToBeSet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_expense_activty_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

//            case R.id.clr_btn:
//
//                onClearbtn();
//
//                break;

            case R.id.saveButton:
                // COMPLETED: 10-09-2018 1.Retrieve and Save data to database and also update the recycler view


                if (amountTextInputEditText.getText().toString().isEmpty()
                        || descriptionTextInputEditText.getText().toString().isEmpty()) {

                    if (amountTextInputEditText.getText().toString().isEmpty())
                        amountTextInputEditText.setError("Amount cannot be empty");
                    if (descriptionTextInputEditText.getText().toString().isEmpty())
                        descriptionTextInputEditText.setError("Please write some description");

                } else {
                    amount = Integer.parseInt(amountTextInputEditText.getText().toString());
                    description = descriptionTextInputEditText.getText().toString();
                    dateOfExpense = myCalendar.getTime();

                    if (intentFrom.equals(Constants.addIncomeString)
                            || intentFrom.equals(Constants.editIncomeString))
                        categoryOfExpense = "Income";
                    else
                        categoryOfExpense = categories.get(categorySpinner.getSelectedItemPosition());

                    final TransactionEntry mTransactionEntry = new TransactionEntry(amount,
                            categoryOfExpense,
                            description,
                            dateOfExpense,
                            categoryOfTransaction
                    );

                    if (intentFrom.equals(Constants.addIncomeString) || intentFrom.equals(Constants.addExpenseString)) {


                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase.transactionDao().insertExpense(mTransactionEntry);
                            }
                        });

                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        Snackbar.make(getCurrentFocus(), "Transaction Added", Snackbar.LENGTH_LONG).show();
                    } else {
                        mTransactionEntry.setId(transactionid);
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                appDatabase.transactionDao().updateExpenseDetails(mTransactionEntry);

                            }
                        });

                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        Snackbar.make(getCurrentFocus(), "Transaction Updated", Snackbar.LENGTH_LONG).show();

                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1000);

                }
                break;
        }
        return true;


    }

    public void loadCategory() {
        gson = new Gson();
        String json = null;
        json = sharedPreferences.getString("category", null);
        Type type = new TypeToken<ArrayList<UserData>>() {
        }.getType();
        Mylist = gson.fromJson(json, type);

        if (Mylist == null) {

            Mylist = new ArrayList<UserData>();
        }
    }


}

