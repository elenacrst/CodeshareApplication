package com.example.absolute.codepasta.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.support.design.widget.TextInputEditText;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.absolute.codepasta.R;
import com.example.absolute.codepasta.mvvm.PastaIntImpl;
import com.example.absolute.codepasta.mvvm.PastaViewModel;

public class PostActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private TextInputEditText mDescriptionEditText;
    private Switch mPrivateSwitch;

    private static TextView mLinkTitleTextView;
    private static TextView mLinkTextView;

    private PastaViewModel mPastaModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        findViews();

        mPastaModel = ViewModelProviders.of(this).get(PastaViewModel.class);
    }

    public void addNewPasta(View view) {
        boolean isValid=true;
        if (mNameEditText.getText() == null || mNameEditText.getText().toString().length()==0){
            mNameEditText.setHintTextColor(getResources().getColor(R.color.colorAccent));
            isValid = false;
        }
        if (isValid){
            mPastaModel.postPasta(mNameEditText.getText().toString(),
                    mDescriptionEditText.getText().toString(),
                    mPrivateSwitch.isChecked());

        }
    }

    private void findViews(){
        mNameEditText  = (EditText) findViewById(R.id.pasta_content_title);
        mDescriptionEditText = (TextInputEditText) findViewById(R.id.description_edit_text);
        mPrivateSwitch  =  (Switch) findViewById(R.id.private_switch);
        mLinkTextView = (TextView)findViewById(R.id.link_pasta_text);
        mLinkTitleTextView = (TextView) findViewById(R.id.link_pasta_text_title);
    }

    public static void showUrlText(String url){
        mLinkTextView.setText(url);
        mLinkTextView.setVisibility(View.VISIBLE);
        mLinkTitleTextView.setVisibility(View.VISIBLE);
    }

    public static void hideUrlText(){
        Toast.makeText(mLinkTextView.getContext(), "An error occurred.", Toast.LENGTH_LONG).show();
        mLinkTextView.setVisibility(View.GONE);
        mLinkTitleTextView.setVisibility(View.GONE);
    }

    public CountingIdlingResource getEspressoIdlingResourceForPostActivity() {
        if (mPastaModel != null){
            return ((PastaIntImpl)mPastaModel.getmPastaInt()).getEspressoTestIdlingResource();
        }
        return null;
    }


}
