package com.prapps.pairheal.utils;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.prapps.pairheal.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoalsAdapter extends RecyclerView.Adapter<GoalsAdapter.ViewHolder> {

    private List<ParseObject> goals;
    private boolean mine;

    public GoalsAdapter(List<ParseObject> goals, boolean mine) {
        this.goals = goals;
        this.mine = mine;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_goal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = goals.get(position);
        holder.mQtn.setText(holder.mItem.getString("title"));
        holder.mAns.setText(holder.mItem.getString("desc"));

        if (mine)
            holder.mDelete.setOnClickListener(v -> deleteObject(holder.mItem, holder.mDelete.getContext()));
        else
            holder.mDelete.setVisibility(View.GONE);
    }

    private void deleteObject(ParseObject mItem, Context context) {
        mItem.deleteInBackground(e -> {
            if (e == null) {
                Toast.makeText(context, "Goal successfully deleted", Toast.LENGTH_SHORT).show();
                goals.remove(mItem);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "Delete goal failed due to " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ParseObject mItem;
        @BindView(R.id.row_goal_title)
        TextView mQtn;
        @BindView(R.id.row_goal_desc)
        TextView mAns;
        @BindView(R.id.row_goal_delete)
        ImageView mDelete;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ButterKnife.bind(this, view);
        }

    }
}
