package com.sample.MyDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserTable userTable);

    @Query("SELECT EXISTS (SELECT * from UserTable where userName=:userName)")
    boolean is_taken(String userName);

    @Query("SELECT EXISTS (SELECT * from UserTable where userName=:userName AND password=:password)")
    boolean login(String userName, String password);


}
