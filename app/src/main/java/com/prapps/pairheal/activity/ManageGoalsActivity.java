package com.prapps.pairheal.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.prapps.pairheal.R;
import com.prapps.pairheal.utils.GoalsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ManageGoalsActivity extends AppCompatActivity {

    @BindView(R.id.list)
    RecyclerView mList;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    String uid = "";
    private List<ParseObject> goals;
    private GoalsAdapter mGoalsAdapter;
    private boolean mine = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_manage_goals);
        ButterKnife.bind(this);
        mine = getIntent().getBooleanExtra("mine", false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mine ? "Manage Goals" : "See Goals");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        uid = getIntent().getStringExtra("uid");
        mList.setLayoutManager(new LinearLayoutManager(this));
        mProgressBar.setVisibility(View.VISIBLE);
        getGoals();
    }

    private void getGoals() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Goal");
        query.whereEqualTo("uid", uid);
        query.findInBackground((goals, e) -> {
            mProgressBar.setVisibility(View.GONE);
            if (e == null) {
                Log.i("ROYCE", "Retrieved " + goals.size() + " goals");
                if (goals.size() != 0)
                    setAdapter(goals);
            } else {
                Log.i("ROYCE", "Error: " + e.getMessage());
            }
        });
    }

    private void setAdapter(List<ParseObject> goals) {
        this.goals = goals;
        mGoalsAdapter = new GoalsAdapter(goals,mine);
        mList.setAdapter(mGoalsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
