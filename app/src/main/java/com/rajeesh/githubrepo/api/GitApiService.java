
package com.rajeesh.githubrepo.api;

import com.rajeesh.githubrepo.mvp.model.Repo;
import com.rajeesh.githubrepo.mvp.model.RepoDetails;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public interface GitApiService {
    @GET
    Observable<List<Repo>> getData(@Url String url);

    @GET
    Observable<RepoDetails> getRepoData(@Url String url);

}
