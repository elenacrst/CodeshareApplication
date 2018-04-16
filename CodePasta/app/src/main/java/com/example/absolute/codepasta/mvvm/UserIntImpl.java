package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

import com.example.absolute.codepasta.retrofit.UserResponse;
import com.example.absolute.codepasta.retrofit.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Absolute on 12/11/2017.
 */

public class UserIntImpl implements UserInt {

    public static final String BASE_URL = "https://api.github.com/";
    private UserService mUserService;

    private CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource(
            "User_Call");

    public UserIntImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
               // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mUserService = retrofit.create(UserService.class);
    }

    @Override
    public LiveData<UserResponse> getUser(final String username, final Context context) {
        final MutableLiveData<UserResponse> liveData = new MutableLiveData<>();
        Call<UserResponse> call = mUserService.getUserResponse(username);

        espressoTestIdlingResource.increment();

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.isSuccessful()){
                    liveData.setValue(new UserResponse(response.body()));
                    //got user response here, handle it todo
                    UserViewModel.handleResponse(response.body(),context);
                }else{
                    UserViewModel.queryUserDb(username, context);
                }
                espressoTestIdlingResource.decrement();

            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t) {
                Log.w("userintimpl", t.toString());

                UserViewModel.queryUserDb(username, context);
            }
        });
        return liveData;
    }

    public CountingIdlingResource getEspressoTestIdlingResource() {
        return espressoTestIdlingResource;
    }
}
