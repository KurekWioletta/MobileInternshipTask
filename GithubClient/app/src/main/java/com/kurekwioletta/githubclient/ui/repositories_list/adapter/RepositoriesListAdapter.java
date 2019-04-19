package com.kurekwioletta.githubclient.ui.repositories_list.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.adapter.viewholder.RepositoriesListViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RepositoriesListAdapter extends RecyclerView.Adapter {

    private final List<Repository> mRepositoriesList = new ArrayList<>();

    private Context mContext;

    @Inject
    public RepositoriesListAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new RepositoriesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RepositoriesListViewHolder) holder).bindData(mRepositoriesList.get(position));

        holder.itemView.setOnClickListener(v -> onItemClicked(holder.getLayoutPosition()));
    }

    @Override
    public int getItemCount() {
        return mRepositoriesList.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.item_repository;
    }

    public void updateRepositoriesList(List<Repository> repositoriesList) {
        mRepositoriesList.addAll(repositoriesList);
        notifyDataSetChanged();
    }

    private void onItemClicked(int position) {
        ((RepositoriesListActivity) mContext).onRepositoryClicked(mRepositoriesList.get(position));
    }
}
