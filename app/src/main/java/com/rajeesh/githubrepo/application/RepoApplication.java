
package com.rajeesh.githubrepo.application;

import android.app.Application;

import com.rajeesh.githubrepo.di.components.ApplicationComponent;
import com.rajeesh.githubrepo.di.components.DaggerApplicationComponent;
import com.rajeesh.githubrepo.di.module.ApplicationModule;
import com.rajeesh.githubrepo.githubfile.GithubApp;
/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class RepoApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this, GithubApp.API_URL))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
