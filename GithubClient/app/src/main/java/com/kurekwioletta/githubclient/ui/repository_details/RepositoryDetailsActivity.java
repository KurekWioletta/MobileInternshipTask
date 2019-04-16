package com.kurekwioletta.githubclient.ui.repository_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.ui.base.BaseActivity;
import com.kurekwioletta.githubclient.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryDetailsActivity extends BaseActivity implements RepositoryDetailsContract.View {

    @Inject
    RepositoryDetailsPresenter<RepositoryDetailsContract.View> mPresenter;

    @BindView(R.id.tv_repository_details_name)
    TextView tvName;

    @BindView(R.id.tv_repository_details_description)
    TextView tvDescription;

    @BindView(R.id.tv_repository_details_watchers)
    TextView tvWatchers;

    @BindView(R.id.tv_repository_details_stars)
    TextView tvStars;

    @BindView(R.id.tv_repository_details_forks)
    TextView tvForks;

    @BindView(R.id.tv_repository_details_created)
    TextView tvCreated;

    @BindView(R.id.tv_repository_details_updated)
    TextView tvUpdated;

    @BindView(R.id.tv_repository_details_pushed)
    TextView tvPushed;

    @BindView(R.id.tv_repository_details_hyperlink)
    TextView tvHyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_details);

        getActivityComponent().inject(this);
        setUnBinder(ButterKnife.bind(this));

        mPresenter.onAttach(this);
        mPresenter.onViewInitialized(getIntent().getParcelableExtra(AppConstants.EXTRA_REPOSITORY));
    }

    @Override
    public void setUpRepositoryDetails(Repository repository) {
        tvName.setText(repository.getName());
        if(repository.getDescription() == null) {
            tvDescription.setVisibility(View.GONE);
        }
        else {
            tvDescription.setText(repository.getDescription());
        }
        tvWatchers.setText(String.valueOf(repository.getWatchersCount()));
        tvStars.setText(String.valueOf(repository.getStargazersCount()));
        tvForks.setText(String.valueOf(repository.getForksCount()));
        tvCreated.setText(repository.getCreatedAt().substring(0,10)); // yyyy-mm-dd
        tvUpdated.setText(repository.getUpdatedAt().substring(0,10));
        tvPushed.setText(repository.getPushedAt().substring(0,10));
        tvHyperlink.setText(repository.getHtmlUrl());
    }

    @Override
    protected void onStop() {
        mPresenter.onDetach();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryDetailsActivity.class);
        intent.putExtra(AppConstants.EXTRA_REPOSITORY,repository);
        return intent;
    }
}
