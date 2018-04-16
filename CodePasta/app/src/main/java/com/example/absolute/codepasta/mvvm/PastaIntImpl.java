package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.util.Log;

import com.example.absolute.codepasta.ui.PostActivity;
import com.example.absolute.codepasta.retrofit.Files;
import com.example.absolute.codepasta.retrofit.MessageResponse;
import com.example.absolute.codepasta.retrofit.MyFile;
import com.example.absolute.codepasta.retrofit.PastaData;
import com.example.absolute.codepasta.retrofit.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Absolute on 12/18/2017.
 */

public class PastaIntImpl implements PastaInt {

    public static final String BASE_URL = "https://api.github.com/";
    private UserService mUserService;

    private CountingIdlingResource espressoTestIdlingResource = new CountingIdlingResource(
            "Post_Call");

    public PastaIntImpl(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mUserService = retrofit.create(UserService.class);
    }

    @Override
    public LiveData<MessageResponse> postPasta(String filesContent, String description, boolean isPublic) {
        final MutableLiveData<MessageResponse> liveData = new MutableLiveData<>();
        Files files = new Files();
        MyFile myFile = new MyFile();
        myFile.setContent(filesContent);
        files.setFile(myFile);
        Call<MessageResponse> call = mUserService.savePost(
                new PastaData(
                        files,
                        description,
                        isPublic
                )
        );
        espressoTestIdlingResource.increment();
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MessageResponse> call, @NonNull Response<MessageResponse> response) {
                Log.v("resp code",response.code()+" code, message "+response);

                if (response.code() == 201){
                    if (response.body() != null){
                        liveData.setValue(response.body());
                        PostActivity.showUrlText(response.body().getHtml_url()+"");
                    }else{
                        PostActivity.hideUrlText();
                    }

                }else{
                    PostActivity.hideUrlText();
                }
                espressoTestIdlingResource.decrement();
            }

            @Override
            public void onFailure(@NonNull Call<MessageResponse> call, @NonNull Throwable t) {
                PostActivity.hideUrlText();
            }
        });

        return liveData;
    }

    public CountingIdlingResource getEspressoTestIdlingResource() {
        return espressoTestIdlingResource;
    }
}
