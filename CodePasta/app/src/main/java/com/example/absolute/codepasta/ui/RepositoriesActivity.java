package com.example.absolute.codepasta.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.absolute.codepasta.R;
import com.example.absolute.codepasta.adapters.ReposAdapter;
import com.example.absolute.codepasta.mvvm.MyRepositoryIntImpl;
import com.example.absolute.codepasta.mvvm.RepositoryIntImpl;
import com.example.absolute.codepasta.retrofit.RepositoryData;

import java.util.ArrayList;
import java.util.List;

public class RepositoriesActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    private static RecyclerView mRecyclerViewRepos;
    private static ReposAdapter mReposAdapter;

    private List<RepositoryData> mData = new ArrayList<>();

    private static TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories);

        setupRecyclerView();

        mEmptyTextView = (TextView) findViewById(R.id.empty_text_view);

        Log.v("called repository","repositories activity");

        if (getIntent().hasExtra("repositories")){
            Bundle data  = getIntent().getBundleExtra("repositories");
            if (data.containsKey("repos")){
                mData = data.getParcelableArrayList("repos");
                refreshUI(mData);
            }
        }

        if (UserProfileActivity.isRecentRepos){
            RepositoryIntImpl.espressoTestIdlingResource.decrement();
        }else{
            MyRepositoryIntImpl.espressoTestIdlingResource.decrement();
        }

    }

    public static void refreshUI(List<RepositoryData> data){
        Log.v("refreshed repositories", data.size()+"");
        mReposAdapter = new ReposAdapter(data);
        mReposAdapter.notifyDataSetChanged();

        mRecyclerViewRepos.setAdapter(mReposAdapter);
        if ( data.size()==0){
            mEmptyTextView.setVisibility(View.VISIBLE);
            mRecyclerViewRepos.setVisibility(View.GONE);
        }else{
            mEmptyTextView.setVisibility(View.GONE);
            mRecyclerViewRepos.setVisibility(View.VISIBLE);
        }
    }

    public void setupRecyclerView(){
        mRecyclerViewRepos = (RecyclerView) findViewById(R.id.recyclerview_repos);
        mRecyclerViewRepos.setLayoutManager(layoutManager);
        mRecyclerViewRepos.setHasFixedSize(true);
    }
}
