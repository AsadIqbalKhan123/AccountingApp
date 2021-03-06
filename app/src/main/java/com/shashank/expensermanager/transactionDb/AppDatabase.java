package com.shashank.expensermanager.transactionDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

@Database(entities = TransactionEntry.class, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();

//    private static final String dbName = "user";

    private static final String DATABASE_NAME = "TransactionDb";

    private static AppDatabase appDatabase;


    public static synchronized AppDatabase getInstance(Context context) {

        if (appDatabase == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");

                appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return appDatabase;
    }

    public abstract TransactionDao transactionDao();
}