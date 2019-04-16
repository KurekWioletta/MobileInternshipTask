package com.kurekwioletta.githubclient.ui.repositories_list.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_repository_name)
    TextView tvName;

    @BindView(R.id.tv_repository_stars)
    TextView tvStars;

    public RepositoriesListViewHolder(@NonNull View parent) {
        super(parent);
        ButterKnife.bind(this, parent);
    }

    public void bindData(final Repository repository) {
        tvName.setText(repository.getName());
        tvStars.setText(String.valueOf(repository.getStargazersCount()));
    }

}
