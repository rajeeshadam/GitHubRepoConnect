

package com.rajeesh.githubrepo.di.components;

import com.rajeesh.githubrepo.di.module.RepoModule;
import com.rajeesh.githubrepo.di.scope.PerActivity;
import com.rajeesh.githubrepo.modules.details.DetailActivity;
import com.rajeesh.githubrepo.modules.home.RepoActivity;

import dagger.Component;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */

@PerActivity
@Component(modules = RepoModule.class, dependencies = ApplicationComponent.class)
public interface RepoComponent {

    void inject(RepoActivity activity);
    void inject(DetailActivity activity);
}
