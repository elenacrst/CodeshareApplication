package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.absolute.codepasta.ui.PostActivity;
import com.example.absolute.codepasta.retrofit.MessageResponse;

/**
 * Created by Absolute on 12/18/2017.
 */

public class PastaViewModel extends ViewModel{

    private MediatorLiveData<MessageResponse> mMessageResponse;
    private PastaInt mPastaInt;

    // No argument constructor
    public PastaViewModel() {
        mMessageResponse = new MediatorLiveData<>();
        mPastaInt = new PastaIntImpl();

    }

    @NonNull
    public LiveData<MessageResponse> getMessageResponse() {
        return mMessageResponse;
    }

    public void postPasta(String filesContent, String description, boolean isPublic) {
        mMessageResponse.addSource(
                mPastaInt.postPasta(filesContent, description, isPublic), new Observer<MessageResponse>() {
                    @Override
                    public void onChanged(@Nullable MessageResponse messageResponse) {
                        Log.wtf("message"," response "+messageResponse);
                        if (messageResponse != null){
                            PostActivity.showUrlText(messageResponse.getHtml_url());
                        }else{
                            PostActivity.hideUrlText();
                        }
                    }
                }
        );
    }

    public PastaInt getmPastaInt() {
        return mPastaInt;
    }
}
