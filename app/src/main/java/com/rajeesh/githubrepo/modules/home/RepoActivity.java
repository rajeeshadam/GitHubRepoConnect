package com.rajeesh.githubrepo.modules.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.rajeesh.githubrepo.R;
import com.rajeesh.githubrepo.base.BaseActivity;
import com.rajeesh.githubrepo.di.components.DaggerRepoComponent;
import com.rajeesh.githubrepo.di.module.RepoModule;
import com.rajeesh.githubrepo.githubfile.GithubApp;
import com.rajeesh.githubrepo.githubfile.GithubSession;
import com.rajeesh.githubrepo.modules.details.DetailActivity;
import com.rajeesh.githubrepo.modules.home.adapter.GitRepoAdapter;
import com.rajeesh.githubrepo.mvp.model.Repo;
import com.rajeesh.githubrepo.mvp.model.RepoDetails;
import com.rajeesh.githubrepo.mvp.presenter.RepoPresenter;
import com.rajeesh.githubrepo.mvp.view.MainView;
import com.rajeesh.githubrepo.utilities.GlobalConstants;
import com.rajeesh.githubrepo.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class RepoActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,MainView {
    @Bind(R.id.repo_list)
    protected RecyclerView mRepoList;
    @Bind(R.id.nav_view)
    protected  NavigationView navigationView;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout drawer ;

    @Inject
    protected RepoPresenter mPresenter;
    private GitRepoAdapter mRepoAdapter;
    private SharedPreferences sharedPref;
    public static final String API_USERNAME = "username";
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        initializeList();
        loadRepo();
    }
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }
    private void loadRepo() {
        if (NetworkUtils.isNetAvailable(this)) {
            mPresenter.getRepos();
        } else {
            // mPresenter.getCakesFromDatabase();
        }
    }

    private void initializeList() {


        setSupportActionBar(toolbar);
        setTitle("User :"+getUsername());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        mRepoList.setHasFixedSize(true);
        mRepoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRepoAdapter = new GitRepoAdapter(getLayoutInflater());
        mRepoAdapter.setRepoClickListener(mReopClickListener);
        mRepoList.setAdapter(mRepoAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_reload) {
            loadRepo();
        } else if (id == R.id.nav_aboutme) {

            showAbout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void logout() {
        sharedPref = getSharedPreferences(GithubSession.SHARED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(GithubSession.API_ID, null);
        editor.putString(GithubSession.API_ACCESS_TOKEN, null);
        editor.putString(GithubSession.API_USERNAME, null);
        editor.putString(GithubSession.API_REPO, null);
        editor.commit();
        Intent splashScreen = new Intent(this, SplashSCreen.class);
        startActivity(splashScreen);
        finish();

    }
    private void showAbout() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Developed by Rajeesh adambil")
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Get Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/rajeeshadam/GitHubRepoConnect"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerRepoComponent.builder()
                .applicationComponent(getApplicationComponent())
                .repoModule(new RepoModule(this))
                .build().inject(this);
    }

    @Override
    public void onRepoLoaded(List<Repo> repos) {
        mRepoAdapter.addRepo(repos);
    }

    @Override
    public void onRepoDetailLoaded(RepoDetails repo) {

    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {

        Toast.makeText(RepoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {

        Toast.makeText(RepoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public String getUsername() {
        sharedPref = getSharedPreferences(GithubSession.SHARED, Context.MODE_PRIVATE);
        return sharedPref.getString(API_USERNAME, null);
    }
    @Override
    public void onClearItems() {
        mRepoAdapter.clearRepo();
    }

    private GitRepoAdapter.OnRepoClickListener mReopClickListener = new GitRepoAdapter.OnRepoClickListener() {
        @Override
        public void onClick(View v, Repo repo, String name, int position) {
            GlobalConstants.REPO_DETAIL_URL= "/repos/" +getUsername() +"/" + name + "/commits/master";
            Log.e("urlcheck",GithubApp.API_URL+ GlobalConstants.REPO_DETAIL_URL);
            Intent intent = new Intent(RepoActivity.this, DetailActivity.class);
            intent.putExtra("REPO_NAME",name);
            startActivity(intent);
        }


    };
}
