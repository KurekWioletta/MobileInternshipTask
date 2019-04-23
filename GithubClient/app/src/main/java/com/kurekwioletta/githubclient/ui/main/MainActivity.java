package com.kurekwioletta.githubclient.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.kurekwioletta.githubclient.R;
import com.kurekwioletta.githubclient.di.activity.HasActivitySubcomponentBuilders;
import com.kurekwioletta.githubclient.di.activity.components.MainActivityComponent;
import com.kurekwioletta.githubclient.di.activity.modules.MainActivityModule;
import com.kurekwioletta.githubclient.ui.base.BaseActivity;
import com.kurekwioletta.githubclient.ui.repositories_list.RepositoriesListActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Inject
    MainPresenter<MainContract.View> mainPresenter;

    @BindView(R.id.et_main_username)
    EditText etUsername;

    @BindView(R.id.pb_main_loading)
    ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUnBinder(ButterKnife.bind(this));

        mainPresenter.onAttach(this);
    }

    @Override
    protected void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders) {
        ((MainActivityComponent.Builder) hasActivitySubcomponentBuilders
                .getActivityComponentBuilder(MainActivity.class))
                .activityModule(new MainActivityModule(this))
                .build()
                .injectMembers(this);
    }

    @Override
    public void openRepositoriesListActivity(String username) {
        startActivity(RepositoriesListActivity.getStartIntent(this, username));
    }

    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onDestroy() {
        mainPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick(R.id.btn_main_load_repositories)
    public void onLoadRepositoriesClick(View v) {
        mainPresenter.onLoadRepositoriesClick(etUsername.getText().toString());
    }
}
