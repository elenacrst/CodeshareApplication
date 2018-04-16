package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.absolute.codepasta.retrofit.UserResponse;

/**
 * Created by Absolute on 12/11/2017.
 */

public interface UserInt {
    LiveData<UserResponse> getUser(String username, Context context);//implemented, handles the response of a request sent to server

}
