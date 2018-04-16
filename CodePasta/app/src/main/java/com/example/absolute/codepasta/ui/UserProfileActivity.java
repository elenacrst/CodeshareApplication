package com.example.absolute.codepasta.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.absolute.codepasta.R;
import com.example.absolute.codepasta.mvvm.MyRepositoryIntImpl;
import com.example.absolute.codepasta.mvvm.MyRepositoryViewModel;
import com.example.absolute.codepasta.mvvm.RepositoryIntImpl;
import com.example.absolute.codepasta.mvvm.RepositoryViewModel;
import com.example.absolute.codepasta.retrofit.UserResponse;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends AppCompatActivity {

    private ImageView mAvatarImageView;
    private TextView mUsernameTextView, mCompanyTextView, mNameTextView, mLocationTextView,
            mHireableTextView, mFollowersTextView, mFollowingTextView, mReposTextView,
            mProfileLinkTextView, mCreatedTextView, mUpdatedTextView;

    private static RepositoryViewModel mRepositoryModel;

    private static MyRepositoryViewModel mMyReposModel;

    private String mCurrentUsername;

    public static boolean isRecentRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        findViews();

        if (getIntent().hasExtra("userresponse")){
            Bundle data  = getIntent().getBundleExtra("userresponse");
            if (data.containsKey("user")){
                UserResponse response = data.getParcelable("user");
                if (response != null) mCurrentUsername = response.getLogin();
                setViews(response);
            }
        }

        mRepositoryModel = ViewModelProviders.of(this).get(RepositoryViewModel.class);

        mMyReposModel = ViewModelProviders.of(this).get(MyRepositoryViewModel.class);

    }

    private void findViews(){
        mAvatarImageView = (ImageView) findViewById(R.id.avatar_image);
        mUsernameTextView = (TextView) findViewById(R.id.username_text);
        mCompanyTextView = (TextView)findViewById(R.id.company_text);
        mNameTextView = (TextView) findViewById(R.id.name_text);
        mLocationTextView = (TextView) findViewById(R.id.location_text);
        mHireableTextView = (TextView)findViewById(R.id.hireable_text);
        mFollowersTextView = (TextView)findViewById(R.id.followers_text);
        mFollowingTextView = (TextView)findViewById(R.id.following_text);
        mReposTextView = (TextView) findViewById(R.id.public_repos_text);
        mProfileLinkTextView = (TextView) findViewById(R.id.profile_link_text);
        mCreatedTextView = (TextView) findViewById(R.id.created_at_text);
        mUpdatedTextView  = (TextView)findViewById(R.id.updated_at_text);

    }

    private void setViews(UserResponse user){
        Picasso.with(this).load(user.getAvatar_url())
                .into(mAvatarImageView);
        mUsernameTextView.setText(user.getLogin());
        if (user.getCompany() != null){
            mCompanyTextView.setText(user.getCompany());
        }
        if (user.getName() != null){
            mNameTextView.setText(user.getName());
        }
        if (user.getLocation() != null){
            mLocationTextView.setText(user.getLocation());
        }
        if (user.isHireable()){
            mHireableTextView.setText(String.format("%s", user.isHireable()));
        }
        mFollowersTextView.setText(String.format("%d", user.getFollowers()));
        mFollowingTextView.setText(String.format("%d", user.getFollowing()));
        mReposTextView.setText(String.format("%d", user.getPublic_repos()));
        mProfileLinkTextView.setText(user.getHtml_url());
        mCreatedTextView.setText(user.getCreated_at());
        if (user.getUpdated_at() != null){
            mUpdatedTextView.setText(user.getUpdated_at());
        }

    }

    public void recentPastasClick(View view) {
        isRecentRepos = true;
        mRepositoryModel.loadRepos(this);
    }

    public void userPastasClick(View view) {
        isRecentRepos = false;
        mMyReposModel.loadRepos(mCurrentUsername, this);
    }

    public void postPastaClick(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    public static CountingIdlingResource getEspressoIdlingResourceForProfileActivity() {
        if (isRecentRepos && mRepositoryModel != null){
            return ((RepositoryIntImpl)mRepositoryModel.getmReposInt()).getEspressoTestIdlingResource();
        }else if (!isRecentRepos && mMyReposModel!=null){
            return ((MyRepositoryIntImpl)mMyReposModel.getmReposInt()).getEspressoTestIdlingResource();
        }
        return null;
    }
}
