package com.rajeesh.githubrepo.modules.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rajeesh.githubrepo.R;
import com.rajeesh.githubrepo.githubfile.GithubApp;
import com.rajeesh.githubrepo.utilities.GlobalConstants;

/**
 * Created by Rajeesh adambil on 23/01/2017.
 */
public class SplashSCreen extends AppCompatActivity {

    Handler handler = new Handler();
    private GithubApp mApp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        mApp = new GithubApp(this, GlobalConstants.CLIENT_ID,
                GlobalConstants.CLIENT_SECRET, GlobalConstants.CALLBACK_URL);
        mApp.setListener(listener);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mApp.hasAccessToken()){
                    GlobalConstants.REPO_URL=mApp.getRepoUrl().substring(GithubApp.API_URL.length()+1,mApp.getRepoUrl().length());
                    Intent repolist = new Intent(SplashSCreen.this, RepoActivity.class);
                    startActivity(repolist);
                    finish();

                }
                else {
                    if (mApp.hasAccessToken()) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(
                                SplashSCreen.this);
                        builder.setMessage("Disconnect from GitHub?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog, int id) {
                                                mApp.resetAccessToken();

                                            }
                                        })
                                .setNegativeButton("No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(
                                                    DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        mApp.authorize();
                    }
                }


            }
        }, 2000);




    }

    GithubApp.OAuthAuthenticationListener listener = new GithubApp.OAuthAuthenticationListener() {

        @Override
        public void onSuccess() {
            GlobalConstants.REPO_URL=mApp.getRepoUrl().substring(GithubApp.API_URL.length()+1,mApp.getRepoUrl().length());
            Intent repolist = new Intent(SplashSCreen.this, RepoActivity.class);
            startActivity(repolist);
            finish();
        }

        @Override
        public void onFail(String error) {
            Toast.makeText(SplashSCreen.this, error, Toast.LENGTH_SHORT).show();
        }
    };
}
