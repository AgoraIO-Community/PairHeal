package com.prapps.pairheal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prapps.pairheal.R;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    FirebaseUser mCurrentUser;
    private String uid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        if (mCurrentUser != null) {
//            Log.i("PAIRHEAL", mCurrentUser.getDisplayName());
            Log.i("ROYCE", mCurrentUser.getUid());
            uid = mCurrentUser.getUid();
//            Log.i("PAIRHEAL",mCurrentUser.getDisplayName());
        }

        findViewById(R.id.layout_add_goal).setOnClickListener(this);
        findViewById(R.id.layout_call_pairheal).setOnClickListener(this);
        findViewById(R.id.layout_manage_goals).setOnClickListener(this);
        findViewById(R.id.layout_see_goals).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_add_goal:
                startActivity(new Intent(this, NewGoalActivity.class).
                        putExtra("uid", uid));
                break;
            case R.id.layout_call_pairheal:
                startActivity(new Intent(this, CallTwinActivity.class).
                        putExtra("uid", uid));
                break;
            case R.id.layout_manage_goals:
                startActivity(new Intent(this, ManageGoalsActivity.class).
                        putExtra("mine", true).
                        putExtra("uid", uid));
                break;
            case R.id.layout_see_goals:
                startActivity(new Intent(this, ManageGoalsActivity.class).
                        putExtra("mine", false).
                        putExtra("uid", "dJZo1kmxCvSxNNhHErrgiOrROXB3"));
                break;
        }
    }
}
