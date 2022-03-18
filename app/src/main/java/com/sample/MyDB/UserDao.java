package com.sample.MyDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserTable userTable);

    @Query("SELECT EXISTS (SELECT * from UserTable where email=:email)")
    boolean is_taken(String email);

    @Query("SELECT EXISTS (SELECT * from UserTable where email=:userEmail AND password=:password )")
    boolean login(String userEmail, String password);


}
