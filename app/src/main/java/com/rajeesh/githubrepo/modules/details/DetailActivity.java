/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rajeesh.githubrepo.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.rajeesh.githubrepo.R;
import com.rajeesh.githubrepo.base.BaseActivity;
import com.rajeesh.githubrepo.di.components.DaggerRepoComponent;
import com.rajeesh.githubrepo.di.module.RepoModule;
import com.rajeesh.githubrepo.modules.adapter.GitRepoAdapter;
import com.rajeesh.githubrepo.modules.adapter.GitRepoDetailAdapter;
import com.rajeesh.githubrepo.mvp.model.Repo;
import com.rajeesh.githubrepo.mvp.model.RepoDetails;
import com.rajeesh.githubrepo.mvp.presenter.RepoDetailsPresenter;
import com.rajeesh.githubrepo.mvp.view.MainView;
import com.rajeesh.githubrepo.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class DetailActivity extends BaseActivity  implements MainView {

    public static final String REPO = "repo";
    @Bind(R.id.CommitDetails) protected TextView CommitDetails;
    @Bind(R.id.detail_list) protected RecyclerView RVdetail_list;
    @Inject
    protected RepoDetailsPresenter mPresenter;
    private GitRepoDetailAdapter mRepoAdapter;
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        showBackArrow();
        loadRepo();

    }
    private void initializeList() {
        setTitle("REPO :"+getIntent().getStringExtra("REPO_NAME"));
        RVdetail_list.setHasFixedSize(true);
        RVdetail_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRepoAdapter = new GitRepoDetailAdapter(getLayoutInflater());
        RVdetail_list.setAdapter(mRepoAdapter);
    }
    private void loadRepo() {
        if (NetworkUtils.isNetAvailable(this)) {
            mPresenter.getRepos();
        } else {
            // mPresenter.getCakesFromDatabase();
        }
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRepoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .repoModule(new RepoModule(this))
                .build().inject(this);
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRepoLoaded(List<Repo> repos) {

    }

    @Override
    public void onRepoDetailLoaded(RepoDetails repo) {
            CommitDetails.setText("Commit Message : " + repo.getCommit().getMessage());
            mRepoAdapter.addRepo(repo.getFiles());
    }

    @Override
    public void onShowDialog(String message) {

    }

    @Override
    public void onHideDialog() {

    }

    @Override
    public void onShowToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onError(String message) {
        CommitDetails.setText(" Git Repository is empty.");
    }

    @Override
    public void onClearItems() {

            mRepoAdapter.clearRepo();
    }
}
