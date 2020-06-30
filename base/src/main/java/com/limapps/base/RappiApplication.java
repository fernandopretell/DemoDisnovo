package com.limapps.base;

import androidx.annotation.Nullable;
import androidx.lifecycle.ProcessLifecycleOwner;
import android.content.Context;
import androidx.multidex.MultiDex;

import com.limapps.base.di.BaseComponent;
import com.limapps.base.models.UserInfoModel;
import com.limapps.base.persistence.preferences.PreferencesManager;
import com.limapps.base.utils.ForegroundBackgroundListener;

import dagger.android.support.DaggerApplication;


public abstract class RappiApplication extends DaggerApplication {

    private static RappiApplication INSTANCE_;

    protected BaseComponent component;

    public static RappiApplication getInstance() {
        return INSTANCE_;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE_ = this;
    }

    /*@Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }*/

    /*public void initBackgroundListener(ForegroundBackgroundListener listener) {
        ProcessLifecycleOwner.get()
                .getLifecycle()
                .addObserver(listener);
    }

    public BaseComponent getComponent() {
        return component;
    }

    public @Nullable  UserInfoModel getUser() {
        return PreferencesManager.INSTANCE.getUserProfile();
    }*/

}