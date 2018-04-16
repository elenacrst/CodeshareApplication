package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

import com.example.absolute.codepasta.retrofit.RepositoryData;
import com.example.absolute.codepasta.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Absolute on 12/18/2017.
 */

public class MyRepositoryIntImpl implements MyRepositoryInt {
    public static final String BASE_URL = "https://api.github.com/";
    private UserService mUserService;

    public static CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource(
            "My_Repos_Call");

    public MyRepositoryIntImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mUserService = retrofit.create(UserService.class);
    }

    @Override
    public MutableLiveData<List<RepositoryData>> getRepositories(String username, final Context context) {
        final MutableLiveData<List<RepositoryData>> liveData = new MutableLiveData<>();
        Call<List<RepositoryData>> call = mUserService.getMyRepositories(username);
        espressoTestIdlingResource.increment();
        call.enqueue(new Callback<List<RepositoryData>>() {
            @Override
            public void onResponse(@NonNull Call<List<RepositoryData>> call, @NonNull Response<List<RepositoryData>> response) {
                List<RepositoryData> reposList = new ArrayList<>();
                if (response.body()!=null){
                    reposList.addAll(response.body());
                    liveData.setValue(reposList);
                    MyRepositoryViewModel.handleResponse(reposList, context);
                }
                Log.v("myrepos",response.body()+" ");

            }

            @Override
            public void onFailure(@NonNull Call<List<RepositoryData>> call, @NonNull Throwable t) {
                MyRepositoryViewModel.handleError(context);
                Log.v("myrepos",t.toString()+" ");
            }
        });
        return liveData;
    }

    public CountingIdlingResource getEspressoTestIdlingResource() {
        return espressoTestIdlingResource;
    }
}
