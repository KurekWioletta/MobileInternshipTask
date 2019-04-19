package com.kurekwioletta.githubclient.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kurekwioletta.githubclient.GithubClientApp;
import com.kurekwioletta.githubclient.di.activity.HasActivitySubcomponentBuilders;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpActivityComponent();
    }

    protected void setUpActivityComponent() {
        injectMembers(GithubClientApp.get(this));
    }

    protected abstract void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders);

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
