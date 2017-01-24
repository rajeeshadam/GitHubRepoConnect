
package com.rajeesh.githubrepo.mvp.presenter;

import com.rajeesh.githubrepo.api.GitApiService;
import com.rajeesh.githubrepo.base.BasePresenter;
import com.rajeesh.githubrepo.mvp.model.RepoDetails;
import com.rajeesh.githubrepo.mvp.view.MainView;
import com.rajeesh.githubrepo.utilities.GlobalConstants;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class RepoDetailsPresenter extends BasePresenter<MainView> implements Observer<RepoDetails> {

    @Inject
    protected GitApiService mApiService;
    @Inject
    public RepoDetailsPresenter() {

    }

    public void getRepos() {
        getView().onShowDialog("Loading Repo...."+ GlobalConstants.REPO_DETAIL_URL);
        Observable<RepoDetails> repoDetail = mApiService.getRepoData(GlobalConstants.REPO_DETAIL_URL);
        subscribe(repoDetail,this);
    }


    @Override
    public void onCompleted() {
        getView().onHideDialog();
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onError("Error loading repos " + e.getMessage());
    }

    @Override
    public void onNext(RepoDetails repoDetails) {
        getView().onClearItems();
        getView().onRepoDetailLoaded(repoDetails);
    }






}
