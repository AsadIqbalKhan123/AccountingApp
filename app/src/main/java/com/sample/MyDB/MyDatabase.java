package com.sample.MyDB;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserTable.class}, version = 1)

public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDao getDao();


}
