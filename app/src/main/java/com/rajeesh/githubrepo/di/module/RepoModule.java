
package com.rajeesh.githubrepo.di.module;

import com.rajeesh.githubrepo.api.GitApiService;
import com.rajeesh.githubrepo.di.scope.PerActivity;
import com.rajeesh.githubrepo.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
@Module
public class RepoModule {

    private MainView mView;

    public RepoModule(MainView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    GitApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(GitApiService.class);
    }

    @PerActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
