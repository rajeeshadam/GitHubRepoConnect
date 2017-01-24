

package com.rajeesh.githubrepo.mvp.view;

import com.rajeesh.githubrepo.mvp.model.Repo;
import com.rajeesh.githubrepo.mvp.model.RepoDetails;

import java.util.List;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public interface MainView extends BaseView {
    void onRepoLoaded(List<Repo> repos);
    void onRepoDetailLoaded(RepoDetails repo);
    void onShowDialog(String message);
    void onHideDialog();
    void onShowToast(String message);
    void onError(String message);
    void onClearItems();
}
