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
import com.example.absolute.codepasta.data.RecentRepositoriesTable;
import com.example.absolute.codepasta.retrofit.RepositoryData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Absolute on 12/12/2017.
 */

public class RepositoryViewModel extends ViewModel {
    private MediatorLiveData<List<RepositoryData>> mRepositoryData;
    private RepositoryInt mReposInt;

    // No argument constructor
    public RepositoryViewModel() {
        mRepositoryData = new MediatorLiveData<>();
        mReposInt = new RepositoryIntImpl();

    }

    @NonNull
    public LiveData<List<RepositoryData>> getRepositories() {
        return mRepositoryData;
    }

    public void loadRepos(final Context context) {
        Log.v("loadrepository", "called");
        mRepositoryData.addSource(

                mReposInt.getRepositories(context), new Observer<List<RepositoryData>>() {
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

                    /*@Override
                    public void onChanged(@Nullable RepositoryResponse repos) {
                        Log.v("repositorychange", "called");
                        if (repos!=null){
                            List<RepositoryData> repositoryData = repos.getItems();
                            mRepositoryData.setValue(repositoryData);
                            handleResponse(repos, context);
                        }else{
                            handleError(context);

                        }
                    }*/
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

        Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = Room
                        .databaseBuilder(context, AppDatabase.class, "pasta-database")
                        .fallbackToDestructiveMigration()
                        .build();
                List<RecentRepositoriesTable> recentRepos = db.recentRepositoriesDao().getRecentRepos();
                db.recentRepositoriesDao().delete(recentRepos);
                for (RepositoryData repo: data){
                    RecentRepositoriesTable newRecentRepo = new RecentRepositoriesTable(repo.getName(),
                            repo.getHtml_url(), repo.getDescription(), repo.getCreated_at(),repo.getUpdated_at(),
                            repo.getLanguage(), repo.isHas_issues(), repo.isHas_downloads(), repo.getWatchers(),
                            repo.getScore());
                    db.recentRepositoriesDao().insertRecentRepo(newRecentRepo);
                }
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static void handleError(final Context context) {

        Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show();

        //no network connection => try to query db
        final List<RepositoryData> data= new ArrayList<>();

        Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                AppDatabase db = Room
                        .databaseBuilder(context,AppDatabase.class, "pasta-database")
                        .fallbackToDestructiveMigration()
                        .build();
                List<RecentRepositoriesTable> recentRepos = db.recentRepositoriesDao().getRecentRepos();

                for (RecentRepositoriesTable repositoryData: recentRepos){
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

    public RepositoryInt getmReposInt() {
        return mReposInt;
    }
}
