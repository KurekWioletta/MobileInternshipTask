package com.kurekwioletta.githubclient.ui.repository_details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.data.model.Repository;
import com.kurekwioletta.githubclient.di.activity.HasActivitySubcomponentBuilders;
import com.kurekwioletta.githubclient.di.activity.components.RepositoryDetailsActivityComponent;
import com.kurekwioletta.githubclient.di.activity.modules.RepositoryDetailsActivityModule;
import com.kurekwioletta.githubclient.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepositoryDetailsActivity extends BaseActivity implements RepositoryDetailsContract.View {

    private static final String EXTRA_REPOSITORY = "com.kurekwioletta.githubclient.EXTRA_REPOSITORY";

    @Inject
    RepositoryDetailsPresenter<RepositoryDetailsContract.View> repositoryDetailsPresenter;

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

        setUnBinder(ButterKnife.bind(this));

        repositoryDetailsPresenter.onAttach(this);
        repositoryDetailsPresenter.onViewInitialized(getIntent().getParcelableExtra(EXTRA_REPOSITORY));
    }

    @Override
    protected void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders) {
        ((RepositoryDetailsActivityComponent.Builder) hasActivitySubcomponentBuilders
                .getActivityComponentBuilder(RepositoryDetailsActivity.class))
                .activityModule(new RepositoryDetailsActivityModule(this))
                .build()
                .injectMembers(this);
    }

    @Override
    public void setUpRepositoryDetails(Repository repository) {
        tvName.setText(repository.getName());
        if (repository.getDescription() == null) {
            tvDescription.setVisibility(View.GONE);
        } else {
            tvDescription.setText(repository.getDescription());
        }
        tvWatchers.setText(String.valueOf(repository.getWatchersCount()));
        tvStars.setText(String.valueOf(repository.getStargazersCount()));
        tvForks.setText(String.valueOf(repository.getForksCount()));
        tvCreated.setText(repository.getCreatedAt().substring(0, 10)); // yyyy-mm-dd
        tvUpdated.setText(repository.getUpdatedAt().substring(0, 10));
        tvPushed.setText(repository.getPushedAt().substring(0, 10));
        tvHyperlink.setText(repository.getHtmlUrl());
    }

    @Override
    protected void onDestroy() {
        repositoryDetailsPresenter.onDetach();
        super.onDestroy();
    }

    public static Intent getStartIntent(Context context, Repository repository) {
        Intent intent = new Intent(context, RepositoryDetailsActivity.class);
        intent.putExtra(EXTRA_REPOSITORY, repository);
        return intent;
    }
}
