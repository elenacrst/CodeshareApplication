package com.example.absolute.codepasta.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.absolute.codepasta.R;
import com.example.absolute.codepasta.mvvm.UserIntImpl;
import com.example.absolute.codepasta.mvvm.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;

    private UserViewModel mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUserModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUsernameEditText = (EditText) findViewById(R.id.editTextUsername);

    }

    public void doneLogin(View view) {
        if (mUsernameEditText.getText()==null || !(mUsernameEditText.getText().toString().length()>0)){
            mUsernameEditText.setHintTextColor(getResources().getColor(R.color.colorAccent));
        }else{
            final String input = mUsernameEditText.getText().toString();
            mUserModel.loadUser(input, this);
        }
    }

    public CountingIdlingResource getEspressoIdlingResourceForMainActivity() {
        if (mUserModel != null){
            return ((UserIntImpl)mUserModel.getmUserInt()).getEspressoTestIdlingResource();
        }
        return null;
    }

}
