package com.shashank.expensermanager.transactionDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {

    @Query("select * from transactionTable order by date DESC")
    LiveData<List<TransactionEntry>> loadAllTransactions();

    @Query("select * from transactionTable where id = :id")
    LiveData<TransactionEntry> loadExpenseById(int id);

    @Query("select sum(amount) from transactionTable where transactionType =:transactionType")
    int getAmountByTransactionType(String transactionType);

    @Query("select sum(amount) from transactionTable where transactionType =:transactionType and  date between :startDate and :endDate")
    int getAmountbyCustomDates(String transactionType, long startDate, long endDate);

    @Query("select sum(amount) from transactionTable where category=:category")
    int getSumExpenseByCategory(String category);

    @Query("select sum(amount) from transactionTable where category=:category and date between :startDate and :endDate")
    int getSumExpenseByCategoryCustomDate(String category, long startDate, long endDate);

    @Query("select min(date) from transactionTable ")
    long getFirstDate();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertExpense(TransactionEntry transactionEntry);

    @Delete
    void removeExpense(TransactionEntry transactionEntry);

    @Delete
    void delete(TransactionEntry transactionEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateExpenseDetails(TransactionEntry transactionEntry);


    // FOR REGISTISTRATION USER WE USE THIS QUERY ....

    @Insert
    void registerUser(TransactionEntry transactionEntry);

    @Query("select * from transactionTable where userId=(:userId) and password=(:password)")
    TransactionEntry login(String userId, String password);


}
