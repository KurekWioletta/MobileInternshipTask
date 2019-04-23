package com.kurekwioletta.githubclient.ui.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    public void showMessage(int resId) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(getResources().getString(resId));
        alertDialog.setButton(
                DialogInterface.BUTTON_NEUTRAL,
                "OK",
                (dialog, id) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    protected void setUpActivityComponent() {
        injectMembers(GithubClientApp.get(this));
    }

    protected abstract void injectMembers(HasActivitySubcomponentBuilders hasActivitySubcomponentBuilders);

    protected void setUnBinder(Unbinder unBinder) {
        mUnbinder = unBinder;
    }
}
