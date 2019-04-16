package com.kurekwioletta.githubclient.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kurekwioletta.githubclient.GithubClientApp;
import com.kurekwioletta.githubclient.di.components.ActivityComponent;
import com.kurekwioletta.githubclient.di.components.DaggerActivityComponent;
import com.kurekwioletta.githubclient.di.modules.activity.BaseActivityModule;

import butterknife.Unbinder;

public class BaseActivity extends AppCompatActivity implements MvpView {

    private Unbinder mUnbinder;
    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityComponent = DaggerActivityComponent.builder()
                .baseActivityModule(new BaseActivityModule(this))
                .appComponent(((GithubClientApp) getApplication()).getComponent())
                .build();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void showMessage(int resId) {
        Toast.makeText(this, getResources().getString(resId), Toast.LENGTH_SHORT).show();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnbinder = unBinder;
    }
}
