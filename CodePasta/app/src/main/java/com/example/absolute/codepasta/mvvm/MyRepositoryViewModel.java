package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.absolute.codepasta.ui.RepositoriesActivity;
import com.example.absolute.codepasta.data.AppDatabase;
import com.example.absolute.codepasta.data.MyRepositoriesTable;
import com.example.absolute.codepasta.retrofit.RepositoryData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Absolute on 12/18/2017.
 */

public class MyRepositoryViewModel extends ViewModel {
    private MediatorLiveData<List<RepositoryData>> mRepositoryData;
    private MyRepositoryInt mReposInt;

    // No argument constructor
    public MyRepositoryViewModel() {
        mRepositoryData = new MediatorLiveData<>();
        mReposInt = new MyRepositoryIntImpl();
    }

    @NonNull
    public LiveData<List<RepositoryData>> getMyRepositories() {
        return mRepositoryData;
    }

    public void loadRepos(String username, final Context context) {
        Log.v("loadrepository", "called");
        mRepositoryData.addSource(

                mReposInt.getRepositories(username, context), new Observer<List<RepositoryData>>() {
                    @Override
                    public void onChanged(@Nullable List<RepositoryData> repositoryData) {
                        Log.v("repositorychange", "called");
                        if (repositoryData != null && repositoryData.size()>0){
                            mRepositoryData.setValue(repositoryData);
                            handleResponse(repositoryData, context);
                        }else{
                            handleError(context);

                        }
                    }

                }
        );
    }

    public static void handleResponse(final List<RepositoryData> data, final Context context) {
        for (RepositoryData repositoryData: data){
            Log.v("repository", repositoryData.getCreated_at()+" "+
                    repositoryData.getDescription()+" "+
                    repositoryData.getHtml_url()+" "+
                    repositoryData.getLanguage()+" "+
                    repositoryData.getName()+" "+
                    repositoryData.getScore()+" "+
                    repositoryData.getUpdated_at()+" "+
                    repositoryData.getWatchers());
            //response => query, delete everything from db and insert the new response

        }

        Intent intent = new Intent(context,
                RepositoriesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("repos", (ArrayList<? extends Parcelable>) data);
        intent.putExtra("repositories", bundle);
        context.startActivity(intent);

        io.reactivex.Observable.fromCallable(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                AppDatabase db = Room
                        .databaseBuilder(context, AppDatabase.class, "pasta-database")
                        .fallbackToDestructiveMigration()
                        .build();
                List<MyRepositoriesTable> repos = db.myRepositoriesDao().getMyRepos();
                db.myRepositoriesDao().delete(repos);
                for (RepositoryData repo: data){
                    MyRepositoriesTable newRepo = new MyRepositoriesTable(repo.getName(),
                            repo.getHtml_url(), repo.getDescription(), repo.getCreated_at(),repo.getUpdated_at(),
                            repo.getLanguage(), repo.isHas_issues(), repo.isHas_downloads(), repo.getWatchers(),
                            repo.getScore());
                    db.myRepositoriesDao().insertMyRepo(newRepo);
                }
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());;


    }

    public static void handleError(final Context context) {

        Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();

        //no network connection => try to query db
        final List<RepositoryData> data= new ArrayList<>();

        io.reactivex.Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = Room
                        .databaseBuilder(context, AppDatabase.class, "pasta-database")
                        .fallbackToDestructiveMigration()
                        .build();
                List<MyRepositoriesTable> repos = db.myRepositoriesDao().getMyRepos();

                for (MyRepositoriesTable repositoryData: repos){
                    Log.v("repository-query", repositoryData.getCreated_at()+" "+
                            repositoryData.getDescription()+" "+
                            repositoryData.getHtml_url()+" "+
                            repositoryData.getLanguage()+" "+
                            repositoryData.getName()+" "+
                            repositoryData.getScore()+" "+
                            repositoryData.getUpdated_at()+" "+
                            repositoryData.getWatchers());
                    data.add(new RepositoryData(repositoryData.getName(), repositoryData.getHtml_url(),
                            repositoryData.getDescription(), repositoryData.getCreated_at(),
                            repositoryData.getUpdated_at(), repositoryData.getLanguage(),
                            repositoryData.isHas_issues(), repositoryData.isHas_downloads(),
                            repositoryData.getWatchers(),repositoryData.getScore()));
                }
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Void>() {
                    @Override
                    public void onNext(Void aVoid) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("error", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Intent intent = new Intent(context,
                                RepositoriesActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("repos", (ArrayList<? extends Parcelable>) data);
                        intent.putExtra("repositories", bundle);
                        context.startActivity(intent);
                    }
                });

    }

    public MyRepositoryInt getmReposInt() {
        return mReposInt;
    }
}
