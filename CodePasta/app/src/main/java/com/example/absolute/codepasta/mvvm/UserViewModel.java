package com.example.absolute.codepasta.mvvm;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.absolute.codepasta.ui.UserProfileActivity;
import com.example.absolute.codepasta.data.AppDatabase;
import com.example.absolute.codepasta.data.UserTable;
import com.example.absolute.codepasta.retrofit.UserResponse;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Absolute on 12/11/2017.
 */

public class UserViewModel extends ViewModel {
    private MediatorLiveData<UserResponse> mUserResponse;
    private UserInt mUserInt;

    // No argument constructor
    public UserViewModel() {
        mUserResponse = new MediatorLiveData<>();
        mUserInt = new UserIntImpl();

    }

    @NonNull
    public LiveData<UserResponse> getUserResponse() {
        return mUserResponse;
    }

    public void loadUser(final String username, final Context context) {

        mUserResponse.addSource(
                mUserInt.getUser(username, context), new Observer<UserResponse>() {
                    @Override
                    public void onChanged(@Nullable UserResponse userResponse) {
                        Log.v("changed","user");
                        if (userResponse!=null){
                            mUserResponse.setValue(userResponse);
                            handleResponse(userResponse, context);
                        }else{
                            handleError(context);
                            queryUserDb(username, context);
                        }
                    }
                }
        );
    }

    public static void queryUserDb(final String input, final Context context){

        Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() {
                AppDatabase db = Room
                        .databaseBuilder(context, AppDatabase.class, "pasta-database")
                        .fallbackToDestructiveMigration()
                        .build();
                UserTable userLoggedIn = db.userDao()
                        .getUser(input);
                if (userLoggedIn != null )
                {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setAvatar_url(userLoggedIn.getAvatar_url());
                    userResponse.setBio(userLoggedIn.getBio());
                    userResponse.setCompany(userLoggedIn.getCompany());
                    userResponse.setCreated_at(userLoggedIn.getCreated_at());
                    userResponse.setFollowers(userLoggedIn.getFollowers());
                    userResponse.setFollowing(userLoggedIn.getFollowing());
                    userResponse.setHireable(userLoggedIn.isHireable());
                    userResponse.setHtml_url(userLoggedIn.getHtml_url());
                    userResponse.setLocation(userLoggedIn.getLocation());
                    userResponse.setLogin(userLoggedIn.getLogin());
                    userResponse.setName(userLoggedIn.getName());
                    userResponse.setPublic_repos(userLoggedIn.getPublic_repos());
                    userResponse.setUpdated_at(userLoggedIn.getUpdated_at());
                    handleResponse(userResponse, context);
                }
                return null;
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
    }

    public static void addUser(final AppDatabase db, final UserTable user) {

        Observable.fromCallable(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                UserTable previousUser = db.userDao().getUser(user.getLogin());
                if (previousUser != null) db.userDao().deleteUser(previousUser);
                db.userDao().insertUser(user);
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static void handleResponse(UserResponse userResponse, Context context){
        AppDatabase db = Room
                .databaseBuilder(context, AppDatabase.class, "pasta-database")
                .fallbackToDestructiveMigration()
                .build();
        Log.v("handle response","user"+userResponse.getLogin());
        UserTable userTable = new UserTable();
        userTable.setLogin(userResponse.getLogin());
        userTable.setAvatar_url(userResponse.getAvatar_url());
        userTable.setHtml_url(userResponse.getHtml_url());
        userTable.setName(userResponse.getName());
        userTable.setCompany(userResponse.getCompany());
        userTable.setHireable(userResponse.isHireable());
        userTable.setBio(userResponse.getBio());
        userTable.setPublic_repos(userResponse.getPublic_repos());
        userTable.setFollowers(userResponse.getFollowers());
        userTable.setFollowing(userResponse.getFollowing());
        userTable.setCreated_at(userResponse.getCreated_at());
        userTable.setUpdated_at(userResponse.getUpdated_at());
        userTable.setLocation(userResponse.getLocation());
        addUser(db, userTable );

        Intent intent = new Intent(context,
                UserProfileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", userResponse);
        intent.putExtra("userresponse", bundle);
        context.startActivity(intent);
    }

    private void handleError(Context context){
        Toast.makeText(context, "Login failed", Toast.LENGTH_LONG).show();
    }

    public UserInt getmUserInt() {
        return mUserInt;
    }
}
