package com.kurekwioletta.githubclient.ui.repositories_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.base.BaseActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.adapter.RepositoriesListAdapter;
import com.kurekwioletta.githubclient.ui.repository_details.RepositoryDetailsActivity;
import com.kurekwioletta.githubclient.utils.AppConstants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoriesListActivity extends BaseActivity implements RepositoriesListContract.View {

    private static final String EXTRA_USERNAME = "com.kurekwioletta.githubclient.EXTRA_USERNAME";

    @Inject
    RepositoriesListPresenter<RepositoriesListContract.View> mPresenter;

    @Inject
    RepositoriesListAdapter mAdapter;

    @Inject
    LinearLayoutManager mLinearLayoutManager;

    @BindView(R.id.rv_repositories_list)
    RecyclerView rvRepositoriesList;

    @BindView(R.id.cl_repositories_list_loading)
    ConstraintLayout clLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);
        setUpRepositoriesListRecyclerView();
        mPresenter.onViewInitialized(getIntent().getStringExtra(EXTRA_USERNAME));
    }

    @Override
    public void updateRepositoriesListRecyclerView(List<Repository> repositoriesList) {
        mAdapter.updateRepositoriesList(repositoriesList);
    }

    @Override
    public void showLoading() {
        clLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        clLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    public void onRepositoryClicked(Repository repository) {
        startActivity(RepositoryDetailsActivity.getStartIntent(this, repository));
    }

    public static Intent getStartIntent(Context context, String username) {
        Intent intent = new Intent(context, RepositoriesListActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        return intent;
    }

    private void setUpRepositoriesListRecyclerView() {
        rvRepositoriesList.setLayoutManager(mLinearLayoutManager);
        rvRepositoriesList.setAdapter(mAdapter);
    }
}
