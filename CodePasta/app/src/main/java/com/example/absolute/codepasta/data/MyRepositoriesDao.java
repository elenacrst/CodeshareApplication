package com.example.absolute.codepasta.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Absolute on 12/18/2017.
 */

@Dao
public interface MyRepositoriesDao {
    @Query("select * from my_repositories")
    List<MyRepositoriesTable> getMyRepos();

    @Insert
    void insertMyRepo(MyRepositoriesTable repo);

    @Delete
    void delete(List<MyRepositoriesTable> repo);
}
