package com.example.absolute.codepasta.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
               // .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public UserService getUserService() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(UserService.class);
    }
}
