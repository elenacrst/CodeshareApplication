package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.espresso.idling.CountingIdlingResource;

import com.example.absolute.codepasta.retrofit.RepositoryData;
import com.example.absolute.codepasta.retrofit.RepositoryResponse;
import com.example.absolute.codepasta.retrofit.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Absolute on 12/12/2017.
 */

public class RepositoryIntImpl implements RepositoryInt{
    public static final String BASE_URL = "https://api.github.com/";
    private UserService mUserService;

    public static CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource(
            "Recent_Repos_Call");

    public RepositoryIntImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mUserService = retrofit.create(UserService.class);
    }

    @Override
    public MutableLiveData<List<RepositoryData>> getRepositories(final Context context) {
        final MutableLiveData<List<RepositoryData>> liveData = new MutableLiveData<>();
        Call<RepositoryResponse> call = mUserService.getRepositories();
        espressoTestIdlingResource.increment();
        call.enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(@NonNull Call<RepositoryResponse> call, @NonNull Response<RepositoryResponse> response) {
                List<RepositoryData> reposList = new ArrayList<>();
                if (response.body() != null && response.body().getItems()!=null){
                    reposList.addAll(response.body().getItems());
                    liveData.setValue(reposList);
                    RepositoryViewModel.handleResponse(reposList, context);
                }
                //espressoTestIdlingResource.decrement();
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                RepositoryViewModel.handleError(context);
              //  espressoTestIdlingResource.decrement();
            }
        });
        return liveData;
    }

    public CountingIdlingResource getEspressoTestIdlingResource() {
        return espressoTestIdlingResource;
    }

}
