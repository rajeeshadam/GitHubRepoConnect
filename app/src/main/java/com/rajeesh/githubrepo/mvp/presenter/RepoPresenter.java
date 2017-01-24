
package com.rajeesh.githubrepo.mvp.presenter;

import com.rajeesh.githubrepo.api.GitApiService;
import com.rajeesh.githubrepo.base.BasePresenter;
import com.rajeesh.githubrepo.mvp.model.Repo;
import com.rajeesh.githubrepo.mvp.view.MainView;
import com.rajeesh.githubrepo.utilities.GlobalConstants;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class RepoPresenter extends BasePresenter<MainView> implements Observer<List<Repo>> {

    @Inject
    protected GitApiService mApiService;
    @Inject
    public RepoPresenter() {

    }

    public void getRepos() {
        getView().onShowDialog("Loading Repo....");
        Observable<List<Repo>> repoList = mApiService.getData(GlobalConstants.REPO_URL);
       subscribe(repoList,this);
    }


    @Override
    public void onCompleted() {
        getView().onHideDialog();
        getView().onShowToast("Repos loading complete!");
    }

    @Override
    public void onError(Throwable e) {
        getView().onHideDialog();
        getView().onError("Error loading repos " + e.getMessage());
    }

    @Override
    public void onNext(List<Repo> repos) {

        getView().onClearItems();
        getView().onRepoLoaded(repos);
    }




}
