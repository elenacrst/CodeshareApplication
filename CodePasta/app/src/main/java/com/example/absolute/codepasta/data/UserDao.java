package com.example.absolute.codepasta.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {
    @Query("select * from user where login = :login")
    UserTable getUser(String login);

    @Insert
    void insertUser(UserTable user);

    @Delete
    void deleteUser(UserTable user);
}
