package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.example.absolute.codepasta.retrofit.RepositoryData;

import java.util.List;

/**
 * Created by Absolute on 12/18/2017.
 */

public interface MyRepositoryInt {
    MutableLiveData<List<RepositoryData>> getRepositories(String username, Context context);
}
