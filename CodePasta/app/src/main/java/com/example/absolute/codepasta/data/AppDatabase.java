package com.example.absolute.codepasta.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {UserTable.class, RecentRepositoriesTable.class, MyRepositoriesTable.class},
        version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract RecentRepositoriesDao recentRepositoriesDao();
    public abstract MyRepositoriesDao myRepositoriesDao();

}
