package com.prapps.pairheal.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.prapps.pairheal.R;
import com.prapps.pairheal.utils.Goal;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewGoalActivity extends AppCompatActivity {

    @BindView(R.id.et_title)
    EditText mGoalTitle;
    @BindView(R.id.et_desc)
    EditText mGoalDesc;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_goal);
        ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New Goal");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        uid = getIntent().getStringExtra("uid");

        findViewById(R.id.add_goal_button).setOnClickListener(v -> addGoal());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void addGoal() {
        if (mGoalTitle.getText().toString().length() == 0 ||
                mGoalDesc.getText().toString().length() == 0) {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            mProgressBar.setVisibility(View.VISIBLE);
            ParseObject goal = new ParseObject("Goal");
            goal.put("title", mGoalTitle.getText().toString());
            goal.put("desc", mGoalDesc.getText().toString());
            goal.put("uid", uid);
            goal.saveInBackground(e -> {
                mProgressBar.setVisibility(View.GONE);
                if (e == null) {
                    Toast.makeText(NewGoalActivity.this, "Goal saved", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            });
        }
    }


}
