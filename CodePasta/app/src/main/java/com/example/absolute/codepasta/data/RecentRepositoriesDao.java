package com.example.absolute.codepasta.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface RecentRepositoriesDao {
    @Query("select * from recent_repositories")
    List<RecentRepositoriesTable> getRecentRepos();

    @Insert
    void insertRecentRepo(RecentRepositoriesTable repo);

    @Delete
    void delete(List<RecentRepositoriesTable> repo);
}

