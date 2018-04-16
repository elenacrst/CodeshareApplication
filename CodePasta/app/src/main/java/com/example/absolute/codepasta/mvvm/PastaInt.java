package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;

import com.example.absolute.codepasta.retrofit.MessageResponse;

/**
 * Created by Absolute on 12/18/2017.
 */

public interface PastaInt {
    LiveData<MessageResponse> postPasta(String filesContent, String description, boolean isPublic);
}
