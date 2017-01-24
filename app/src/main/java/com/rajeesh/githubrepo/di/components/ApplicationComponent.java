
package com.rajeesh.githubrepo.di.components;

import android.content.Context;

import com.rajeesh.githubrepo.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

    Context exposeContext();
}
